
/**
 * HyperspaceLane.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 3, 2
 * 
 * @author Michel Préjet
 * @version July 29th, 2025
 * 
 *          PURPOSE: Represents a HyperspaceLane connecting two star systems in
 *          a galaxy, including its name, its direction (the name of the star 
 *          systems representing its origin and its destination), the distance 
 *          which it covers (in light years), and its toll cost (in credits).
 *          Provides getter methods for each of these instance variables, as
 *          well as methods to check whether it contains any star system with a 
 *          given name, to check whether it connects two star systems with given 
 *          names, check whether a given star system is equal to it (connects
 *          the same star systems in either direction), get the lexicographic
 *          comparison of another hyperspace lane to it (according to the name 
 *          of the star system of origin), and sort an ArrayList of hyperspace 
 *          lanes via insertion sort according to the name of the hyperspace lanes' 
 *          star system of origin.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class HyperspaceLane {
    private String fromSystem;
    private String toSystem;
    private double distance; // in light years
    private double tollCost; // in credits

    /**
     * Constructs a new HyperspaceLane with a given direction (fromSystem and
     * toSystem), a given distance, and a given toll cost. Throws an
     * IllegalArgumentException if the given fromSystem and toSystem arguments
     * are null, or if the given distance or toll cost values are negative.
     * 
     * @param fromSystem the system serving as the starting point of the
     *                   hyperspace lane.
     * @param toSystem   the destination system of the hyperspace lane.
     * @param distance   the distance covered by the hyperspace lane.
     * @param tollCost   the toll cost of the hyperspace lane.
     */
    public HyperspaceLane(String fromSystem, String toSystem, double distance, double tollCost) {
        if (fromSystem != null && toSystem != null) {
            this.fromSystem = fromSystem;
            this.toSystem = toSystem;
        } else {
            throw new IllegalArgumentException("HyperspaceLane constructor cannot accept null arguments.");
        }

        if (distance >= 0 && tollCost >= 0) {
            this.distance = distance;
            this.tollCost = tollCost;
        } else {
            throw new IllegalArgumentException(
                    "HyperspaceLane constructor cannot accept negative arguments.)");
        }
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public String getToSystem() {
        return toSystem;
    }

    public double getDistance() {
        return distance;
    }

    public double getTollCost() {
        return tollCost;
    }

    /**
     * Checks whether the current hyperspace lane contains a given star system
     * (passed by name) as either its origin or its destination.
     * 
     * @param a the name of star system.
     * @return true if the current hyperspace lane connects the given star
     *         system to any other; false otherwise.
     */
    public boolean containsSystem(String a) {
        boolean toReturn = false;
        if (a != null) {
            toReturn = a.equals(this.fromSystem) || a.equals(this.toSystem);
        }
        return toReturn;
    }

    /**
     * Checks whether the current hyperspace lane connects two given star
     * systems in either direction, and verifies that the two given star
     * systems are not equal.
     * 
     * @param a the first star system.
     * @param b the second star system.
     * @return true if the current hyperspace lane connects the two given
     *         star systems in either direction; false otherwise.
     */
    public boolean connects(String a, String b) {
        boolean toReturn = false;
        if (a != null && b != null) {
            toReturn = containsSystem(a) && containsSystem(b) && !a.equals(b);
        }
        return toReturn;
    }

    /**
     * Checks whether a given hyperspace lane connects the same two systems as
     * the current instance (in either direction).
     * 
     * @param other the hyperspace lane to be compared with the current instance.
     * @return true if both hyperspace lanes connect the same two systems; false
     *         otherwise.
     */
    public boolean equals(HyperspaceLane other) {
        boolean isEqual = false;
        if (other != null && other.connects(this.fromSystem, this.toSystem)) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns the lexicographic comparison of the current hyperspace lane's
     * fromSystem name to a given hyperspace lane's fromSystem name. Throws a
     * NullPointerException if the given hyperspace lane is null.
     * 
     * @param other the hyperspace lane to be compared to the current instance.
     * @return the lexicographic comparison of the current hyperspace lane's
     *         fromSystem name to the other hyperspace lane's fromSystem name (as an
     *         integer).
     */
    public int compareTo(HyperspaceLane other) {
        int compareTo;
        if (other != null) {
            compareTo = this.fromSystem.compareTo(other.getFromSystem());
        } else {
            throw new NullPointerException("Cannot compare with a null object.");
        }
        return compareTo;
    }

    /**
     * Returns a String representation of the current hyperspace lane,
     * including its star system of origin, its destination star system, its
     * distance (in light years), and its toll cost (in credits)
     * 
     * @return a String representation of the current hyperspace lane.
     */
    public String toString() {
        return String.format("Lane[from=%s, to=%s, %.2f LY, %.2f cr toll]", this.fromSystem, this.toSystem,
                this.distance, this.tollCost);
    }

    /**
     * Sorts a given ArrayList of HyperspaceLane using insertion sort according
     * the fromSystem name of each hyperspace lane.
     * 
     * @param lanes the ArrayList of HyperspaceLane to be sorted.
     */
    public static void insertionSortByFromName(ArrayList<HyperspaceLane> lanes) {
        for (int i = 1; i < lanes.size(); i++) {
            insertionSortRound(lanes, i);
        }
    }

    /**
     * Performs a single round of insertion sort. Assumes the given ArrayList of
     * HyperspaceLane is already sorted from index 0 to the given index minus
     * one, then shifts each element lexicographically greater than the fromSystem
     * name of the hyperspace lane at the given index to the right. The hyperspace
     * lane at the given index is then inserted into its correct position in the
     * sorted portion of the ArrayList.
     * 
     * @param lanes the ArrayList of lanes on which a single round of
     *              insertion sort should be performed.
     * @param i     the first index of the unsorted portion of the ArrayList.
     */
    public static void insertionSortRound(ArrayList<HyperspaceLane> lanes, int i) {
        HyperspaceLane currItem = lanes.get(i);
        int position = i - 1;

        while (position >= 0 && lanes.get(position).compareTo(currItem) > 0) {
            lanes.set(position + 1, lanes.get(position));
            position--;
        }

        lanes.set(position + 1, currItem);
    }
}
