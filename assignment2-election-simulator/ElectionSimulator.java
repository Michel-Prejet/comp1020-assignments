
/**
 * ElectionSimulator.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, 5
 * 
 * @author Michel Préjet
 * @version July 24th, 2025
 * 
 *          PURPOSE: Simulates an election under various electoral systems, 
 *          including national popular vote (NPV), first-past-the-post (FPTP),
 *          proportional representation (PR), mixed-member representation (MMP),
 *          and weighted first-past-the-post (W-FPTP). Contains all of the ridings 
 *          in the election, as well as getter and setter methods which allow the user
 *          to get all ridings, get a riding with a specific ID, add a Riding,
 *          and add a Candidate to a riding. Additionally, provides methods to get 
 *          seat distributions under each electoral system.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class ElectionSimulator {
    private ArrayList<Riding> ridings;

    /**
     * Constructs a new ElectionSimulator. Creates an ArrayList of ridings that
     * is empty by default.
     */
    public ElectionSimulator() {
        this.ridings = new ArrayList<Riding>();
    }

    public ArrayList<Riding> getRidings() {
        return this.ridings;
    }

    /**
     * Returns the riding matching the given ID if such a riding exists; returns
     * null otherwise.
     * 
     * @param riding the ID of the riding.
     * @return the riding matching the given ID if such a riding exists;
     *         null otherwise.
     */
    public Riding findRiding(int riding) {
        Riding returnRiding = null;
        for (Riding currRiding : this.ridings) {
            if (currRiding.getId() == riding) {
                returnRiding = currRiding;
            }
        }
        return returnRiding;
    }

    /**
     * Adds the given riding to the ridings ArrayList if a riding with that ID
     * doesn't already exist. Returns true if successful; otherwise, returns
     * false.
     * 
     * @param riding the riding to be added to the ArrayList.
     * @return true if successful; false otherwise.
     */
    public boolean addRiding(Riding riding) {
        boolean returnVal = false;

        if (findRiding(riding.getId()) == null) {
            this.ridings.add(riding);
            returnVal = true;
        }

        return returnVal;
    }

    /**
     * Adds a given Candidate to a riding with a given ID, if that riding
     * exists and if the candidate is not null. Returns true if the operation
     * was successful; returns false otherwise.
     * 
     * @param ridingId the ID of the riding to which the candidate will be added.
     * @param c        the candidate to add to the riding.
     * @return true if the candidate was added successfully; false otherwise.
     */
    public boolean addCandidateToRiding(int ridingId, Candidate c) {
        boolean returnVal = false;
        Riding currRiding = findRiding(ridingId);

        if (currRiding != null && c != null) {
            currRiding.addCandidate(c);
            returnVal = true;
        }

        return returnVal;
    }

    /**
     * Calculates the total number of votes for each party across all ridings.
     * For each candidate, their number of votes is added to the count for
     * their respective party, then each party and its vote count is returned in
     * a PartyResult ArrayList.
     * 
     * @return an ArrayList of PartyResult, each containing a party and its
     *         total vote count.
     */
    public ArrayList<PartyResult> getTotalVotesPerPartyNational() {
        ArrayList<PartyResult> parties = new ArrayList<PartyResult>();

        for (Riding currRiding : this.ridings) {
            addUpRidingVotes(currRiding, parties);
        }

        return parties;
    }

    /**
     * Simulates a national popular vote system by finding and returning the
     * party with the most votes across all ridings. If no PartyResult has been
     * created and assigned to a Candidate, returns null.
     * 
     * @return the name of the party with the most votes across ridings, or null
     *         if no parties have been linked to any Candidate.
     */
    public String getNPVWinner() {
        ArrayList<PartyResult> nationalResult = getTotalVotesPerPartyNational();
        PartyResult winner = null;
        String party = null;

        // If there are parties in the nationalResult ArrayList, loop through
        // it to find the one with the most votes.
        if (nationalResult.size() != 0) {
            winner = nationalResult.get(0);
            for (PartyResult currParty : nationalResult) {
                if (winner.getCount() < currParty.getCount()) {
                    winner = currParty;
                }
            }
        }

        if (winner != null) {
            party = winner.getParty();
        }

        return party;
    }

    /**
     * Determines the number of seats each party wins under a first-past-
     * the-post system. Calculates the number of votes won by each party in a
     * riding, then attributes one seat to the party with the most votes. Returns
     * an ArrayList of PartyResult containing the total seat count for each
     * Party.
     * 
     * @return an ArrayList of PartyResult containing the total number of seats
     *         won by each party.
     */
    public ArrayList<PartyResult> getFPTPSeatDistribution() {
        ArrayList<PartyResult> seats = new ArrayList<PartyResult>();

        // Find the party with the most votes for each riding, then increment
        // their seat count in the seats ArrayList.
        for (Riding currRiding : this.ridings) {
            PartyResult winningParty = getRidingWinner(currRiding);
            if (winningParty != null) {
                String winningPartyName = winningParty.getParty();
                PartyResult.addResultToPartyList(seats, winningPartyName, 1);
            }
        }

        return seats;
    }

    /**
     * Calculates the number of seats each party wins under a proportional
     * representation system. Each party's total vote count is divided by the
     * total number of votes cast nationally, then multiplied by the total
     * number of seats. The seat count is then rounded to the nearest integer.
     * Parties that have not won any seats are not included in the result.
     * 
     * @param totalSeats the total number of seats nationally.
     * @return an ArrayList of PartyResult containing each party along with the
     *         number of seats they have won (exluding parties with 0 seats).
     */
    public ArrayList<PartyResult> getPRSeatDistribution(int totalSeats) {
        ArrayList<PartyResult> nationalResult = getTotalVotesPerPartyNational();

        // Get the total number of votes cast nationally.
        long totalNationalVotes = 0;
        for (PartyResult currParty : nationalResult) {
            totalNationalVotes += currParty.getCount();
        }

        // Calculate the number of seats for each party and add it to an
        // ArrayList of PartyResult.
        ArrayList<PartyResult> seatsPR = new ArrayList<PartyResult>();
        if (totalNationalVotes > 0) {
            for (PartyResult currParty : nationalResult) {
                int seatCount = (int) Math.round(((double) currParty.getCount() / totalNationalVotes) * totalSeats);
                if (seatCount > 0) {
                    PartyResult.addResultToPartyList(seatsPR, currParty.getParty(), seatCount);
                }
            }
        }

        return seatsPR;
    }

    /**
     * Calculates the number of seats each party wins under a mixed-member
     * proportional system. "Top-up" seat counts are obtained by subtracting
     * each party's FPTP seats from their PR seats (with only positive results
     * being included). Then, "top-up" seat counts are added onto FPTP seat
     * counts (including parties with 0 FPTP seats, but who won "top-up" seats).
     * 
     * @return an ArrayList of PartyResult containing each party along with
     *         the number of MMP seats they have won.
     */
    public ArrayList<PartyResult> getMMPSeatDistribution() {
        ArrayList<PartyResult> mmpResult = new ArrayList<PartyResult>();

        // Get FPTP, PR, and "top-up" seat distributions.
        ArrayList<PartyResult> fptpResult = getFPTPSeatDistribution();
        ArrayList<PartyResult> prResult = getPRSeatDistribution(this.ridings.size());
        ArrayList<PartyResult> topUpSeats = getTopUpSeats(fptpResult, prResult);

        // Merge FPTP and "top-up" results.
        for (PartyResult currParty : fptpResult) {
            String party = currParty.getParty();
            double newCount = currParty.getCount();
            for (PartyResult topUp : topUpSeats) {
                if (topUp.getParty().equals(party)) {
                    newCount += topUp.getCount();
                }
            }

            PartyResult.addResultToPartyList(mmpResult, party, newCount);
        }

        // If a party did not win FPTP seats but received "top-up" seats, add it
        // to mmpResult.
        for (PartyResult topUp : topUpSeats) {
            String party = topUp.getParty();
            boolean found = false;

            for (int i = 0; i < fptpResult.size() && !found; i++) {
                if (fptpResult.get(i).getParty().equals(party)) {
                    found = true;
                }
            }

            if (!found) {
                PartyResult.addResultToPartyList(mmpResult, party, topUp.getCount());
            }
        }

        return mmpResult;
    }

    /**
     * Determines the number of seats each party wins under a weighted first-
     * past-the-post system. Calculates a preliminary seat weight for each
     * riding by dividing the elector count by the national average number of
     * electors, then normalizes seat counts using a scaling factor so that the
     * total number of seats matches the available seat count.
     * 
     * @param totalSeats the total number of seats available in the election.
     * @return an ArrayList of PartyResult, each containing a party and its
     *         final weighted seat count.
     */
    public ArrayList<PartyResult> getWeightedFPTPSeatDistribution(int totalSeats) {
        ArrayList<PartyResult> weightedResultPrelim = new ArrayList<PartyResult>();
        double nationalAverageElectors = calculateAverageNumElectors();

        // Calculate the preliminary weight for each party.
        for (Riding currRiding : this.ridings) {
            if (currRiding.getElectors() > 0) {
                double weight = (double) currRiding.getElectors() / nationalAverageElectors;
                PartyResult winningParty = getRidingWinner(currRiding);
                if (winningParty != null) {
                    PartyResult.addResultToPartyList(weightedResultPrelim, winningParty.getParty(), weight);
                }
            }
        }

        // Normalize seat counts.
        ArrayList<PartyResult> weightedResultFinal = new ArrayList<PartyResult>();
        double scalingFactor = calculateScalingFactor(weightedResultPrelim, totalSeats);
        if (scalingFactor > 0) {
            for (PartyResult currParty : weightedResultPrelim) {
                int finalSeatCount = (int) Math.round(currParty.getCount() * scalingFactor);
                PartyResult.addResultToPartyList(weightedResultFinal, currParty.getParty(), finalSeatCount);
            }
        }

        return weightedResultFinal;
    }

    /**
     * Finds the riding with the highest turnout. If a valid riding exists (any
     * riding with more than 0 electors), searches the ridings ArrayList for the
     * one with the highest turnout value.
     * 
     * @return the riding with the highest turnout; or null if no valid riding
     *         exists.
     */
    public Riding getRidingWithHighestTurnout() {
        Riding highestTurnoutRiding = null;

        // Initialize with the first valid riding.
        boolean isValid = false;
        for (int i = 0; i < this.ridings.size() && !isValid; i++) {
            if (this.ridings.get(i).getElectors() > 0) {
                isValid = true;
                highestTurnoutRiding = this.ridings.get(i);
            }
        }

        // Find which riding has the highest turnout.
        if (isValid) {
            for (Riding currRiding : this.ridings) {
                if (currRiding.getElectors() > 0 && currRiding.getTurnout() > highestTurnoutRiding.getTurnout()) {
                    highestTurnoutRiding = currRiding;
                }
            }
        }

        return highestTurnoutRiding;
    }

    /**
     * Finds the riding with the lowest turnout. If a valid riding exists (any
     * riding with more than 0 electors), searches the ridings ArrayList for the
     * one with the lowest turnout value.
     * 
     * @return the riding with the lowest turnout; or null if no valid riding
     *         exists.
     */
    public Riding getRidingWithLowestTurnout() {
        Riding lowestTurnoutRiding = null;

        // Initialize with the first valid riding.
        boolean isValid = false;
        for (int i = 0; i < this.ridings.size() && !isValid; i++) {
            if (this.ridings.get(i).getElectors() > 0) {
                isValid = true;
                lowestTurnoutRiding = this.ridings.get(i);
            }
        }

        // Find which riding has the lowest turnout.
        if (isValid) {
            for (Riding currRiding : this.ridings) {
                if (currRiding.getElectors() > 0 && currRiding.getTurnout() < lowestTurnoutRiding.getTurnout()) {
                    lowestTurnoutRiding = currRiding;
                }
            }
        }

        return lowestTurnoutRiding;
    }

    /**
     * Adds the votes for each candidate in a given riding to the count for their
     * respective party in a given ArrayList of PartyResult. If the candidate's
     * party doesn't exist, creates a new PartyResult object and adds it to the
     * ArrayList.
     * 
     * @param riding  the riding whose votes will be added.
     * @param results the ArrayList of PartyResult containing vote counts for
     *                each party.
     */
    private void addUpRidingVotes(Riding riding, ArrayList<PartyResult> results) {
        ArrayList<Candidate> candidates = riding.getCandidates();
        for (Candidate currCand : candidates) {
            PartyResult.addResultToPartyList(results, currCand.getParty(), currCand.getVotes());
        }
    }

    /**
     * Finds the party with the highest count in a given riding.
     * 
     * @param riding the riding for which we want to find the winning party.
     * @return a PartyResult containing the party with the highest count, or
     *         null if no parties exist for the given riding.
     */
    private PartyResult getRidingWinner(Riding riding) {
        PartyResult winningParty = null;

        // Gather votes for all parties in the riding.
        ArrayList<PartyResult> ridingResults = new ArrayList<PartyResult>();
        addUpRidingVotes(riding, ridingResults);

        // Find the party with the highest count.
        if (ridingResults.size() > 0) {
            winningParty = ridingResults.get(0);
            for (PartyResult currParty : ridingResults) {
                if (currParty.getCount() > winningParty.getCount()) {
                    winningParty = currParty;
                }
            }
        }

        return winningParty;
    }

    /**
     * Computes "top-up" seats for each party - that is, the number of additional
     * FPTP seats a party would need to reach its PR seat count. For elements in
     * fptpResult and prResult with matching parties, the number of FPTP seats
     * is subtracted from the number of PR seats, and negative values are clamped
     * to 0. Any positive result is added to a topUpSeats ArrayList of
     * PartyResult.
     * 
     * @param fptpResult an ArrayList of PartyResult containing the total FPTP seat
     *                   count for each party.
     * @param prResult   an ArrayList of PartyResult containing the total PR seat
     *                   count for each party.
     * @return an ArrayList of PartyResult, each containing a party and its
     *         "top-up" seat count (parties with counts less than or equal to zero
     *         are excluded).
     */
    private ArrayList<PartyResult> getTopUpSeats(ArrayList<PartyResult> fptpResult, ArrayList<PartyResult> prResult) {
        ArrayList<PartyResult> topUpSeats = new ArrayList<PartyResult>();

        // Subtract FPTP seats from PR seats for each party.
        for (PartyResult currPR : prResult) {
            String party = currPR.getParty();
            double topUpVal = currPR.getCount();

            for (PartyResult currFPTP : fptpResult) {
                if (currFPTP.getParty().equals(party)) {
                    // Calculate the "top-up" value, clamping negative values to 0.
                    topUpVal = Math.max(currPR.getCount() - currFPTP.getCount(), 0);
                }
            }

            // If topUpVal is positive, add it to topUpSeats under its respective
            // party.
            if (topUpVal > 0) {
                PartyResult.addResultToPartyList(topUpSeats, party, topUpVal);
            }
        }

        return topUpSeats;
    }

    /**
     * Calculates the average number of electors across all ridings. Finds the
     * total number of electors, then divides it by the number of ridings.
     * 
     * @return the average number of electors across all ridings.
     */
    private double calculateAverageNumElectors() {
        int numRidings = this.ridings.size();
        int totalNumElectors = 0;

        for (Riding currRiding : this.ridings) {
            totalNumElectors += currRiding.getElectors();
        }

        return (double) totalNumElectors / numRidings;
    }

    /**
     * Calculates the scaling factor needed to determine seat distributions
     * under a weighted first-past-the-post system. Adds up all weighted seats,
     * then divides the total number of seats by that value.
     * 
     * @param weightedSeats an ArrayList of PartyResult containing weighted seat
     *                      counts for each party.
     * @param totalSeats    the total number of seats available.
     * @return the scaling factor for a weighted first-past-the-post system, or
     *         -1 if no weighted seat values were found.
     */
    private double calculateScalingFactor(ArrayList<PartyResult> weightedSeats, int totalSeats) {
        double sumOfWeightedSeats = 0;

        // Add up all weighted seat counts.
        for (PartyResult currParty : weightedSeats) {
            sumOfWeightedSeats += currParty.getCount();
        }

        // Return the scaling factor, or -1 if no weighted seat values were found.
        double returnVal;
        if (sumOfWeightedSeats > 0) {
            returnVal = totalSeats / sumOfWeightedSeats;
        } else {
            returnVal = -1;
        }

        return returnVal;
    }
}
