
/**
 * IOHelper.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, 6
 * 
 * @author Michel Préjet
 * @version July 24th, 2025
 * 
 *          PURPOSE: Serves as a tool to populate an ElectionSimulator with 
 *          ridings and candidates created based on information read from given 
 *          CSV files, then write the output of an election under several systems
 *          to an output file. Provides methods to load candidates and ridings
 *          from CSV files, get the highest and lowest turnout ridings in the
 *          election, and get a list of candidates who reside outside of their
 *          riding. Also includes private helper methods to validate and parse
 *          input, print seat distributions, print the top two parties under any
 *          given system, and verify whether a province or a riding exists.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 *          
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class IOHelper {

    public static final int ROW_START = 2;
    public static final int NUM_FIELDS_RIDING = 4;
    public static final int NUM_FIELDS_CANDIDATE = 6;

    /**
     * Creates new candidates and adds them to an ElectionSimulator using input
     * from a CSV file. Checks that the given name of the file is valid and that
     * the current line of the file is valid, then creates a new Candidate
     * with a name, party, vote count, and province/territory of residence, and
     * adds it to the riding of the candidate. For an invalid file name, an
     * IllegalArgumentException is thrown. For an invalid line of input, a
     * MalformedDataException is thrown.
     * 
     * @param ed      an ElectionSimular containing the ridings to be populated
     *                with candidates.
     * @param csvFile the CSV file from which candidate information is read.
     * @param parties the list of valid parties to which the candidate can belong.
     * @throws IOException            if the file cannot be properly read.
     * @throws MalformedDataException if any line contains missing, empty, or
     *                                blank fields, if the riding ID and vote
     *                                count aren't non-negative
     *                                integers, if the provinces specified don't
     *                                exist, if the given riding name and ID don't
     *                                match any riding in ed, if the given party
     *                                isn't found in parties, if the candidate's
     *                                name is missing, or if the candidate
     *                                already exists in the riding.
     */
    public static void loadCandidatesFromCSV(ElectionSimulator ed, String csvFile, String[] parties)
            throws IOException, MalformedDataException {
        // Perform validation checks on the given file name.
        validateString(csvFile, "File name");

        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        int row = ROW_START;
        String line = br.readLine();
        line = br.readLine(); // Skip the header.
        while (line != null) {
            String[] tokens = line.split(",");

            // Check that the line is formatted properly.
            validateLineCandidate(tokens, row);

            // Check that the given ProvinceTerritory exists for both the riding
            // of the candidate and their residence.
            provinceExists(tokens[0], row);
            ProvinceTerritory residence = provinceExists(tokens[4], row);

            // Check that the given party exists. If so, separate the party name
            // from the candidate's name.
            String[] nameAndParty = parseCandidateNameAndParty(tokens[3], parties, row);
            String candidateName = nameAndParty[0];
            String party = nameAndParty[1];

            // Check that the candidate name is not empty or only whitespace.
            if (candidateName.trim().equals("")) {
                throw new MalformedDataException(row, "Missing candidate name: " + tokens[3]);
            }

            // Check that the given riding exists and corresponds to the given
            // ID.
            String ridingName = tokens[1];
            int ridingID = Integer.parseInt(tokens[2]);
            ridingExists(ridingName, ridingID, ed.getRidings(), row, candidateName);

            // Attempt to add the candidate.
            int numVotes = Integer.parseInt(tokens[5]);
            boolean addedCandidate = ed.addCandidateToRiding(ridingID,
                    new Candidate(candidateName, party, numVotes, residence));
            if (!addedCandidate) {
                throw new MalformedDataException(row,
                        "Failed to add candidate " + candidateName + " to " + ridingName + " " + ridingID);
            }

            line = br.readLine();
            row++;
        }

        br.close();
    }

    /**
     * Creates new ridings and adds them to an ElectionSimulator using input
     * from a CSV file. Checks that the given name of the file is valid and
     * that the current line of the file is valid, then creates a new Riding
     * with a name, ProvinceTerritory, ID, and elector count, and adds it
     * to the given ElectionSimulator. For an invalid file name, an
     * IllegalArgumentException is thrown. For an invalid line of input,
     * a MalformedDataException is thrown.
     * 
     * @param ed      the ElectionSimulator to be populated.
     * @param csvFile the CSV file from which riding information is read.
     * @throws IOException            if the file cannot be read properly.
     * @throws MalformedDataException if any line contains missing, empty, or
     *                                blank fields, if the riding ID and elector
     *                                count aren't non-negative
     *                                integers, if the province specified doesn't
     *                                exist, or if the riding already exists in
     *                                ed.
     */
    public static void loadRidingsFromCSV(ElectionSimulator ed, String csvFile)
            throws IOException, MalformedDataException {
        // Perform validation checks on the given file name.
        validateString(csvFile, "File name");

        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        int row = ROW_START;
        String line = br.readLine();
        line = br.readLine(); // Skip the header.
        while (line != null) {
            String[] tokens = line.split(",");

            // Check that the line is formatted properly.
            validateLineRiding(tokens, row);

            // Check that the given ProvinceTerritory exists.
            ProvinceTerritory location = provinceExists(tokens[0], row);

            // Attempt to add the riding.
            String ridingName = tokens[1];
            int ridingID = Integer.parseInt(tokens[2]);
            int electorCount = Integer.parseInt(tokens[3]);
            boolean addedRiding = ed.addRiding(new Riding(ridingName, ridingID, location, electorCount));
            if (!addedRiding) {
                throw new MalformedDataException(row, "Failed to add riding " + ridingName + ".");
            }

            line = br.readLine();
            row++;
        }

        br.close();
    }

    /**
     * Writes election results under five different systems (national popular
     * vote, first-past-the-post, proportional representation, mixed-member
     * representation, and weighted first-past-the-post) to an output file with
     * a given name. Includes the total vote count for each party, the seat
     * distributions under each system, and the winner and runner-up under each
     * system.
     * 
     * @param ed         the ElectionSimulator used to simulate the election
     *                   under each system.
     * @param filename   the name of the file to which output should be written.
     * @param totalSeats the total number of seat available in the election.
     * @throws IOException if the file cannot be written to properly.
     */
    public static void writeResultsToFile(ElectionSimulator ed, String filename, int totalSeats) throws IOException {
        // Perform validation checks on the given file name.
        validateString(filename, "File name");

        PrintWriter pw = new PrintWriter(new File(filename));

        // Print header.
        pw.println("Election Results Summary");
        pw.println("=========================");

        // Print total votes per party.
        pw.println("Total Votes per Party:");
        ArrayList<PartyResult> totalVotesPerParty = ed.getTotalVotesPerPartyNational();
        printSeatDistribution(totalVotesPerParty, pw);

        // Print NPV winner.
        pw.println("Method #1 - NPV Winner: " + ed.getNPVWinner());
        pw.println();

        // Print FPTP seat distribution, winner, and runner-up.
        pw.println("Method #2 - First-Past-The-Post (FPTP):");
        ArrayList<PartyResult> fptpSeats = ed.getFPTPSeatDistribution();
        printSeatDistribution(fptpSeats, pw);
        printTopTwo(fptpSeats, pw);

        // Print PR seat distribution, winner, and runner-up.
        pw.println("Method #3 - Proportional Representation (PR):");
        ArrayList<PartyResult> prSeats = ed.getPRSeatDistribution(totalSeats);
        printSeatDistribution(prSeats, pw);
        printTopTwo(prSeats, pw);

        // Print MMP seat distribution, winner, and runner-up.
        pw.println("Method #4 - Mixed-Member Proportional (MMP):");
        ArrayList<PartyResult> mmpSeats = ed.getMMPSeatDistribution();
        printSeatDistribution(mmpSeats, pw);
        printTopTwo(mmpSeats, pw);

        // Print weighted FPTP seat distribution, winner, and runner-up.
        pw.println("Method #5 - Weighted FPTP (W-FPTP):");
        ArrayList<PartyResult> weightedSeats = ed.getWeightedFPTPSeatDistribution(totalSeats);
        printSeatDistribution(weightedSeats, pw);
        printTopTwo(weightedSeats, pw);

        pw.close();
    }

    /**
     * Returns a formatted String describing the riding with the highest turnout,
     * including its name, province/territory abbreviation, turnout, total votes,
     * and number of electors.
     * 
     * @param simulator the ElectionSimulator containing the ridings.
     * @return a formatted String describing the riding with the highest turnout,
     *         or "No valid ridings found." if the ElectionSimulator does not
     *         contain any ridings.
     */
    public static String getHighestTurnoutRiding(ElectionSimulator simulator) {
        String returnVal;
        Riding highestTurnoutRiding = simulator.getRidingWithHighestTurnout();

        if (highestTurnoutRiding != null) {
            String name = highestTurnoutRiding.getName();
            String province = highestTurnoutRiding.getLocation().getAbbreviation();
            double turnout = highestTurnoutRiding.getTurnout();
            int votes = highestTurnoutRiding.getTotalVotes();
            int electors = highestTurnoutRiding.getElectors();

            returnVal = String.format("Highest Turnout Riding:\n%s(%s): %.2f%% turnout (%d of %d electors)", name,
                    province, turnout * 100, votes, electors);
        } else {
            returnVal = "No valid ridings found.";
        }

        return returnVal;
    }

    /**
     * Returns a formatted String describing the riding with the lowest turnout,
     * including its name, province/territory abbreviation, turnout, total votes,
     * and number of electors.
     * 
     * @param simulator the ElectionSimulator containing the ridings.
     * @return a formatted String describing the riding with the lowest turnout,
     *         or "No valid ridings found." if the ElectionSimulator does not
     *         contain any ridings.
     */
    public static String getLowestTurnoutRiding(ElectionSimulator simulator) {
        String returnVal;
        Riding lowestTurnoutRiding = simulator.getRidingWithLowestTurnout();

        if (lowestTurnoutRiding != null) {
            String name = lowestTurnoutRiding.getName();
            String province = lowestTurnoutRiding.getLocation().getAbbreviation();
            double turnout = lowestTurnoutRiding.getTurnout();
            int votes = lowestTurnoutRiding.getTotalVotes();
            int electors = lowestTurnoutRiding.getElectors();

            returnVal = String.format("Lowest Turnout Riding:\n%s(%s): %.2f%% turnout (%d of %d electors)", name,
                    province, turnout * 100, votes, electors);
        } else {
            returnVal = "No valid ridings found.";
        }

        return returnVal;
    }

    /**
     * Returns a list of every candidate in a given ElectionSimulator that
     * resides in a province different than that of their riding. Iterates
     * through every candidate contained in simulator and adds those meeting the
     * above conditions to an ArrayList of Candidate. Then, builds a String
     * containing a description of all of those candidates and returns it.
     * 
     * @param simulator the ElectionSimulator containing all the candidates.
     * @return a string representation of every candidate whose province of
     *         residence differs from that of their riding, or "None found." if no
     *         candidates meeting that condition were found in simulator.
     */
    public static String getCandidatesOutsideTheirRiding(ElectionSimulator simulator) {
        ArrayList<Candidate> candidatesOutsideRiding = new ArrayList<Candidate>();

        // Search for candidates whose province/territory of residence differs
        // from that of their riding and store them in an ArrayList.
        for (Riding currRiding : simulator.getRidings()) {
            String ridingLocation = currRiding.getLocation().getFullNameBilingual();
            for (Candidate currCand : currRiding.getCandidates()) {
                String candidateLocation = currCand.getResidence().getFullNameBilingual();
                if (!ridingLocation.equals(candidateLocation)) {
                    candidatesOutsideRiding.add(currCand);
                }
            }
        }

        // Return a description of each candidate found.
        String returnVal;
        if (candidatesOutsideRiding.size() > 0) {
            returnVal = "";
            for (Candidate currCandidate : candidatesOutsideRiding) {
                String name = currCandidate.getName();
                String party = currCandidate.getParty();
                String residence = currCandidate.getResidence().getAbbreviation();
                String riding = "";
                String ridingProvince = "";

                // To find riding information, reiterate through all ridings to
                // find the one with the current candidate, then save the
                // information from that riding.
                for (Riding currRiding : simulator.getRidings()) {
                    for (Candidate comparedCandidate : currRiding.getCandidates()) {
                        if (comparedCandidate.getName().equals(currCandidate.getName())) {
                            riding = currRiding.getName();
                            ridingProvince = currRiding.getLocation().getAbbreviation();
                        }
                    }
                }

                returnVal += String.format("- %s (%s) in %s [%s != %s]\n", name, party, riding, ridingProvince,
                        residence);
            }
        } else {
            returnVal = "None found.";
        }

        return returnVal;
    }

    /**
     * Validates a string input. Checks that the string is not null, empty, or
     * only whitespace. If invalid, throws an IllegalArgumentException.
     * 
     * @param input     the string to be validated.
     * @param fieldName the name of the field in which the input was entered.
     */
    public static void validateString(String input, String fieldName) {
        if (input == null || input.trim().equals("")) {
            throw new IllegalArgumentException(fieldName + " must not be null, empty, or whitespace only.");
        }
    }

    /**
     * Performs validation checks on a line whose purpose is to create a new
     * Riding. Checks that the line contains four comma-separated fields that
     * aren't empty or only whitespace, and that the last two fields (riding ID
     * and elector count) are non-negative integers.
     * 
     * @param tokens an array of strings containing the tokens from the line.
     * @param row    the row of the CSV file from which the line was read.
     * @throws MalformedDataException if the line contains missing, empty, or
     *                                blank fields, or if the riding ID and elector
     *                                count aren't non-negative
     *                                integers.
     */
    private static void validateLineRiding(String[] tokens, int row) throws MalformedDataException {
        // Check that there are 4 fields.
        if (tokens.length != NUM_FIELDS_RIDING) {
            throw new MalformedDataException(row, "Row is malformed.");
        }

        // Check that none of the fields are empty or only whitespace.
        for (String currField : tokens) {
            if (currField.trim().equals("")) {
                throw new MalformedDataException(row, "Row contains empty field(s).");
            }
        }

        // Check that the riding ID is an integer.
        try {
            Integer.parseInt(tokens[2]);
        } catch (NumberFormatException e) {
            throw new MalformedDataException(row, "Bad cast to integer.");
        }

        // Check that the elector count is an integer.
        try {
            Integer.parseInt(tokens[3]);
        } catch (NumberFormatException e) {
            throw new MalformedDataException(row, "Bad cast to integer.");
        }

        // Check that the riding ID is non-negative.
        if (Integer.parseInt(tokens[2]) < 0) {
            throw new MalformedDataException(row, "Riding ID must be non-negative.");
        }

        // Check that the elector count is non-negative.
        if (Integer.parseInt(tokens[3]) < 0) {
            throw new MalformedDataException(row, "Elector count must be non-negative.");
        }

    }

    /**
     * Performs validation checks on a line whose purpose is to create a new
     * Candidate. Checks that the line contains six comma-separated fields that
     * aren't empty or only whitespace, and that the third and sixth fields
     * (riding ID and vote count) are non-negative integers.
     * 
     * @param tokens an array of strings containing the tokens from the line.
     * @param row    the row of the CSV file from which the line was read.
     * @throws MalformedDataException if the line contains missing, empty, or
     *                                blank fields, or if the riding ID and vote
     *                                count aren't non-negative
     *                                integers.
     */
    private static void validateLineCandidate(String[] tokens, int row) throws MalformedDataException {
        // Check that there are 4 fields.
        if (tokens.length != NUM_FIELDS_CANDIDATE) {
            throw new MalformedDataException(row, "Row is malformed.");
        }

        // Check that none of the fields are empty or only whitespace.
        for (String currField : tokens) {
            if (currField.trim().equals("")) {
                throw new MalformedDataException(row, "Row contains empty field(s).");
            }
        }

        // Check that the riding ID is an integer.
        try {
            Integer.parseInt(tokens[2]);
        } catch (NumberFormatException e) {
            throw new MalformedDataException(row, "Bad cast to integer.");
        }

        // Check that the vote count is an integer.
        try {
            Integer.parseInt(tokens[5]);
        } catch (NumberFormatException e) {
            throw new MalformedDataException(row, "Bad cast to integer.");
        }

        // Check that the riding ID is non-negative.
        if (Integer.parseInt(tokens[2]) < 0) {
            throw new MalformedDataException(row, "Riding ID must be non-negative.");
        }

        // Check that the vote count is non-negative.
        if (Integer.parseInt(tokens[5]) < 0) {
            throw new MalformedDataException(row, "Vote count must be non-negative.");
        }

    }

    /**
     * Checks whether a province/territory with a given name exists in the
     * ProvinceTerritory class. If not, throws a MalformedDataException.
     * 
     * @param provinceName the name of the province.
     * @param row          the row of the CSV file from which the province was read.
     * @throws MalformedDataException if no province/territory was found with
     *                                the given name.
     * @return the ProvinceTerritory matching the given name, or null if no such
     *         ProvinceTerritory was found.
     */
    private static ProvinceTerritory provinceExists(String provinceName, int row) throws MalformedDataException {
        ProvinceTerritory province = ProvinceTerritory.getProvinceMatch(provinceName);
        if (province == null) {
            throw new MalformedDataException(row, "Unrecognized province: " + provinceName + ".");
        }
        return province;
    }

    /**
     * Checks whether a riding with a given name and a given ID exists in a
     * given ArrayList of ridings. If not, throws a MalformedDataException.
     * 
     * @param name          the name of the riding.
     * @param id            the ID of the riding.
     * @param ridings       the ArrayList of Riding to be searched.
     * @param row           the row of the CSV file from which the riding name and
     *                      ID
     *                      were read.
     * @param candidateName the name of the candidate for which the method was
     *                      called.
     * @throws MalformedDataException if no riding is found with the given name
     *                                and ID.
     */
    private static void ridingExists(String name, int id, ArrayList<Riding> ridings, int row, String candidateName)
            throws MalformedDataException {
        boolean ridingExists = false;

        for (Riding currRiding : ridings) {
            if (currRiding.getId() == id && currRiding.getName().equals(name)) {
                ridingExists = true;
            }
        }

        if (!ridingExists) {
            throw new MalformedDataException(row, "Failed to add " + candidateName + " to riding " + id + " " + name);
        }
    }

    /**
     * Splits an input string into an array of two strings: the name of a
     * candidate, and their party. Also checks if the input contains the name of a
     * valid party, and throws a MalformedDataException if that is not the
     * case.
     * 
     * @param input   the string to be validated and/or split.
     * @param parties the list of parties.
     * @param row     the row of the CSV file from which the input was read.
     * @return an array of strings with the name of the candidate at index 0,
     *         and the name of the party at index 1.
     * @throws MalformedDataException if the party name is invalid or missing
     *                                from the input.
     */
    private static String[] parseCandidateNameAndParty(String input, String[] parties, int row)
            throws MalformedDataException {
        String[] nameAndParty = new String[2];
        int partyIndex = -1;

        // Search the list of parties to check whether the input contains any
        // of their names.
        for (String currParty : parties) {
            if (input.contains(currParty)) {
                partyIndex = input.indexOf(currParty);
            }
        }

        // If the party name was found, split the input into an array of two
        // strings containing the name and the party of the candidate.
        if (partyIndex == -1) {
            throw new MalformedDataException(row, "Unrecognized candidate party: " + input);
        } else {

            if (partyIndex > 0) {
                nameAndParty[0] = input.substring(0, partyIndex - 1);
            } else {
                nameAndParty[0] = "";
            }

            nameAndParty[1] = input.substring(partyIndex);
        }

        return nameAndParty;
    }

    /**
     * Prints the seat distribution in a given ArrayList of PartyResult to an
     * output file. Indents output and ends with a blank newline.
     * 
     * @param seats the seat distribution to be printed.
     * @param pw    the PrintWriter used to print to the file.
     */
    private static void printSeatDistribution(ArrayList<PartyResult> seats, PrintWriter pw) {
        for (PartyResult currParty : seats) {
            pw.printf("\t%s: %.2f\n", currParty.getParty(), currParty.getCount());
        }
        pw.println();
    }

    /**
     * Prints the winner and the runner-up in an election to an output file
     * given an ArrayList of PartyResult containing the seat distribution.
     * Indents output and ends with a blank newline.
     * 
     * @param seats the seat distribution of the election.
     * @param pw    the PrintWriter used to print to the file.
     */
    private static void printTopTwo(ArrayList<PartyResult> seats, PrintWriter pw) {
        String[] topTwo = PartyResult.getTopTwoPartyNames(seats);
        if (!topTwo[0].equals("N/A") && !topTwo[1].equals("N/A")) {
            pw.println("\tWinner: " + topTwo[0]);
            pw.println("\tRunner-up: " + topTwo[1]);
        } else if (!topTwo[0].equals("N/A")) {
            pw.println("\tWinner: " + topTwo[0]);
        }
        pw.println();
    }
}
