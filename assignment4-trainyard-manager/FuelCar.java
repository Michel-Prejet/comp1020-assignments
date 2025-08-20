/**
 * FuelCar.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, 2
 * 
 * @author Michel Préjet
 * @version August 4th, 2025
 * 
 *          PURPOSE: Represents a FuelCar on a train, including its current fuel
 *          level, its maximum fuel level, and the next train car. Provides
 *          getter methods for the maximum fuel level and the next car, as well
 *          as methods to get the current car's type, set the next car, add
 *          fuel, and consume fuel.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

public class FuelCar implements iTrainCar {
    private int currentFuelLevel;
    private iTrainCar next;
    private final int maxFuelLevel;

    /**
     * Constructs a new FuelCar with a given maximum fuel capacity. Sets the
     * current fuel level to 0 by default. Throws an IllegalArgumentException
     * if the given maximum fuel level is less than or equal to 0.
     * 
     * @param maxFuelLevel the maximum fuel level of the fuel car.
     */
    public FuelCar(int maxFuelLevel) {
        if (maxFuelLevel <= 0) {
            throw new IllegalArgumentException("Maximum fuel level must be greater than 0.");
        }

        this.maxFuelLevel = maxFuelLevel;
        this.currentFuelLevel = 0;
    }

    public String getType() {
        return iTrainCar.FUEL;
    }

    public int getMaxCapacity() {
        return this.maxFuelLevel;
    }

    public iTrainCar getNext() {
        return this.next;
    }

    public void setNext(iTrainCar next) {
        this.next = next;
    }

    /**
     * Adds a given amount of fuel to the fuel car, if doing so does not cause the
     * fuel to car to exceed the maximum fuel level. Throws an
     * IllegalArgumentException if the given amount of fuel is not positive.
     * 
     * @param amount the amount of fuel to be added to the current fuel car.
     * @return true if the given amount of fuel would not have caused the current
     *         level to exceed the maximum level; false otherwise.
     * 
     */
    public boolean refuel(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Fuel amount must be positive.");
        }

        boolean notOverfull = this.currentFuelLevel + amount <= this.maxFuelLevel;
        if (notOverfull) {
            this.currentFuelLevel += amount;
        }

        return notOverfull;
    }

    /**
     * Subtracts a given amount of fuel from the fuel car, if doing so does not
     * cause the fuel level to go below 0. Throws an IllegalArgumentException if
     * the given amount of fuel is not positive.
     * 
     * @param amount the amount of fuel that the current car will consume.
     * @return true if the given amount of fuel would not have caused the current
     *         level to be less than or equal to 0; false otherwise.
     */
    public boolean consumeFuel(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Fuel amount must be positive.");
        }

        boolean notBelowEmpty = this.currentFuelLevel - amount >= 0;
        if (notBelowEmpty) {
            this.currentFuelLevel -= amount;
        }

        return notBelowEmpty;
    }

    /**
     * Returns a visual representation of the current fuel level and the
     * maximum fuel level for the current fuel car using hashtags and spaces.
     * 
     * @return a visual representation of the current fuel car's fuel
     *         variables.
     */
    public String toStringContents() {
        String output = "Fuel: [";
        for (int i = 0; i < this.maxFuelLevel; i++) {
            if (i < currentFuelLevel) {
                output += "#";
            } else {
                output += " ";
            }
        }
        return output + "]";
    }

    /**
     * Returns a String representation of the current fuel car, including its
     * current and maximum fuel levels.
     * 
     * @return a String representation of the current fuel car.
     */
    public String toString() {
        return String.format("FuelCar (%d/%d)", this.currentFuelLevel, this.maxFuelLevel);
    }
}
