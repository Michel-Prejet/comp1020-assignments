
/**
 * EngineModule.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, 1
 * 
 * @author Michel Préjet
 * @version July 13th, 2025
 * 
 *          PURPOSE: Represents an engine module of a starship. Contains thrust 
 *          and fuel level variables, and simulates behaviours such as suffering 
 *          damage, undergoing maintenance, and undertaking expeditions. 
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.Random;

public class EngineModule {

    private String name;
    private boolean hasThrust;
    private int maxFuelLevel;
    private int currentFuelLevel;
    private static final int MAX_FUEL_REDUCE = 10;

    /**
     * Constructs a new EngineModule with a name and a maximum fuel level.
     * Initial fuel level is set to full and hasThrust is set to true by
     * default.
     * 
     * @param name      the name of the EngineModule.
     * @param fuelLevel the maximum fuel level of the EngineModule.
     */
    public EngineModule(String name, int fuelLevel) {
        this.name = name;
        this.hasThrust = true;
        this.maxFuelLevel = fuelLevel;
        this.currentFuelLevel = fuelLevel;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Checks whether the EngineModule is functional - that is, it has both
     * thrust and fuel.
     * 
     * @return true if the EngineModule has thrust and fuel; false otherwise.
     */
    public boolean isWorking() {
        return this.hasThrust && this.currentFuelLevel > 0;
    }

    /**
     * Simulates damage to the EngineModule by setting hasThrust to false.
     */
    public void damage() {
        this.hasThrust = false;
    }

    /**
     * Simulates performing maintenance on the EngineModule by setting hasThrust
     * to true and setting currentFuelLevel to maxFuelLevel.
     */
    public void performMaintenance() {
        this.hasThrust = true;
        this.currentFuelLevel = this.maxFuelLevel;
    }

    /**
     * Simulates the EngineModule completing an expedition by subtracting
     * a random amount of fuel from currentFuelLevel.
     * 
     * @param random random object used to generate the subtracted value.
     */
    public void completeExpedition(Random random) {
        int fuelReduced = random.nextInt(MAX_FUEL_REDUCE + 1);
        this.currentFuelLevel = Math.max(this.currentFuelLevel - fuelReduced, 0);
    }

    /**
     * Produces a new instance of this EngineModule with the same name and the
     * same fuel capacity. Does not carry over hasThrust and currentFuelLevel
     * values.
     * 
     * @return a clone of the current EngineModule instance.
     */
    public EngineModule clone() {
        return new EngineModule(this.name, this.maxFuelLevel);
    }

    /**
     * Returns a string representation of the current EngineModule object,
     * which includes its name, thrust status, fuel level, and whether it is
     * functional.
     * 
     * @return a string representation of the current EngineModule object.
     */
    public String toString() {
        String result;

        // Return one of two different strings depending on the value returned
        // by isWorking().
        if (isWorking()) {
            result = String.format("Engine Module \"%s\" [Status: Working, Thrust: %b, Fuel: %d/%d]", this.name,
                    this.hasThrust,
                    this.currentFuelLevel, this.maxFuelLevel);
        } else {
            result = String.format("Engine Module \"%s\" [Status: Damaged, Thrust: %b, Fuel: %d/%d]", this.name,
                    this.hasThrust,
                    this.currentFuelLevel, this.maxFuelLevel);

        }

        return result;
    }
}
