
/**
 * PartyResult.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, 4
 * 
 * @author Michel Préjet
 * @version July 19th, 2025
 * 
 *          PURPOSE: Represents a political party with a name and a count (which
 *          could refer to the number of votes or seats, or the weight of the
 *          party). Provides getter methods for both these instance variables,
 *          and a method to increment the party's count by a given amount. Also
 *          provides static methods for a PartyResult ArrayList, which allow the
 *          user to get and increment the count of a specific party in the 
 *          ArrayList, and to get the two parties in the ArrayList with the 
 *          highest count. Helper methods are used to validate party names, get 
 *          the index of a party with a given name in an ArrayList, and get the 
 *          party with the highest count in an ArrayList.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class PartyResult {
    private String party;
    private double count;

    /**
     * Constructs a new PartyResult with a given party name. Sets count to 0.0
     * by default. Throws an IllegalArgumentException if the given name is invalid
     * (null, empty, or only whitespace).
     * 
     * @param party the name of the party.
     */
    public PartyResult(String party) {
        IOHelper.validateString(party, "Party name"); // Validate input.
        this.party = party;
        this.count = 0.0;
    }

    public String getParty() {
        return this.party;
    }

    public double getCount() {
        return this.count;
    }

    /**
     * Increases the party's count by a given amount. Does not allow the value
     * of count to fall below zero.
     * 
     * @param amount the amount to be added to the party's count.
     */
    public void increment(double amount) {
        if (this.count + amount >= 0) {
            this.count += amount;
        } else {
            this.count = 0;
        }
    }

    /**
     * Returns a string representation of the PartyResult, including the party's
     * name and its count.
     * 
     * @return a string representation of the current PartyResult.
     */
    public String toString() {
        return String.format("%s: %.2f", this.party, this.count);
    }

    /**
     * Increments the count for a party with a given name in a given ArrayList
     * of PartyResult. If no party exists with that name, a new one is created
     * and added to the ArrayList.
     * 
     * @param list   the ArrayList of PartyResult in which the given party
     *               should be found or created.
     * @param party  the name of the party whose count should be incremented.
     * @param amount the amount by which the party's count should be incremented.
     */
    public static void addResultToPartyList(ArrayList<PartyResult> list, String party, double amount) {
        // Perform validation checks.
        boolean isValid = true;
        try {
            IOHelper.validateString(party, "Party name");
        } catch (IllegalArgumentException e) {
            isValid = false;
        }

        // Find the index of the party that matches the given name.
        int index = getPartyIndex(list, party);

        // Increment the party's count.
        if (index != -1) {
            list.get(index).increment(amount);
        } else if (isValid) {
            list.add(new PartyResult(party)); // Create new PartyResult if no such party exists.
            list.get(list.size() - 1).increment(amount);
        }
    }

    /**
     * Returns the count for a party with a given name in a given ArrayList of
     * PartyResult. If the given name is invalid or doesn't correspond to any
     * element in the ArrayList, a count of 0.0 is returned.
     * 
     * @param list  the PartyResult ArrayList to be searched.
     * @param party the name of the party whose count should be returned.
     * @return the count of the party with the given name if it exists and is
     *         valid; 0.0 otherwise.
     */
    public static double getPartyCountFromList(ArrayList<PartyResult> list, String party) {
        // Perform validation checks.
        boolean isValid = true;
        try {
            IOHelper.validateString(party, "Party name");
        } catch (IllegalArgumentException e) {
            isValid = false;
        }

        // Find the PartyResult matching the given name.
        int index = getPartyIndex(list, party);
        double returnCount = 0.0;
        if (index != -1 && isValid) {
            returnCount = list.get(index).getCount();
        }

        return returnCount;
    }

    /**
     * Returns a string array containing the party with the highest count in a
     * given ArrayList of PartyResult at index 0, and the party with the second
     * highest count at index 1. If there is only one party, index 1 contains
     * "N/A"; if there are no parties, both indexes contain "N/A".
     * 
     * @param results the ArrayList of PartyResult to be searched.
     * @return a String array containing the two parties with the highest counts,
     *         or "N/A" at index 1 if there is only one party; or "N/A" at both
     *         indexes if there are no parties.
     */
    public static String[] getTopTwoPartyNames(ArrayList<PartyResult> results) {
        String[] topNames;
        PartyResult party1;
        PartyResult party2;

        if (results.size() == 0) {
            topNames = new String[] { "N/A", "N/A" };
        } else if (results.size() == 1) {
            party1 = getTopParty(results);
            topNames = new String[] { party1.getParty(), "N/A" };
        } else {
            // Get party with highest count, then remove it from ArrayList to
            // get party with second highest count, then add it back again.
            party1 = getTopParty(results);
            results.remove(party1);
            party2 = getTopParty(results);
            results.add(party1);
            topNames = new String[] { party1.getParty(), party2.getParty() };
        }

        return topNames;
    }

    /**
     * Returns the index of a PartyResult object with a given name in a given
     * ArrayList of PartyResult.
     * 
     * @param list  the ArrayList of PartyResult to be searched.
     * @param party the name of the party being searched for.
     * @return the index of the PartyResult with the given name, if it exists;
     *         -1 otherwise.
     */
    private static int getPartyIndex(ArrayList<PartyResult> list, String party) {
        int index = -1;
        party = party.trim().toLowerCase();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParty().toLowerCase().equals(party)) {
                index = i;
            }
        }

        return index;
    }

    /**
     * Returns the party with the highest count in an ArrayList of PartyResult.
     * 
     * @param list the ArrayList of PartyResult to be searched.
     * @return the PartyResult with the highest count.
     */
    private static PartyResult getTopParty(ArrayList<PartyResult> list) {
        PartyResult topParty = list.get(0);
        for (PartyResult currParty : list) {
            if (currParty.getCount() > topParty.getCount()) {
                topParty = currParty;
            }
        }

        return topParty;
    }

}
