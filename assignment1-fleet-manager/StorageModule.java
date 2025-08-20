/**
 * StorageModule.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, 1
 * 
 * @author Michel Préjet
 * @version July 13th, 2025
 * 
 *          PURPOSE: Represents a storage module of a starship. Simulates
 *          storage using a PFA along with a maximum storage capacity, and a
 *          boolean that ignals whether or not the load is secured.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

public class StorageModule {

    private String name;
    private boolean securedLoad;
    private int storedCount;
    private String[] storage;

    /**
     * Constructs a new StorageModule with a name and maximal storage capacity.
     * Instantiates an empty PFA and sets securedLoad to true by default.
     * 
     * @param name            the name of the StorageModule.
     * @param storageCapacity the maximum storage capacity of the StorageModule.
     */
    public StorageModule(String name, int storageCapacity) {
        this.name = name;
        this.storage = new String[storageCapacity];
        this.storedCount = 0;
        this.securedLoad = true;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Checks whether the StorageModule is functional - that is, the load is
     * secured and the storage is not full.
     * 
     * @return true if the StorageModule has a secured load and is not full;
     *         false otherwise.
     */
    public boolean isWorking() {
        return this.securedLoad && (this.storedCount < this.storage.length);
    }

    /**
     * Simulates damage to the StorageModule by setting securedLoad to false.
     */
    public void damage() {
        this.securedLoad = false;
    }

    /**
     * Simulates performing maintenance on the StorageModule by setting
     * securedLoad to true.
     */
    public void performMaintenance() {
        this.securedLoad = true;
    }

    /**
     * Simulates the StorageModule completing an expedition by storing an item
     * in the storage array (if it is not already full).
     * 
     * @param itemToStore the item to be stored.
     * @return true if the item was successfully stored; false otherwise.
     */
    public boolean completeExpedition(String itemToStore) {
        boolean returnVal = false;

        if (this.storedCount < this.storage.length) {
            this.storage[this.storedCount++] = itemToStore;
            returnVal = true;
        }

        return returnVal;
    }

    /**
     * Produces a new instance of this StorageModule with the same name and the
     * same storage capacity. Does not carry over the value of securedLoad or
     * any items contained in storage.
     * 
     * @return a clone of the current StorageModule instance.
     */
    public StorageModule clone() {
        return new StorageModule(this.name, this.storage.length);
    }

    /**
     * Returns a string representation of the current StorageModule object,
     * which includes its name, its secured status, the number of items stored
     * and the total storage capacity, its contents, and whether it is
     * functional.
     * 
     * @return a string representation of the current StorageModule object.
     */
    public String toString() {
        String result;

        // Return one of two different strings depending on the value returned
        // by isWorking().
        if (isWorking()) {
            result = String.format("Storage Module \"%s\" [Status: Secured, Stored Items: %d/%d, Contents: [",
                    this.name, this.storedCount, this.storage.length);
        } else {
            result = String.format("Storage Module \"%s\" [Status: Unsecured, Stored Items: %d/%d, Contents: [",
                    this.name, this.storedCount, this.storage.length);
        }

        // Concatenate the contents of the storage module.
        if (this.storedCount > 0) {
            for (int i = 0; i < this.storedCount - 1; i++) {
                result += this.storage[i] + ", ";
            }
            result += this.storage[this.storedCount - 1] + "]]";
        } else {
            result += "]]";
        }

        return result;
    }
}