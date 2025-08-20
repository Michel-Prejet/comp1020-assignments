/**
 * Train.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, 5
 * 
 * @author Michel Préjet
 * @version August 8th, 2025
 * 
 *          PURPOSE: Represents a Train in a trainyard, including its name and
 *          all of its train cars (fuel cars, passenger cars, and luggage cars).
 *          Provides a getter method for the name, as well as methods to add
 *          a train car, remove a train car, attach a sequence of train cars,
 *          detach a sequence of train cars (by index or by train car), find
 *          the first car of a specific type, sort the train cars by type,
 *          add a passenger, deboard a passenger, find the passenger car of
 *          a passenger with a given name, find the luggage car of a luggage
 *          with a given label, get the size of the train, and calculate the
 *          maximum distance the train can travel.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

public class Train {
    private String name;
    private iTrainCar head;

    /**
     * Constructs a new Train with a given name. Sets the head to null by default.
     * Throws an IllegalArgumentException if the name argument is null.
     * 
     * @param name the name of the train.
     */
    public Train(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Train constructor cannot accept null arguments.");
        }

        this.name = name;
        this.head = null;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Adds a given iTrainCar to the end of the train. Iterates through
     * every iTrainCar to find the last one, then changes its 'next' pointer to
     * the given iTrainCar. If the head is null, it is set to the given iTrainCar.
     * If the given iTrainCar is null, an IllegalArgumentException is thrown.
     * 
     * @param car the iTrainCar to be added to the end of the train.
     */
    public void addCar(iTrainCar car) {
        if (car == null) {
            throw new IllegalArgumentException("The train car cannot be null.");
        }

        // Search for the last iTrainCar in the train.
        iTrainCar curr = this.head;
        iTrainCar prev = null;
        while (curr != null) {
            prev = curr;
            curr = curr.getNext();
        }

        // Ensure that adding the car doesn't cause a cycle.
        boolean causesCycle = false;
        iTrainCar curr2 = this.head;
        while (curr2 != null) {
            if (curr2 == car) {
                causesCycle = true;
            }
            curr2 = curr2.getNext();
        }

        // Add to the end of the train.
        if (!causesCycle) {
            if (this.head == null) {
                this.head = car;
            } else {
                prev.setNext(car);
            }
        }
    }

    /**
     * Removes a given iTrainCar from the train, if it exists. Iterates
     * through the train until it finds a matching node, then changes the
     * 'next' pointer of its previous iTrainCar to point to the iTrainCar two
     * positions over. If no matching iTrainCar is found, nothing is removed.
     * 
     * @param target the iTrainCar to be removed.
     * @return true if the given iTrainCar was found and successfully removed;
     *         false otherwise.
     */
    public boolean removeCar(iTrainCar target) {
        // Search for the matching iTrainCar.
        iTrainCar curr = this.head;
        iTrainCar prev = null;
        iTrainCar toRemove = null;
        while (curr != null && toRemove == null) {
            if (checkCarEquality(curr, target)) {
                toRemove = curr;
            } else {
                prev = curr;
                curr = curr.getNext();
            }
        }

        // If a matching iTrainCar was found, remove it.
        boolean success = false;
        if (toRemove != null) {
            if (prev == null) {
                this.head = this.head.getNext();
            } else {
                prev.setNext(prev.getNext().getNext());
            }
            success = true;
        }

        return success;
    }

    /**
     * Attaches a given sublist of iTrainCar objects to the end of the train.
     * 
     * @param other the head of the sublist to be added.
     */
    public void attachCars(iTrainCar other) {
        iTrainCar curr = other;
        while (curr != null) {
            addCar(curr);
            curr = curr.getNext();
        }
    }

    /**
     * Detaches all nodes (iTrainCar objects) starting at a given index.
     * Traverses the train up to the given index, then sets the previous iTrainCar's
     * 'next' pointer to null. Sets the head to null if the given index is 0.
     * 
     * @param index the index at which the iTrainCar, and all subsequent cars,
     *              should be detached.
     * @return the sublist of iTrainCar that was detached, or null if the given
     *         index was negative or greater than or equal to the size of the train.
     */
    public iTrainCar detachAtIndex(int index) {
        iTrainCar toReturn;

        if (index < 0 || index >= size()) {
            toReturn = null;
        } else {
            // Traverse the train up to the given index.
            iTrainCar curr = this.head;
            iTrainCar prev = null;
            for (int i = 0; i < index; i++) {
                prev = curr;
                curr = curr.getNext();
            }

            // Detach the train cars from the given index.
            toReturn = curr;
            if (index == 0) {
                this.head = null;
            } else {
                prev.setNext(null);
            }
        }

        return toReturn;
    }

    /**
     * Detaches all iTrainCar objects starting at a given iTrainCar. Traverses
     * the train until a train car matching the given iTrainCar is found, then
     * sets the previous iTrainCar's 'next' pointer to null. Sets the head to
     * null if the given iTrainCar is the head.
     * 
     * @param target the iTrainCar to be removed along with all subsequent train
     *               cars.
     * @return the sublist of iTrainCar that was detached, or null if the given
     *         iTrainCar did not yield a match.
     */
    public iTrainCar detachAt(iTrainCar target) {
        // Search for matching iTrainCar.
        iTrainCar curr = this.head;
        iTrainCar prev = null;
        iTrainCar toReturn = null;
        while (target != null && curr != null && toReturn == null) {
            if (checkCarEquality(curr, target)) {
                toReturn = curr;
            } else {
                prev = curr;
                curr = curr.getNext();
            }
        }

        // Detach the train cars from the matching iTrainCar.
        if (target != null) {
            toReturn = curr;
            if (toReturn != null) {
                if (toReturn == this.head) {
                    this.head = null;
                } else {
                    prev.setNext(null);
                }
            }
        }

        return toReturn;
    }

    /**
     * @param type the type for which the first iTrainCar in the train should be
     *             returned.
     * @return the first iTrainCar in the train corresponding to the given type.
     */
    public iTrainCar findFirstCarOfType(String type) {
        iTrainCar curr = this.head;
        iTrainCar firstMatching = null;
        while (type != null && curr != null && firstMatching == null) {
            if (curr.getType().equals(type)) {
                firstMatching = curr;
            }
            curr = curr.getNext();
        }
        return firstMatching;
    }

    /**
     * Sorts all train cars in the current train in ascending lexicographic order
     * according to their type name. Adds the head of the current train to a
     * new train using ordered insert, then removes the head, repeating this
     * process until the head is null. Finally, the head of the current train
     * is set to the head of the sorted train.
     */
    public void sortCarsByType() {
        Train sortedTrain = new Train(this.name);
        while (this.head != null) {
            iTrainCar restOfList = this.head.getNext();
            sortedTrain.orderedInsert(this.head);
            this.head = restOfList;
        }
        this.head = sortedTrain.head;
    }

    /**
     * Attempts to add a given passenger and their luggage to the train. Searches
     * for the first available PassengerCar and LuggageCar in the train, and if
     * they both exist, adds the passenger to the PassengerCar and all of their
     * luggage to the LuggageCar, but only if there is enough space to do so in both
     * cases. If either train car doesn't exist or doesn't have enough space, no
     * action is taken. Throws an IllegalArgumentException if the given person
     * is null.
     * 
     * @param p the person to be added to the train.
     * @return true if the person was successfully added to the first available
     *         PassengerCar and if all of their luggage was successfully added to
     *         the first available LuggageCar; false otherwise.
     */
    public boolean addPassenger(Person p) {
        if (p == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }

        // Find the first PassengerCar and LuggageCar on the train.
        iTrainCar curr = this.head;
        PassengerCar passengerCar = null;
        LuggageCar luggageCar = null;
        while (curr != null && (passengerCar == null || luggageCar == null)) {
            if (curr instanceof PassengerCar && passengerCar == null) {
                passengerCar = (PassengerCar) curr;
            } else if (curr instanceof LuggageCar && luggageCar == null) {
                luggageCar = (LuggageCar) curr;
            }
            curr = curr.getNext();
        }

        // Attempt to add the passenger and their luggage to the train. If there
        // is no available PassengerCar or LuggageCar, or if either of them don't
        // have enough available space, no action is taken.
        boolean success = false;
        if (passengerCar != null) {
            success = passengerCar.addPassenger(p);
            if (success && luggageCar != null
                    && (p.getLuggages().size() <= luggageCar.getMaxCapacity() - luggageCar.getNumOccupiedSlots())) {
                for (Luggage lug : p.getLuggages()) {
                    luggageCar.addLuggage(lug);
                }
            }
        }

        return success;
    }

    /**
     * Attempts to remove a given passenger from the train. Searches all
     * passenger cars and deboards the given passenger if they are found. If
     * the passenger was successfully removed, all luggage cars in the train
     * are also searched and all the passenger's luggage is removed as well.
     * Throws an IllegalArgumentException if the given person is null.
     * 
     * @param p the person to be removed from the train along with their luggage.
     * @return true if the passenger was removed successfully; false otherwise.
     */
    public boolean deboardPassenger(Person p) {
        if (p == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }

        // Attempt to remove the passenger from the train.
        boolean success = false;
        iTrainCar curr = this.head;
        while (curr != null) {
            if (curr instanceof PassengerCar && !success) {
                if (((PassengerCar) curr).deBoard(p)) {
                    success = true;
                }
            }
            curr = curr.getNext();
        }

        // If the passenger was successfully removed, also remove their luggage.
        if (success) {
            curr = this.head;
            while (curr != null) {
                if (curr instanceof LuggageCar) {
                    for (Luggage lug : p.getLuggages()) {
                        ((LuggageCar) curr).deBoard(lug);
                    }
                }
                curr = curr.getNext();
            }
        }

        return success;
    }

    /**
     * Finds the passenger car in which a passenger with a given name is
     * travelling. Traverses the train and, for each passenger car, attempts
     * to retrieve the row and seat number corresponding to the given name. If
     * a valid row and seat number are obtained, that passenger car is returned.
     * Throws an IllegalArgumentException if the given name is null, empty, or
     * only whitespace.
     * 
     * @param name the name of the passenger to be located.
     * @return the passenger car containing the passenger with the given name, or
     *         null if no such passenger was found.
     */
    public iTrainCar findPassenger(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be null, empty, or only whitespace.");
        }

        // Search through all trains cars.
        PassengerCar passengerCar = null;
        iTrainCar curr = this.head;
        while (curr != null) {

            // For passenger cars, check for the passenger with the given name.
            if (curr instanceof PassengerCar && passengerCar == null) {
                int[] location = ((PassengerCar) curr).locatePassenger(name);

                // If the passenger with the given name is found, set the current
                // passenger car as the return value.
                if (location != null && location[0] != -1 && location[1] != -1) {
                    passengerCar = (PassengerCar) curr;
                }
            }
            curr = curr.getNext();
        }

        return passengerCar;
    }

    /**
     * Finds the luggage car containing a luggage with a given label. Traverses
     * the train and, for each luggage car, attempts to retrieve the compartment
     * and slot corresponding to the given label. If a valid compartment and slot
     * number are obtained, that luggage car is returned. Throws an
     * IllegalArgumentException if the given label is null, empty, or only
     * whitespace.
     * 
     * @param name the label of the luggage to be located.
     * @return the luggage car containing the luggage with the given label, or
     *         null if no such luggage was found.
     */
    public iTrainCar findLuggage(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Luggage label cannot be null, empty, or only whitespace.");
        }

        // Search through all train cars.
        LuggageCar luggageCar = null;
        iTrainCar curr = this.head;
        while (curr != null) {

            // For luggage cars, check for the luggage with the given label.
            if (curr instanceof LuggageCar && luggageCar == null) {
                int[] location = ((LuggageCar) curr).locateLuggage(label);

                // If the luggage with the given label is found, set the current
                // luggage car as the return value.
                if (location != null && location[0] != -1 && location[1] != -1) {
                    luggageCar = (LuggageCar) curr;
                }
            }
            curr = curr.getNext();
        }

        return luggageCar;
    }

    /**
     * Calculates the maximum distance the train can travel. For any train with
     * at least one train car, adds up the capacity of all fuel cars in the train,
     * then divides it by the total number of cars times 10. If the train has no
     * cars, returns 0.
     * 
     * @return the maximum distance the current train can travel.
     */
    public int calculateMaxDistance() {
        int maxDistance = 0;
        int size = size();

        if (size > 0) {
            // Add up the capacity of all fuel cars in the train.
            int maxFuelCapacity = 0;
            iTrainCar curr = this.head;
            while (curr != null) {
                if (curr instanceof FuelCar) {
                    maxFuelCapacity += curr.getMaxCapacity();
                }
                curr = curr.getNext();
            }

            maxDistance = maxFuelCapacity / (size * 10);
        }

        return maxDistance;
    }

    /**
     * Returns a String representation of the current train, including its
     * name and the type of all of its train cars, listed in order.
     * 
     * @return a String representation of the current train.
     */
    public String toString() {
        String output = this.name + ": ";

        // Concatenate the type for each train car.
        iTrainCar curr = this.head;
        while (curr != null) {
            output += curr.getType() + " -> ";
            curr = curr.getNext();
        }

        // Remove the last arrow.
        if (output.length() > this.name.length() + 2) {
            output = output.substring(0, output.length() - 4);
        }

        return output;
    }

    /**
     * @return the size of the current train (the number of train cars).
     */
    public int size() {
        int size = 0;
        iTrainCar curr = this.head;
        while (curr != null) {
            size++;
            curr = curr.getNext();
        }
        return size;
    }

    /**
     * Inserts a given iTrainCar at the appropriate position in the current train
     * according to the lexicographical value of its type name
     * (Fuel < Luggage < Passenger) using ordered insert.
     * 
     * @param car the train car to be inserted in the train.
     */
    private void orderedInsert(iTrainCar car) {
        iTrainCar curr = this.head;
        iTrainCar prev = null;

        // Find the correct position of the given car in the train.
        while (curr != null && car.getType().compareTo(curr.getType()) > 0) {
            prev = curr;
            curr = curr.getNext();
        }

        // Insert the car at the position found.
        if (prev == null) {
            car.setNext(this.head);
            this.head = car;
        } else {
            prev.setNext(car);
            car.setNext(curr);
        }
    }

    /**
     * Checks if two train cars are equal by checking whether they are the same
     * type and have the same toString() and toStringContents().
     * 
     * @param car1 the first train car.
     * @param car2 the second train car.
     * @return true if the two train cars are equal; false otherwise.
     */
    private boolean checkCarEquality(iTrainCar car1, iTrainCar car2) {
        boolean areEqual = false;
        if (car1.getType().equals(car2.getType())) {
            areEqual = car1.toString().equals(car2.toString())
                    && car1.toStringContents().equals(car2.toStringContents());
        }
        return areEqual;
    }
}
