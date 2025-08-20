
/**
 * StarSystem.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 3, 2
 * 
 * @author Michel Préjet
 * @version July 29th, 2025
 * 
 *          PURPOSE: Represents a StarSystem within a galaxy, including its name,
 *          its faction, the hyperspace lanes to which it is connected, and its
 *          danger level. Provides getter methods for each of these instance
 *          variables, as well as methods to add a hyperspace lane, get a list of 
 *          every other star system connected to it by a hyperspace lane, determine
 *          whether a given star system is equal to it (has the same name), get the
 *          lexicographic comparison of another star system to it (according to 
 *          the name of the star system), and sort an ArrayList of star systems 
 *          by name via selection sort.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class StarSystem {
    private String name;
    private ArrayList<HyperspaceLane> connectedLanes;
    private String faction;
    private int dangerLevel;

    /**
     * Constructs a new StarSystem with a given name, faction, and danger level.
     * Clamps the danger level between 0 and 10 and creates an empty ArrayList
     * of HyperspaceLane. Throws an IllegalArgumentException if the given name or
     * faction are null.
     * 
     * @param name        the name of the star system.
     * @param faction     the faction to which the star system belongs.
     * @param dangerLevel the danger level of the star system (clamped between
     *                    0 and 10).
     */
    public StarSystem(String name, String faction, int dangerLevel) {
        if (name != null && faction != null) {
            this.name = name;
            this.faction = faction;
        } else {
            throw new IllegalArgumentException("StarSystem constructor cannot accept null arguments.");
        }

        // Clamp the danger level between 0 and 10.
        if (dangerLevel >= 0 && dangerLevel <= 10) {
            this.dangerLevel = dangerLevel;
        } else if (dangerLevel < 0) {
            this.dangerLevel = 0;
        } else {
            this.dangerLevel = 10;
        }

        this.connectedLanes = new ArrayList<HyperspaceLane>();
    }

    public String getName() {
        return name;
    }

    public String getFaction() {
        return faction;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    /**
     * Adds a given hyperspace lane to the connectedLanes ArrayList for the current
     * star system if it is not null, if it doesn't already exist in the list,
     * and if the given lane contains the current system's name.
     * 
     * @param lane the hyperspace lane to be added to the current star system.
     */
    public void addLane(HyperspaceLane lane) {
        if (lane != null) {
            // Check whether the given hyperspace lane already exists in system.
            boolean alreadyExists = false;
            for (HyperspaceLane currLane : this.connectedLanes) {
                if (currLane.equals(lane)) {
                    alreadyExists = true;
                }
            }

            // Check whether the system's name is contained in the given hyperspace lane.
            boolean containsName = this.name.equals(lane.getFromSystem()) || this.name.equals(lane.getToSystem());

            // Add the hyperspace lane if all conditions are met.
            if (!alreadyExists && containsName) {
                this.connectedLanes.add(lane);
            }
        }
    }

    public ArrayList<HyperspaceLane> getConnectedLanes() {
        return new ArrayList<HyperspaceLane>(this.connectedLanes);
    }

    /**
     * Returns an ArrayList containing the name of all star systems connected
     * to the current instance via hyperspace lanes. Iterates through every
     * hyperspace lane in connectedLanes, finds the system in that lane that
     * differs from the current instance, and adds it to the list if it hasn't
     * already been added.
     * 
     * @return an ArrayList of String containing the name of all neighboring
     *         star systems.
     */
    public ArrayList<String> getNeighboringSystems() {
        ArrayList<String> neighboringSystems = new ArrayList<String>();
        for (HyperspaceLane lane : connectedLanes) {
            // Find the system (origin or destination) in the current hyperspace
            // lane that differs from the current star system.
            String laneName;
            if (this.name.equals(lane.getToSystem())) {
                laneName = lane.getFromSystem();
            } else {
                laneName = lane.getToSystem();
            }

            // Add the lane to neighboringSystems if it doesn't already exist in
            // the ArrayList.
            if (!neighboringSystems.contains(laneName)) {
                neighboringSystems.add(laneName);
            }
        }

        return neighboringSystems;
    }

    /**
     * Checks whether a given star system has the same name as the current
     * instance.
     * 
     * @param other the star system whose name should be compared to that of the
     *              current instance.
     * @return true if the names match; false otherwise (or if the given star
     *         system is null).
     */
    public boolean equals(StarSystem other) {
        boolean isEqual = false;
        if (other != null) {
            isEqual = this.name.equals(other.getName());
        }
        return isEqual;
    }

    /**
     * Returns the lexicographic comparison of the current star system's name to
     * a given star system's name. Throws a NullPointerException if the given
     * star system is null.
     * 
     * @param other the star system to be compared to the current instance.
     * @return the lexicographic comparison of the current star system's name to
     *         the other star system's name (as an integer).
     */
    public int compareTo(StarSystem other) {
        int compareTo;
        if (other != null) {
            compareTo = this.name.compareTo(other.getName());
        } else {
            throw new NullPointerException("Cannot compare with a null object.");
        }
        return compareTo;
    }

    /**
     * Returns a String representation of the current star system, which includes
     * its name, its factions, and its danger level.
     * 
     * @return a string representation of the current star system.
     */
    public String toString() {
        return String.format("StarSystem[name=%s, faction=%s, dangerLevel=%d]", this.name, this.faction,
                this.dangerLevel);
    }

    /**
     * Sorts a given ArrayList of StarSystem using selection sort. Sorting is
     * based on the lexicographic value of the star system's name.
     * 
     * @param systems the ArrayList of StarSystem to be sorted.
     */
    public static void selectionSortByName(ArrayList<StarSystem> systems) {
        for (int i = 0; i < systems.size() - 1; i++) {
            selectionSortRound(systems, i);
        }
    }

    /**
     * Performs a single round of selection sort. Finds the index of the starship
     * whose name has the smallest lexicographic value, starting from a given index,
     * then swaps the starship at the given index with the starship at the
     * minimum index.
     * 
     * @param systems the ArrayList of StarSystem on which a single round of
     *                selection sort should be performed.
     * @param i       the starting index.
     */
    public static void selectionSortRound(ArrayList<StarSystem> systems, int i) {
        // Get the index of the minimum element.
        int minIndex = i;

        for (int j = i + 1; j < systems.size(); j++) {
            if (systems.get(j).getName().compareTo(systems.get(minIndex).getName()) < 0) {
                minIndex = j;
            }
        }

        // Swap the minimum element and the element at index i, if necessary.
        if (minIndex != i) {
            StarSystem temp = systems.get(minIndex);
            systems.set(minIndex, systems.get(i));
            systems.set(i, temp);
        }
    }
}
