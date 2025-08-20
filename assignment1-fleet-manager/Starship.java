
/**
 * Starship.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, 2
 * 
 * @author Michel Préjet
 * @version July 14th, 2025
 * 
 *          PURPOSE: Represents a Starship, including its name, its modules,
 *          and the number of planets it has discovered. Simulates behaviours
 *          such as undertaking expeditions, discovering planets, suffering
 *          damage from combat, undergoing maintenance, and acquiring new
 *          modules.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.Random;

public class Starship {

    private String name; // Name with CSS
    private String baseName; // Name without CSS
    private int numPlanetDiscoveries;
    private int maxNumModules;
    private Object[] modules;
    private int moduleCount;
    private final int MIN_MODULE_CAPACITY = 2;

    /**
     * Constructs a new Starship with a name (containing the prefix "CSS") and a
     * module capacity. Instantiates an array whose size reflects the specified
     * capacity, but the actual number of modules is 0 by default.
     * 
     * @param name           the name of the starship.
     * @param moduleCapacity the maximum number of modules the starship can
     *                       contain.
     */
    public Starship(String name, int moduleCapacity) {
        this.baseName = name;
        this.name = "CSS " + name;
        this.maxNumModules = Math.max(MIN_MODULE_CAPACITY, moduleCapacity);
        this.modules = new Object[this.maxNumModules];
        this.moduleCount = 0;
    }

    public String getName() {
        return this.name;
    }

    public String getBaseName() {
        return this.baseName;
    }

    /**
     * Adds a module to the starship if its module capacity has not been
     * exceeded, and returns a boolean indicating whether or not the addition
     * of the module was successful.
     * 
     * @param module the module to be added to the starship.
     * @return true if the module was added successfully; false otherwise.
     */
    public boolean addModule(Object module) {
        boolean returnVal;

        // Add the module to the modules array if there is space left.
        if (this.moduleCount < this.maxNumModules) {
            this.modules[this.moduleCount++] = module;
            returnVal = true;
        } else {
            returnVal = false;
        }

        return returnVal;
    }

    /**
     * Provides information about the status of every module by calling each
     * module's respective toString() method.
     * 
     * @return a string containing information about the status of each module
     *         on the starship.
     */
    public String showAllStatuses() {
        String result = "";
        for (int i = 0; i < this.moduleCount; i++) {
            result += this.modules[i].toString() + "\n";
        }

        return result;
    }

    /**
     * Checks if the starship is functional - that is, it has at least one
     * EngineModule and at least one LifeSupportModule.
     * 
     * @return true if the starship has at least one EngineModule and at least
     *         one LifeSupportModule; false otherwise.
     */
    public boolean hasWorkingRequiredModules() {
        boolean hasEngine = false;
        boolean hasLife = false;

        // Iterate through the modules array, setting hasEngine to true if an
        // EngineModule is found, and setting hasLife to true if a
        // LifeSupportModule is found.
        for (int i = 0; i < this.moduleCount; i++) {
            if (this.modules[i] instanceof EngineModule) {
                hasEngine = true;
            } else if (this.modules[i] instanceof LifeSupportModule) {
                hasLife = true;
            }
        }

        return hasEngine && hasLife;
    }

    /**
     * Simulates the starship losing in combat by randomly selecting one of its
     * modules and damaging it.
     * 
     * @param random random object used to randomly select a module.
     * @return a string indicating which module was damaged.
     */
    public String combatLost(Random random) {
        // Select a random module to be damaged in the modules array.
        int index = random.nextInt(moduleCount);
        Object moduleSelected = modules[index];

        // Damage the selected module by casting it to the appropriate type and
        // calling its damage() method.
        String returnVal = "";
        if (moduleSelected instanceof EngineModule) {
            ((EngineModule) moduleSelected).damage();
            returnVal = ((EngineModule) moduleSelected).getName();
        } else if (moduleSelected instanceof StorageModule) {
            ((StorageModule) moduleSelected).damage();
            returnVal = ((StorageModule) moduleSelected).getName();
        } else if (moduleSelected instanceof LifeSupportModule) {
            ((LifeSupportModule) moduleSelected).damage();
            returnVal = ((LifeSupportModule) moduleSelected).getName();
        } else if (moduleSelected instanceof WeaponModule) {
            ((WeaponModule) moduleSelected).damage();
            returnVal = ((WeaponModule) moduleSelected).getName();
        }

        return returnVal;
    }

    /**
     * Simulates the starship completing an expedition by calling each module's
     * respective completeExpedition() method. In particular, an item is
     * stored in the first StorageModule in the modules array (if it exists, and
     * if the item isn't null or an empty string). However, for any remaining
     * StorageModules, the completeExpedition() method is not called.
     * 
     * @param random      random object passed to completeExpedition() methdods.
     * @param itemToStore the item to be stored in the first StorageModule.
     */
    public void completeExpedition(Random random, String itemToStore) {
        boolean isFirstStorageModule = true;
        boolean canStoreItem = itemToStore != null && !itemToStore.equals("");

        // Call every module's completeExpedition() method (except for all
        // StorageModule other than the first).
        for (int i = 0; i < this.moduleCount; i++) {
            Object currModule = modules[i];
            if (currModule instanceof EngineModule) {
                ((EngineModule) currModule).completeExpedition(random);
            } else if (currModule instanceof StorageModule && isFirstStorageModule && canStoreItem) {
                ((StorageModule) currModule).completeExpedition(itemToStore);
                isFirstStorageModule = false;
            } else if (currModule instanceof LifeSupportModule) {
                ((LifeSupportModule) currModule).completeExpedition(random);
            } else if (currModule instanceof WeaponModule) {
                ((WeaponModule) currModule).completeExpedition(random);
            }
        }
    }

    /**
     * Simulates performing maintenance on the starship by calling each
     * module's respective performMaintenance() method.
     */
    public void maintainAllModules() {
        for (int i = 0; i < this.moduleCount; i++) {
            Object currModule = modules[i];
            if (currModule instanceof EngineModule) {
                ((EngineModule) currModule).performMaintenance();
            } else if (currModule instanceof StorageModule) {
                ((StorageModule) currModule).performMaintenance();
            } else if (currModule instanceof LifeSupportModule) {
                ((LifeSupportModule) currModule).performMaintenance();
            } else if (currModule instanceof WeaponModule) {
                ((WeaponModule) currModule).performMaintenance();
            }
        }
    }

    /**
     * Produces a deep copy of the current starship with a new name (that
     * references the original name), the same module capacity, and a clone
     * of each module.
     * 
     * @param newName the name of the copied starship.
     * @return a deep copy of the current starship object.
     */
    public Starship copy(String newName) {
        String name = String.format("%s (%s class)", newName, this.baseName);
        Starship newShip = new Starship(name, this.maxNumModules);

        // Clone each module and add it to the new starship's modules array.
        for (int i = 0; i < this.moduleCount; i++) {
            Object currModule = modules[i];
            if (currModule instanceof EngineModule) {
                newShip.addModule(((EngineModule) currModule).clone());
            } else if (currModule instanceof StorageModule) {
                newShip.addModule(((StorageModule) currModule).clone());
            } else if (currModule instanceof LifeSupportModule) {
                newShip.addModule(((LifeSupportModule) currModule).clone());
            } else if (currModule instanceof WeaponModule) {
                newShip.addModule(((WeaponModule) currModule).clone());
            }
        }

        return newShip;
    }

    /**
     * Simulates the starship discovering a planet by incrementing
     * numPlanetDiscoveries.
     */
    public void discoveredPlanet() {
        numPlanetDiscoveries++;
    }

    /**
     * Returns a string representation of the current starship object, which
     * includes its name, the number of planets it has discovered, and a
     * description of each of its modules.
     * 
     * @return a string representation of the current starship object.
     */
    public String toString() {
        String result = String.format("%s has discovered %d planets.\nModule Statuses:\n", this.name,
                this.numPlanetDiscoveries);
        result += showAllStatuses();

        return result;
    }

}
