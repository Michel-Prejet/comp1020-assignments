/**
 * Candidate.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, 2
 * 
 * @author Michel Préjet
 * @version July 19th, 2025
 * 
 *          PURPOSE: Represents a Candidate in an election. Contains the
 *          name, party, number of votes, and province/territory of residence
 *          of the candidate. Provides getter methods for each of these instance
 *          variables.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

public class Candidate {
    private String name;
    private String party;
    private int votes;
    private ProvinceTerritory residence;

    /**
     * Constructs a new candidate with a given name, party, number of votes,
     * and province/territory of residence.
     * 
     * @param name        the name of the candidate.
     * @param party       the party of the candidate.
     * @param votes       the number of votes for the candidate.
     * @param resProvince the candidate's province of residence.
     */
    public Candidate(String name, String party, int votes, ProvinceTerritory resProvince) {
        this.name = name;
        this.party = party;
        this.votes = votes;
        this.residence = resProvince;
    }

    public String getName() {
        return this.name;
    }

    public String getParty() {
        return this.party;
    }

    public int getVotes() {
        return this.votes;
    }

    public ProvinceTerritory getResidence() {
        return this.residence;
    }
}
