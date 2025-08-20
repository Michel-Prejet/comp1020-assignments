
/**
 * FleetManager.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, 4
 * 
 * @author Michel Préjet
 * @version July 14th, 2025
 * 
 *          PURPOSE: Represents a Fleet Manager, including the fleet's starships
 *          and expeditions, and their maximum amounts. Simulates behaviours such
 *          as adding a starship to the fleet, sending a specific starship on
 *          an expedition, adding a copy of a starship to the fleet, searching
 *          the fleet for a specific starship, and listing all starships or
 *          expeditions in the fleet.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.Random;

public class FleetManager {

    private int maxShips;
    private Starship[] starships;
    private int starshipCount;
    private Expedition[] expeditions;
    private int expeditionCount;
    private final int MAX_EXPEDITIONS = 100;

    /**
     * Constructs a new FleetManager with two PFAs containing the fleet's
     * starships and expeditions. Instantiates the starships array using the
     * maxShips parameter as its length, and the expeditions array using the
     * MAX_EXPEDITIONS constant as its length. The number of starships and
     * expeditions are both set to 0 by default.
     * 
     * @param maxShips the maximum number of starships that can be stored in the
     *                 fleet.
     */
    public FleetManager(int maxShips) {
        this.maxShips = maxShips;
        this.starships = new Starship[maxShips];
        this.expeditions = new Expedition[MAX_EXPEDITIONS];
        this.starshipCount = 0;
        this.expeditionCount = 0;
    }

    /**
     * Instantiates a new starship object with a given name and module capacity,
     * then adds it to the fleet (if there is enough space to do so).
     * 
     * @param name     the name of the starship to be added.
     * @param capacity the module capacity of the Starship to be added.
     * @return true if the starship was successfully added to the fleet; false
     *         otherwise.
     */
    public boolean addStarship(String name, int capacity) {
        boolean returnVal = false;
        if (this.starshipCount < this.maxShips) {
            this.starships[this.starshipCount++] = new Starship(name, capacity);
            returnVal = true;
        }
        return returnVal;
    }

    /**
     * Finds a starship in the fleet with a given name, then makes a deep
     * copy with a new name and adds it to the fleet, if there is space to do
     * so.
     * 
     * @param sourceName the name of the starship to be copied.
     * @param newName    the name of the copied starship.
     * @return true if a copy was successfully added to the fleet; false
     *         otherwise.
     */
    public boolean copyStarship(String sourceName, String newName) {
        Starship currShip = null;
        boolean returnVal = false;

        // Find the starship with the given sourceName.
        boolean found = false;
        for (int i = 0; i < this.starshipCount && !found; i++) {
            if (this.starships[i].getBaseName().equals(sourceName)) {
                currShip = this.starships[i];
                found = true;
            }
        }

        // Add a copy of the starship to the fleet, if it was found and if
        // there is enough space.
        if (found && (this.starshipCount < this.maxShips)) {
            this.starships[this.starshipCount++] = currShip.copy(newName);
            returnVal = true;
        }

        return returnVal;
    }

    /**
     * Searches the fleet for a starship with a given name, then returns it.
     * 
     * @param name the name of the starship to be retrieved.
     * @return the starship with the given name, or null if no such starship
     *         was found in the fleet.
     */
    public Starship findStarshipByName(String name) {
        Starship returnShip = null;

        // Search the starships array (from newest to oldest) for a ship
        // that matches the given name.
        boolean found = false;
        for (int i = this.starshipCount - 1; i >= 0 && !found; i--) {
            if (this.starships[i].getName().equals(name) || this.starships[i].getBaseName().equals(name)) {
                returnShip = this.starships[i];
                found = true;
            }
        }

        // If no match is found, search the starships array again for a
        // partial match.
        boolean found2 = false;
        if (!found) {
            for (int i = this.starshipCount - 1; i >= 0 && !found2; i--) {
                if (this.starships[i].getBaseName().indexOf(name) != -1) {
                    returnShip = this.starships[i];
                    found2 = true;
                }
            }
        }

        // Returns null if no starship was found.
        return returnShip;
    }

    /**
     * Returns a string containing the name of each starship in the fleet on a
     * newline.
     * 
     * @return a string containing the name of each starship in the fleet.
     */
    public String listStarships() {
        String result = "";
        for (int i = 0; i < starshipCount; i++) {
            result += starships[i].getName() + "\n";
        }
        return result;
    }

    /**
     * Simulates sending a given starship in the fleet on an expedition. If a
     * functional starship matching a given name is found in the fleet, it goes
     * on an expedition which gets stored in the current FleetManager.
     * 
     * @param ship   the starship to be sent on an expedition.
     * @param random random object passed to the expedition object's constructor.
     * @return the expedition that was undertaken, or null if no functional
     *         starship was found to go on the expedition.
     */
    public Expedition sendExpedition(Starship ship, Random random) {
        // Find the starship corresponding to the given name.
        Starship currStarship = findStarshipByName(ship.getName());

        Expedition returnExpedition = null;

        // If a functional starship was found, send it on an expedition and
        // store that expedition.
        if (currStarship != null && currStarship.hasWorkingRequiredModules()) {
            returnExpedition = new Expedition(currStarship, random);
            this.expeditions[this.expeditionCount++] = returnExpedition;
        }

        // Return null if no functional starship was found.
        return returnExpedition;
    }

    /**
     * Returns information about the fleet's expeditions. If the fleet has
     * undertaken any expeditions, the toString() for each expedition is
     * returned; otherwise, "No expeditions yet." is returned.
     * 
     * @return a description of each expedition undertaken by the fleet; or
     *         "No expeditions yet." if no expedition have occurred.
     */
    public String getExpeditionLog() {
        String result = "";
        if (this.expeditionCount == 0) {
            result = "No expeditions yet.";
        } else {
            for (int i = 0; i < this.expeditionCount; i++) {
                result += this.expeditions[i].toString() + "\n";
            }
        }
        return result;
    }

    /**
     * Returns a string representation of the current FleetManager object, which
     * includes the number of starships and expeditions, the name of each
     * starship, and a description of each expedition.
     * 
     * @return a string representation of the current FleetManager object.
     */
    public String toString() {
        String result = String.format("Fleet Status:\n\nStarships in Fleet: %d/%d\nShips:\n------\n",
                this.starshipCount,
                this.maxShips);

        // Concatenate the name of each starship in the fleet.
        for (int i = 0; i < this.starshipCount; i++) {
            result += this.starships[i].getName() + "\n";
        }

        result += String.format("\nExpeditions Sent: %d/%d\nExpedition Log:\n---------------\n", expeditionCount,
                MAX_EXPEDITIONS);

        // Concatenate information about each expedition undertaken by the
        // starships in the fleet.
        for (int i = 0; i < this.expeditionCount; i++) {
            result += this.expeditions[i].toString() + "\n";
        }

        return result;
    }
}
