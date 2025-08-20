
/**
 * Starship.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 3, 1
 * 
 * @author Michel Préjet
 * @version July 29th, 2025
 * 
 *          PURPOSE: Represents a Starship in a starship navigation system, 
 *          including its name, its faction, the cost of the ship per antimatter 
 *          unit, the number of antimatter units it uses per light year, and a
 *          list of its enemy factions. Provides getter methods for each of
 *          these instance variables, as well as methods to check whether a
 *          given enemy is in the enemy list and whether a given starship has the
 *          same name as the current one.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class Starship {
    private String name;
    private String faction;
    private double costPerAntimatterUnit; // credits per AMU
    private double antimatterPerLY; // AMUs per lightyear
    private ArrayList<String> enemyFactions;

    /**
     * Constructs a new Starship with a given name, faction, cost per antimatter
     * unit, antimatter per light year, and list of enemy factions. If the name,
     * faction, or enemy factions list are null, or if the cost per antimatter
     * unit or the antimatter per light year values are negative, throws an
     * IllegalArgumentException.
     * 
     * @param name                  the name of the starship.
     * @param faction               the faction to which the starship belongs.
     * @param costPerAntimatterUnit the cost per antimatter unit for the starship.
     * @param antimatterPerLY       the antimatter used by the starship per light
     *                              year.
     * @param enemyFactions         an ArrayList of the starship's enemy factions.
     */
    public Starship(String name, String faction, double costPerAntimatterUnit,
            double antimatterPerLY, ArrayList<String> enemyFactions) {
        if (name != null && faction != null && enemyFactions != null) {
            this.name = name;
            this.faction = faction;
            this.enemyFactions = new ArrayList<>(enemyFactions);
        } else {
            throw new IllegalArgumentException("Starship constructor cannot accept null arguments.");
        }

        if (costPerAntimatterUnit >= 0 && antimatterPerLY >= 0) {
            this.costPerAntimatterUnit = costPerAntimatterUnit;
            this.antimatterPerLY = antimatterPerLY;
        } else {
            throw new IllegalArgumentException(
                    "Starship constructor cannot accept negative arguments.");
        }
    }

    public String getName() {
        return name;
    }

    public String getFaction() {
        return faction;
    }

    public double getCostPerAntimatterUnit() {
        return costPerAntimatterUnit;
    }

    public double getAntimatterPerLY() {
        return antimatterPerLY;
    }

    public ArrayList<String> getEnemyFactions() {
        return new ArrayList<>(this.enemyFactions);
    }

    /**
     * Checks whether a given faction is in the enemy list for the starship.
     * 
     * @param otherFaction the faction to be searched for in the enemy list.
     * @return true if the given faction was found in the enemy list; false
     *         otherwise.
     */
    public boolean isEnemy(String otherFaction) {
        boolean isEnemy = false;
        if (otherFaction != null) {
            for (String enemyName : this.enemyFactions) {
                if (enemyName.equals(otherFaction)) {
                    isEnemy = true;
                }
            }
        }
        return isEnemy;
    }

    /**
     * Returns the total credit cost of fuel for travelling a given number of
     * light years. Multiplies the given distance by the cost per AMU and the
     * AMU per light year. Throws an IllegalArgumentException if the given
     * distance is negative.
     * 
     * @param distance the distance to be travlled, in light years.
     * @return the total credit cost of fuel to travel the given distance.
     */
    public double getFuelCost(double distance) {
        double toReturn;
        if (distance >= 0) {
            toReturn = distance * this.costPerAntimatterUnit * this.antimatterPerLY;
        } else {
            throw new IllegalArgumentException("Cannot calculate fuel for a negative distance.");
        }
        return toReturn;
    }

    /**
     * Checks whether a given starship has the same name as the current
     * starship (case-insensitive).
     * 
     * @param other the starship whose name should be compared to the current
     *              starship.
     * @return true if the names match; false otherwise.
     */
    public boolean equals(Starship other) {
        boolean isEqual = false;
        if (other != null && this.name.toLowerCase().trim().equals(other.getName().toLowerCase().trim())) {
            isEqual = true;
        }
        return isEqual;
    }

    /**
     * Returns a String representation of the current starship including its
     * name, its factions, its AMU per light year, its cost per AMU, and its
     * enemy factions.
     * 
     * @return a String representation of the current starship.
     */
    public String toString() {
        String output = String.format("Starship[name=%s, faction=%s, fuel=%.2f AMU/LY at %.2f cr/AMU, enemies=[",
                this.name, this.faction, this.antimatterPerLY, this.costPerAntimatterUnit);

        // Concatenate information about each enemy faction.
        if (this.enemyFactions.size() > 0) {
            for (int i = 0; i < this.enemyFactions.size() - 1; i++) {
                output += this.enemyFactions.get(i) + ", ";
            }
            output += this.enemyFactions.get(this.enemyFactions.size() - 1);
        }
        output += "]]";

        return output;
    }
}
