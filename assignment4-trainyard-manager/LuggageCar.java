/**
 * LuggageCar.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, 4
 * 
 * @author Michel Préjet
 * @version August 4th, 2025
 * 
 *          PURPOSE: Represents a LuggageCar in a train, including its storage
 *          compartments and the next train car. Provides a getter method for
 *          latter, as well as methods to get the type of the current car, get
 *          its maximum luggage capacity, set the next train car, get the total
 *          weight of the luggage car, initialize a compartment, add luggage to
 *          a compartment, remove luggage from a compartment, and locate luggage
 *          by label.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

public class LuggageCar implements iTrainCar {
    private Luggage[][] storage;
    private iTrainCar next;

    /**
     * Constructs a new LuggageCar with a given number of compartments. Throws
     * an IllegalArgumentException if the given number of compartments is zero
     * or negative.
     * 
     * @param numCompartments number of compartments the current luggage car will
     *                        have.
     */
    public LuggageCar(int numCompartments) {
        if (numCompartments <= 0) {
            throw new IllegalArgumentException("Number of compartments cannot be zero or negative.");
        }

        this.storage = new Luggage[numCompartments][];
    }

    public String getType() {
        return iTrainCar.LUGGAGE;
    }

    /**
     * Determines the total number of initialized luggage spots in the current
     * luggage car.
     * 
     * @return the total number of initialized luggage spots.
     */
    public int getMaxCapacity() {
        int numSpots = 0;
        for (int i = 0; i < this.storage.length; i++) {
            if (this.storage[i] != null) {
                numSpots += this.storage[i].length;
            }
        }
        return numSpots;
    }

    public iTrainCar getNext() {
        return this.next;
    }

    public void setNext(iTrainCar next) {
        this.next = next;
    }

    /**
     * Determines the combined weight of all luggage on the current luggage car.
     * 
     * @return the total weight of all luggage on the current luggage car.
     */
    public int getWeight() {
        int totalWeight = 0;
        for (int i = 0; i < this.storage.length; i++) {
            for (int j = 0; this.storage[i] != null && j < this.storage[i].length; j++) {
                if (this.storage[i][j] != null) {
                    totalWeight += this.storage[i][j].getWeight();
                }
            }
        }
        return totalWeight;
    }

    /**
     * Initializes a compartment at a given index with a given size in the
     * luggage car's storage. Throws an IllegalArgumentException if the given
     * index is negative or greater than storage.length - 1, if the given size is
     * zero or negative, or if the compartment at the given index has already been
     * initialized.
     * 
     * @param index the index of the compartment to be initialized.
     * @param size  number of slots of the initialized compartment.
     * @return true if the compartment was successfully initialized.
     */
    public boolean initializeCompartment(int index, int size) {
        if (index < 0 || index >= this.storage.length) {
            throw new IllegalArgumentException("Index cannot be negative, or greater than or equal to storage.length.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size cannot be zero or negative.");
        }
        if (this.storage[index] != null) {
            throw new IllegalArgumentException("Compartment already initialized.");
        }

        this.storage[index] = new Luggage[size];
        return true;
    }

    /**
     * Adds a given luggage to the next empty slot in the luggage car's storage,
     * if available space exists. Throws an IllegalArgumentException if the
     * given luggage is null.
     * 
     * @param l the luggage to be added to the next empty slot.
     * @return true if an empty slot was found and the luggage was added
     *         successfully; false otherwise.
     */
    public boolean addLuggage(Luggage l) {
        if (l == null) {
            throw new IllegalArgumentException("Luggage cannot be null.");
        }

        // Search for the next empty slot in storage.
        int nextSlotRow = -1, nextSlotCol = -1;
        boolean found = false;
        for (int i = 0; i < this.storage.length && !found; i++) {
            for (int j = 0; this.storage[i] != null && j < this.storage[i].length && !found; j++) {
                if (this.storage[i][j] == null) {
                    nextSlotRow = i;
                    nextSlotCol = j;
                    found = true;
                }
            }
        }

        // Add the given luggage to the next empty slot if it exists.
        boolean added = false;
        if (nextSlotRow >= 0 && nextSlotCol >= 0) {
            this.storage[nextSlotRow][nextSlotCol] = l;
            added = true;
        }

        return added;
    }

    /**
     * Removes a given piece of luggage from the car, if it can be found. Throws
     * an IllegalArgumentException if the given luggage is null.
     * 
     * @param l the piece of luggage to be removed from the luggage car.
     * @return true if the piece of luggage was found and successfully removed
     *         from its slot; false otherwise.
     */
    public boolean deBoard(Luggage l) {
        if (l == null) {
            throw new IllegalArgumentException("Luggage cannot be null.");
        }

        // Search for the location of the given luggage in storage.
        int[] luggageLocation = locateLuggage(l.getLabel());

        // Remove the given luggage from its compartment if it is found.
        boolean deBoarded = false;
        if (luggageLocation != null) {
            this.storage[luggageLocation[0]][luggageLocation[1]] = null;
            deBoarded = true;
        }

        return deBoarded;
    }

    /**
     * Finds a luggage with a given label and returns its row and column
     * numbers in an integer array at indices 0 and 1, respectively. Throws an
     * IllegalArgumentException if the given label is null.
     * 
     * @param label the label of the luggage to be located in the current luggage
     *              car.
     * @return an integer array of size 2 containing the luggage's row number at
     *         index 0 and its column number at index 1, or null if no
     *         luggage was found with the given label.
     */
    public int[] locateLuggage(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Luggage label cannot be null.");
        }

        // Search for the luggage with the given label to obtain its row and
        // column numbers.
        int rowIndex = -1, colIndex = -1;
        boolean found = false;
        for (int i = 0; i < this.storage.length && !found; i++) {
            for (int j = 0; this.storage[i] != null && j < this.storage[i].length && !found; j++) {
                if (this.storage[i][j] != null && this.storage[i][j].getLabel().equals(label)) {
                    rowIndex = i;
                    colIndex = j;
                    found = true;
                }
            }
        }

        // Populate the array to be returned if a luggage with the given label was
        // found.
        int[] luggageLocation = null;
        if (rowIndex >= 0 && colIndex >= 0) {
            luggageLocation = new int[] { rowIndex, colIndex };
        }

        return luggageLocation;
    }

    /**
     * Returns a visual representation of each initialized compartment in the
     * current luggage car, including its index, "__" for empty slots, and "[]"
     * for occupied slots.
     * 
     * @return a visual representation of each compartment in the current
     *         luggage car.
     */
    public String toStringContents() {
        String output = "";
        for (int i = 0; i < this.storage.length; i++) {
            // Only list initialized compartments.
            if (this.storage[i] != null) {
                output += "Compartment " + i + ": ";
            }

            for (int j = 0; this.storage[i] != null && j < this.storage[i].length; j++) {
                if (this.storage[i][j] == null) {
                    output += "__";
                } else {
                    output += "[]";
                }
            }

            // Only add a newline if the next index isn't the last line and if
            // the compartment was initialized.
            if (i < this.storage.length - 1 && this.storage[i] != null) {
                output += "\n";
            }
        }

        return output;
    }

    /**
     * Returns a String representation of the current luggage car, including
     * the number of occupied slots, the total number of slots, and its total
     * weight.
     * 
     * @return a String representation of the current luggage car.
     */
    public String toString() {
        return String.format("LuggageCar (%d/%d filled) @ %dkg", getNumOccupiedSlots(), getMaxCapacity(), getWeight());
    }

    /**
     * Determines the number of occupied slots in the luggage car.
     * 
     * @return the number of occupied slots in the luggage car.
     */
    public int getNumOccupiedSlots() {
        int numOccupied = 0;
        for (int i = 0; i < this.storage.length; i++) {
            for (int j = 0; this.storage[i] != null && j < this.storage[i].length; j++) {
                if (this.storage[i][j] != null) {
                    numOccupied++;
                }
            }
        }

        return numOccupied;
    }
}
