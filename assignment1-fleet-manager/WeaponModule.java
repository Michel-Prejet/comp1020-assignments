
/**
 * WeaponModule.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, 1
 * 
 * @author Michel Préjet
 * @version July 14th, 2025
 * 
 *          PURPOSE: Represents a weapon module of a starship. Contains ammo
 *          count variables, and simulates behaviours such as suffering damage,
 *          undergoing maintenance, and undertaking expeditions.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.Random;

public class WeaponModule {

    private String name;
    private int maxAmmoCount;
    private int currentAmmoCount;
    private static final int MAX_AMMO_FIND = 15;
    private static final int MAX_AMMO_REDUCE_FACTOR = 3;

    /**
     * Constructs a new WeaponModule with a name and maximum ammo level.
     * Initial ammo level is set to full by default.
     * 
     * @param name         the name of the WeaponModule.
     * @param maxAmmoLevel the maximum ammo level of the WeaponModule.
     */
    public WeaponModule(String name, int maxAmmoLevel) {
        this.name = name;
        this.currentAmmoCount = maxAmmoLevel;
        this.maxAmmoCount = maxAmmoLevel;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Checks whether the WeaponModule is functional - that is, its ammo
     * supplies are not depleted.
     * 
     * @return true if the WeaponModule has ammo left; false otherwise.
     */
    public boolean isWorking() {
        return this.currentAmmoCount > 0;
    }

    /**
     * Simulates damage to the WeaponModule by emptying its ammo supplies.
     */
    public void damage() {
        this.currentAmmoCount = 0;
    }

    /**
     * Simulates performing maintenance on the WeaponModule by setting
     * currentAmmoCount to the maximum amount.
     */
    public void performMaintenance() {
        this.currentAmmoCount = this.maxAmmoCount;
    }

    /**
     * Simulates the WeaponModule completing an expedition by subtracting a
     * random amount of ammo from currentAmmoCount, then increasing
     * currentAmmoCount by another random amount.
     * 
     * @param random random object used to generate the subtracted and added
     *               values.
     */
    public void completeExpedition(Random random) {
        // Subtract a random amount of ammo.
        int ammoReduced = random.nextInt(MAX_AMMO_REDUCE_FACTOR + 1) * 10;
        this.currentAmmoCount = Math.max(this.currentAmmoCount - ammoReduced, 0);

        // Adda random amount of ammo.
        this.currentAmmoCount += random.nextInt(MAX_AMMO_FIND + 1);
        this.currentAmmoCount = Math.min(maxAmmoCount, currentAmmoCount);
    }

    /**
     * Produces a new instance of this WeaponModule with the same name and the
     * same ammo capacity. Does not carry over the value of currentAmmoCount.
     * 
     * @return a clone of the current WeaponModule instance.
     */
    public WeaponModule clone() {
        return new WeaponModule(this.name, this.maxAmmoCount);
    }

    /**
     * Returns a string representation of the current WeaponModule object,
     * which includes its name, ammo level, and whether it is functional.
     * 
     * @return a string representation of the current WeaponModule object.
     */
    public String toString() {
        String result;

        // Return one of two different strings depending on the value returned
        // by isWorking().
        if (isWorking()) {
            result = String.format("Weapon Module \"%s\" [Status: Operational, Ammo: %d/%d]", name, currentAmmoCount,
                    maxAmmoCount);
        } else {
            result = String.format("Weapon Module \"%s\" [Status: Empty, Ammo: %d/%d]", name, currentAmmoCount,
                    maxAmmoCount);

        }

        return result;
    }
}