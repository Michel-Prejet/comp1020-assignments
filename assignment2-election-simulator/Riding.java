
/**
 * Riding.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, 3
 * 
 * @author Michel Préjet
 * @version July 19th, 2025
 * 
 *          PURPOSE: Represents an electoral riding in a Canadian federal 
 *          election. Contains the name of the riding, its ID, the ProvinceTerritory
 *          in which it is located, the number of electors, and the list of 
 *          candidates. Provides getter methods for each of these instance 
 *          variables, and methods to add a Candidate, get the total number of 
 *          votes cast, get the voter turnout, and get the winning candidate 
 *          (using first-past-the-post).
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class Riding {
    private String name;
    private int id;
    private int electors;
    private ProvinceTerritory location;
    private ArrayList<Candidate> candidates;

    /**
     * Constructs a new riding with a name, ID, ProvinceTerritory, and number
     * of electors. Creates an ArrayList of candidates for the riding, which is
     * set to empty by default.
     * 
     * @param name     the name of the riding.
     * @param id       the ID of the riding.
     * @param province the ProvinceTerritory in which the riding is located.
     * @param electors the number of electors in the riding.
     */
    public Riding(String name, int id, ProvinceTerritory province, int electors) {
        this.name = name;
        this.id = id;
        this.location = province;
        this.electors = electors;
        this.candidates = new ArrayList<Candidate>();
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public ProvinceTerritory getLocation() {
        return this.location;
    }

    public int getElectors() {
        return this.electors;
    }

    public ArrayList<Candidate> getCandidates() {
        return this.candidates;
    }

    public void addCandidate(Candidate c) {
        this.candidates.add(c);
    }

    /**
     * Returns the total of number of votes in the riding across all candidates.
     * 
     * @return the total number of votes cast in the riding.
     */
    public int getTotalVotes() {
        int totalVotes = 0;
        for (Candidate currCand : this.candidates) {
            totalVotes += currCand.getVotes();
        }

        return totalVotes;
    }

    /**
     * Returns the turnout for the riding - that is, the total number of votes
     * cast divided by the number of electors. Returns a maximum value of 1.
     * 
     * @return the turnout for the riding.
     */
    public double getTurnout() {
        return Math.min(((double) getTotalVotes()) / this.electors, 1);
    }

    /**
     * Returns the candidate with the most votes in the riding. If there is a
     * tie between several candidates, returns the first one in the list.
     * 
     * @return the candidate with the most votes in the riding, or null if there
     *         are no candidates.
     */
    public Candidate getWinningCandidateFPTP() {
        Candidate winner = null;

        if (this.candidates.size() != 0) {
            winner = this.candidates.get(0);
            for (Candidate i : this.candidates) {
                if (i.getVotes() > winner.getVotes()) {
                    winner = i;
                }
            }
        }

        return winner;
    }
}
