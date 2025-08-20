
/**
 * LifeSupportModule.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, 1
 * 
 * @author Michel Préjet
 * @version July 14th, 2025
 * 
 *          PURPOSE: Represents a life support module of a starship. Contains
 *          CO2 level and O2 level variables, and simulates behaviours such as 
 *          suffering damage, undergoing maintenance, and undertaking 
 *          expeditions.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.Random;

public class LifeSupportModule {

    private String name;
    private double currentCO2ppm;
    private int currentO2Level;
    private int maxO2Level;
    private static final double MAX_CO2PPM = 729;
    private static final int MAX_O2_REDUCE = 15;
    private static final double DAMAGE_CO2PPM_FACTOR = 2;
    private static final double DEFAULT_CO2PPM_FACTOR = 0.04;

    /**
     * Constructs a new LifeSupportModule with a name and a maximum O2 level.
     * Initial O2 level is set to full and initial CO2 level is set to 0 by
     * default.
     * 
     * @param name    the name of the LifeSupportModule.
     * @param o2level the maximum O2 level of the LifeSupportModule.
     */
    public LifeSupportModule(String name, int o2level) {
        this.name = name;
        this.maxO2Level = o2level;
        this.currentO2Level = o2level;
        this.currentCO2ppm = 0;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Checks whether the LifeSupportModule is functional - that is, its CO2
     * level does not exceed the maximum value allowed, and its oxygen supply
     * is not empty.
     * 
     * @return true if the LifeSupportModule has oxygen and its CO2 levels are
     *         not excessive; false otherwise.
     */
    public boolean isWorking() {
        return (this.currentCO2ppm < MAX_CO2PPM) && (this.currentO2Level > 0);
    }

    /**
     * Simulates damage to the LifeSupportModule by multiplying the CO2 level
     * by DAMAGE_CO2PPM_FACTOR.
     */
    public void damage() {
        this.currentCO2ppm *= DAMAGE_CO2PPM_FACTOR;
    }

    /**
     * Simulates performing maintenance on the LifeSupportModule by setting the
     * CO2 level to 0 and setting the O2 level to the maximum amount.
     */
    public void performMaintenance() {
        this.currentCO2ppm = 0;
        this.currentO2Level = this.maxO2Level;
    }

    /**
     * Simulates the LifeSupportModule completing an expedition by subtracting a
     * random amount of O2 from currentO2Level and then increasing the CO2 level
     * by a related amount.
     * 
     * @param random random object used to generate the subtracted value.
     */
    public void completeExpedition(Random random) {
        int o2Reduced = random.nextInt(MAX_O2_REDUCE + 1);
        this.currentO2Level = Math.max(this.currentO2Level - o2Reduced, 0);
        this.currentCO2ppm += o2Reduced * DEFAULT_CO2PPM_FACTOR;
    }

    /**
     * Produces a new instance of this LifeSupportModule with the same name and
     * the same O2 capacity. Does not carry over the actual O2 and CO2 levels -
     * sets the O2 level to full and the CO2 level to 0 by default.
     * 
     * @return a clone of the current LifeSupportModule instance.
     */
    public LifeSupportModule clone() {
        return new LifeSupportModule(this.name, this.maxO2Level);
    }

    /**
     * Returns a string representation of the current LifeSupportModule object,
     * which includes its name, O2 level, CO2 level, and whether it is
     * functional.
     * 
     * @return a string representation of the current LifeSupportModule object.
     */
    public String toString() {
        String result;

        // Return one of two different strings depending on the value returned
        // by isWorking().
        if (isWorking()) {
            result = String.format("Life Support Module \"%s\" [Status: Cleaning, O2: %d/%d, CO2: %.2f ppm]", this.name,
                    this.currentO2Level, this.maxO2Level, this.currentCO2ppm);
        } else {
            result = String.format("Life Support Module \"%s\" [Status: Spewing Toxic Fumes, O2: %d/%d, CO2: %.2f ppm]",
                    this.name, this.currentO2Level, this.maxO2Level, this.currentCO2ppm);

        }

        return result;
    }
}
