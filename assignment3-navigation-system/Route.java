
/**
 * Route.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 3, 3
 * 
 * @author Michel Préjet
 * @version July 31st, 2025
 * 
 *          PURPOSE: Represents a full Route taken by a starship, including an 
 *          ArrayList of the star systems it visits, its total distance, its
 *          total cost, its total danger, whether or not an enemy faction was
 *          encountered, and whether or not it avoids all tolls. Provides getter
 *          methods for each of these instance variables, as well as methods to
 *          add a hop (a trip through a hyperspace lane in a starship to a
 *          destination star system) and to check whether a given route follows
 *          the same sequence of star systems as the current instance.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class Route {
    private ArrayList<String> systemsVisited;
    private double totalDistance;
    private double totalCost;
    private int totalDanger;
    private boolean enemyEncountered;
    private boolean tollFree;

    /**
     * Constructs a new Route with an empty ArrayList of String representing
     * the systems it visits, a default value of 0 for its total distance, its
     * total cost, and its total danger, a default value of false for
     * whether an enemy has been encountered, and a default value of true for
     * whether the route is toll free.
     */
    public Route() {
        this.systemsVisited = new ArrayList<String>();
        this.totalDistance = 0;
        this.totalCost = 0;
        this.totalDanger = 0;
        this.enemyEncountered = false;
        this.tollFree = true;
    }

    /**
     * Constructs a new Route as a deep copy of a given route, with the same
     * ArrayList of String representing the systems it visits, the same total
     * distance, total cost, and total danger, the same value for whether an
     * enemy is encountered, and the same value for whether it is toll free.
     */
    public Route(Route other) {
        this.systemsVisited = new ArrayList<String>(other.getSystemsVisited());
        this.totalDistance = other.getTotalDistance();
        this.totalCost = other.getTotalCost();
        this.totalDanger = other.getTotalDanger();
        this.enemyEncountered = other.hasEnemyEncounter();
        this.tollFree = other.isTollFree();
    }

    /**
     * Adds a hop to the current route - that is, a trip through a given hyperspace
     * lane to a given star system in a given starship. If the destination system
     * is connected to the hyperspace lane, and if that system hasn't already been
     * added to the route, adds it to systemsVisited and updates all instance
     * variables for the current route according to information drawn from the
     * destination system, the hyperspace lane, and the starship.
     * 
     * @param destStarSystem    the destination star system of the hop.
     * @param laneToGetToSystem the hyperspace lane of the hop.
     * @param ship              the starship for the hop.
     */
    public void addHop(StarSystem destStarSystem, HyperspaceLane laneToGetToSystem, Starship ship) {
        // Only add hop if the given destination system is contained within the given
        // hyperspace lane, and if the system hasn't already been added.
        if (laneToGetToSystem.containsSystem(destStarSystem.getName())
                && !this.systemsVisited.contains(destStarSystem.getName())) {
            this.systemsVisited.add(destStarSystem.getName());

            // Update instance variables for the route according to the added
            // system's instance variables.
            this.totalDistance += laneToGetToSystem.getDistance();
            this.totalCost += (ship.getFuelCost(laneToGetToSystem.getDistance()) + laneToGetToSystem.getTollCost());
            this.totalDanger += destStarSystem.getDangerLevel();

            // If no enemy has already been encountered, check if the faction of
            // the added system is an enemy of the given ship and update
            // enemyEncountered accordingly.
            if (!this.enemyEncountered) {
                if (ship.getEnemyFactions().contains(destStarSystem.getFaction())) {
                    this.enemyEncountered = true;
                }
            }

            // If no tolls have already been encountered, check if the given
            // lane is toll free and update tollFree accordingly.
            if (this.tollFree && laneToGetToSystem.getTollCost() > 0) {
                this.tollFree = false;
            }

        }
    }

    public ArrayList<String> getSystemsVisited() {
        return new ArrayList<String>(this.systemsVisited);
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getTotalDanger() {
        return totalDanger;
    }

    public boolean hasEnemyEncounter() {
        return enemyEncountered;
    }

    public boolean isTollFree() {
        return tollFree;
    }

    /**
     * Checks whether a given route has the same list of star systems travelled
     * as the current instance (order is significant).
     * 
     * @param other the route to be compared with the current instance.
     * @return true if the given route has the same sequence of star systems;
     *         false otherwise.
     */
    public boolean equals(Route other) {
        boolean isEqual = true;

        if (this.systemsVisited.size() != other.getSystemsVisited().size()) {
            isEqual = false;
        } else {
            for (int i = 0; i < this.systemsVisited.size(); i++) {
                if (!this.systemsVisited.get(i).equals(other.getSystemsVisited().get(i))) {
                    isEqual = false;
                }
            }
        }

        return isEqual;
    }

    /**
     * Returns a String representation of the current route, including the list
     * of star systems it visits, its total distance, its total danger level,
     * its total cost, whether it involves enemy encounters, and whether it is
     * toll free.
     * 
     * @return a String representation of the current route.
     */
    public String toString() {
        String output = "Route: ";

        // Concatenate the name of all star systems visited.
        for (int i = 0; i < this.systemsVisited.size() - 1; i++) {
            output += this.systemsVisited.get(i) + " -> ";
        }
        output += this.systemsVisited.get(systemsVisited.size() - 1) + "\n";

        // Concatenate route statistics.
        String yesNoEnemy = "NO";
        if (this.enemyEncountered) {
            yesNoEnemy = "YES";
        }

        String yesNoTollFree = "NO";
        if (this.tollFree) {
            yesNoTollFree = "YES";
        }

        output += String.format(
                "Distance: %.2f LY | Danger: %d | Enemy Encountered: %s | Toll Free: %s | Total Cost: %.2f cr",
                this.totalDistance, this.totalDanger, yesNoEnemy, yesNoTollFree, this.totalCost);

        return output;
    }
}
