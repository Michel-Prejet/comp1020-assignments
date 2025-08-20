/**
 * PassengerCar.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, 3
 * 
 * @author Michel Préjet
 * @version August 4th, 2025
 * 
 *          PURPOSE: Represents a PassengerCar in a train, including its seats
 *          and the next train car. Provides a getter method for the latter, as
 *          well as methods to get the type of the current car, get the maximum
 *          capacity, set the next car, add a passenger, remove a passenger, and
 *          locate a passenger.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

public class PassengerCar implements iTrainCar {
    private Person[][] seats;
    private iTrainCar next;

    /**
     * Constructs a new PassengerCar with a given number of rows and columns.
     * All seats are set to empty (null) by default. Throws an
     * IllegalArgumentException if the given number of rows or columns is
     * negative or 0.
     * 
     * @param rows the number of rows of seats in the passenger car.
     * @param cols the number of columns of seats in the passenger car.
     */
    public PassengerCar(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows or columns cannot be negative or zero.");
        }

        seats = new Person[rows][cols];
    }

    public String getType() {
        return iTrainCar.PASSENGER;
    }

    public int getMaxCapacity() {
        return this.seats.length * this.seats[0].length;
    }

    public iTrainCar getNext() {
        return this.next;
    }

    public void setNext(iTrainCar next) {
        this.next = next;
    }

    /**
     * Boards a given person in the next available seat if an empty seat
     * exists in the current passenger car. Throws an IllegalArgumentException
     * if the given person is null.
     * 
     * @param p the person to be boarded onto the current passenger car.
     * @return true if an empty seat was found and the person was boarded
     *         successfully; false otherwise.
     */
    public boolean addPassenger(Person p) {
        if (p == null) {
            throw new IllegalArgumentException("Passenger cannot be null.");
        }

        // Search for the first empty seat.
        int nextRow = -1, nextCol = -1;
        boolean found = false;
        for (int i = 0; i < this.seats.length && !found; i++) {
            for (int j = 0; j < this.seats[i].length && !found; j++) {
                if (this.seats[i][j] == null) {
                    nextRow = i;
                    nextCol = j;
                    found = true;
                }
            }
        }

        // Board the given person if an empty seat was found.
        boolean added = false;
        if (nextRow >= 0 && nextCol >= 0) {
            p.board();
            this.seats[nextRow][nextCol] = p;
            added = true;
        }

        return added;
    }

    /**
     * Removes a given person from their seat, if that person is found in the
     * current passenger car's seats ArrayList. Throws an IllegalArgumentException
     * if the given person is null.
     * 
     * @param p the person to be removed from their seat.
     * @return true if the person was found and successfully removed from their
     *         seat; false otherwise.
     */
    public boolean deBoard(Person p) {
        if (p == null) {
            throw new IllegalArgumentException("Passenger cannot be null.");
        }

        // Search for the given person's row and column numbers. Uses ticket
        // ID numbers for each comparison.
        int rowIndex = -1, colIndex = -1;
        boolean found = false;
        for (int i = 0; i < this.seats.length && !found; i++) {
            for (int j = 0; j < this.seats[i].length && !found; j++) {
                if (this.seats[i][j] != null && this.seats[i][j].getTicketID() == p.getTicketID()) {
                    rowIndex = i;
                    colIndex = j;
                    found = true;
                }
            }
        }

        // Remove the given person from their seat if they were found.
        boolean deBoarded = false;
        if (rowIndex >= 0 && colIndex >= 0) {
            this.seats[rowIndex][colIndex] = null;
            deBoarded = true;
        }

        return deBoarded;
    }

    /**
     * Finds a person with a given name and returns their row and column
     * numbers in an integer array at indices 0 and 1, respectively. Throws an
     * IllegalArgumentException if the given name is null.
     * 
     * @param name the name of the person to be located in the current passenger
     *             car.
     * @return an integer array of size 2 containing the person's row number at
     *         index 0 and their column number at index 1, or null if no
     *         passenger was found with the given name.
     */
    public int[] locatePassenger(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be null.");
        }

        // Search for the person with the given name to obtain their row and
        // column numbers.
        int rowIndex = -1, colIndex = -1;
        boolean found = false;
        for (int i = 0; i < this.seats.length && !found; i++) {
            for (int j = 0; j < this.seats[i].length && !found; j++) {
                if (this.seats[i][j] != null && this.seats[i][j].getName().equals(name)) {
                    rowIndex = i;
                    colIndex = j;
                    found = true;
                }
            }
        }

        // Populate the array to be returned if a person with the given name was
        // found.
        int[] passengerLocation = null;
        if (rowIndex >= 0 && colIndex >= 0) {
            passengerLocation = new int[] { rowIndex, colIndex };
        }

        return passengerLocation;
    }

    /**
     * Returns a visual representation of each row in the passenger car,
     * including the row number followed by each passenger's name (or "Empty")
     * in the appropriate order.
     * 
     * @return a visual representation of each row in the passenger car.
     */
    public String toStringContents() {
        String output = "";
        for (int i = 0; i < this.seats.length; i++) {
            output += "Row " + i + ": ";

            int lastRowIndex = this.seats[i].length - 1;
            for (int j = 0; j < lastRowIndex; j++) {
                if (this.seats[i][j] == null) {
                    output += "Empty | ";
                } else {
                    output += this.seats[i][j].getName() + " | ";
                }
            }

            // Concatenate the last seat in the row.
            if (lastRowIndex > 0) {
                if (this.seats[i][lastRowIndex] == null) {
                    output += "Empty";
                } else {
                    output += this.seats[i][lastRowIndex].getName();
                }
            }

            // Only add a newline if the next index isn't the last line.
            if (i < this.seats.length - 1) {
                output += "\n";
            }
        }

        return output;
    }

    /**
     * Returns a String representation of the current passenger car in the form
     * of a summary of the number of seats filled and the total capacity.
     * 
     * @return a String representation of the current passenger car.
     */
    public String toString() {
        return String.format("PassengerCar (%d/%d occupied)", getNumSeatsOccupied(), getMaxCapacity());
    }

    /**
     * Determines the number of occupied seats in the passenger car.
     * 
     * @return the number of occupied seats in the passenger car.
     */
    private int getNumSeatsOccupied() {
        int numOccupied = 0;
        for (Person[] row : this.seats) {
            for (Person seat : row) {
                if (seat != null) {
                    numOccupied++;
                }
            }
        }

        return numOccupied;
    }
}
