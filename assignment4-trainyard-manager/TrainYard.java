
/**
 * TrainYard.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, 6
 * 
 * @author Michel Préjet
 * @version August 8th, 2025
 * 
 *          PURPOSE: Represents a central train yard manager containing a list of 
 *          trains, people, and luggage. Provides methods to create trains, persons, 
 *          and luggage, and to retrieve them by name. Also provides methods to add 
 *          and remove passengers from the train, to locate the train car in which
 *          a specific person or luggage is travelling, to get an array containing 
 *          the names of all trains in the yard, and to get the maximum distance 
 *          a train can travel. Also includes various shunting methods, allowing
 *          the user to sort a train by car type, and to detach all of or parts of
 *          a train and append them onto another.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class TrainYard {
    private ArrayList<Train> trains;
    private ArrayList<Person> people;
    private ArrayList<Luggage> luggage;

    /**
     * Constructs a new TrainYard with empty lists of trains, people, and luggage.
     */
    public TrainYard() {
        this.trains = new ArrayList<Train>();
        this.people = new ArrayList<Person>();
        this.luggage = new ArrayList<Luggage>();
    }

    // ==== Train Management ====
    /**
     * Creates a new train with a given name and adds it to the trains
     * ArrayList if the name is not null, empty, or only whitespace, and if
     * no train already exists with the same name.
     * 
     * @param name the name of the train to be created.
     * @return true if a train with the given name was successfully created and
     *         added to trains; false otherwise.
     */
    public boolean createTrain(String name) {
        boolean success = false;

        if (validateString(name)) {
            // Check if a train with the given name already exists.
            boolean alreadyExists = false;
            for (Train train : this.trains) {
                if (train.getName().toLowerCase().trim().equals(name.toLowerCase().trim())) {
                    alreadyExists = true;
                }
            }

            // Add a new train if no train exists with the given name.
            if (!alreadyExists) {
                this.trains.add(new Train(name));
                success = true;
            }
        }

        return success;
    }

    /**
     * @param name the name of the train to search for in trains (case insensitive).
     * @return the train with the given name, or null if no such train exists.
     */
    public Train getTrain(String name) {
        Train toReturn = null;
        if (name != null) {
            name = name.trim().toLowerCase();
            for (int i = 0; i < this.trains.size() && toReturn == null; i++) {
                if (trains.get(i).getName().trim().toLowerCase().equals(name)) {
                    toReturn = trains.get(i);
                }
            }
        }

        return toReturn;
    }

    /**
     * @return an ArrayList of String containing the name of all trains
     *         currently in the yard.
     */
    public String[] listTrains() {
        String[] trainNames = new String[this.trains.size()];
        for (int i = 0; i < this.trains.size(); i++) {
            trainNames[i] = this.trains.get(i).getName();
        }
        return trainNames;
    }

    // ==== Person and Luggage Management ====
    /**
     * Creates a new person with a given name and adds them to the people
     * ArrayList if the name is not null, empty, or only whitespace, and if
     * no person already exists with the same name.
     * 
     * @param name the name of the person to be created.
     * @return true if a person with the given name was successfully created and
     *         added to people; false otherwise.
     */
    public boolean createPerson(String name) {
        boolean success = false;

        if (validateString(name)) {
            // Add a new person if no person exists with the given name.
            if (getPerson(name) == null) {
                this.people.add(new Person(name));
                success = true;
            }
        }

        return success;
    }

    /**
     * Creates a luggage with a given label and a given weight and assigns it
     * to a person with a given name. The operation is only successful if the
     * given label and name are valid strings, if the weight is positive, if the
     * label isn't already taken, and if there exists a person with the given
     * name.
     * 
     * @param label     the label of the luggage to be created.
     * @param weight    the weight of the luggage to be created.
     * @param ownerName the name of the person to which the luggage should be
     *                  added.
     * @return true if the luggage was successfully created and added to the
     *         person; false otherwise.
     */
    public boolean createLuggage(String label, int weight, String ownerName) {
        // Perform validation checks. Check that the given label and ownerName
        // are not null, empty, or only whitespace, that the given weight is positive,
        // that the label isn't already taken, and that a person with the given
        // name exists (storing that person in a variable if they do exist).
        boolean isValid = false;
        Person person = null;
        if (validateString(label) && validateString(ownerName) && weight > 0
                && getPerson(ownerName) != null) {
            person = getPerson(ownerName);
            isValid = true;
        }

        if (isValid) {
            Luggage toAdd = new Luggage(label, weight);
            this.luggage.add(toAdd);
            person.addLuggage(toAdd);
        }
        return isValid;
    }

    /**
     * @param name the name of the person to be returned (case insensitive).
     * @return the person in the people ArrayList matching the given name, or
     *         null if no such person exists.
     */
    public Person getPerson(String name) {
        Person toReturn = null;
        if (name != null) {
            name = name.trim().toLowerCase();
            for (int i = 0; i < this.people.size() && toReturn == null; i++) {
                if (this.people.get(i).getName().trim().toLowerCase().equals(name)) {
                    toReturn = this.people.get(i);
                }
            }
        }
        return toReturn;
    }

    /**
     * @param label the label of the luggage to be returned (case insensitive).
     * @return the luggage in the luggage ArrayList matching the given label, or
     *         null if no such luggage exists.
     */
    public Luggage getLuggage(String label) {
        Luggage toReturn = null;
        if (label != null) {
            label = label.trim().toLowerCase();
            for (int i = 0; i < this.luggage.size() && toReturn == null; i++) {
                if (this.luggage.get(i).getLabel().trim().toLowerCase().equals(label)) {
                    toReturn = this.luggage.get(i);
                }
            }
        }
        return toReturn;
    }

    // ==== Passenger Operations ====
    /**
     * Adds a person with a given name to a train with a given name, if a
     * person and train with the given names exist.
     * 
     * @param trainName  the name of the train to which the person should be added.
     * @param personName the name of the person to be added to the train.
     * @return true if both a train and a person with the given names were
     *         found, and the person was successfully added to the train; false
     *         otherwise.
     */
    public boolean addPassengerToTrain(String trainName, String personName) {
        Person person = getPerson(personName);
        Train train = getTrain(trainName);

        // Attempt to add the person to the train.
        boolean success = false;
        if (train != null && person != null) {
            success = train.addPassenger(person);
        }

        return success;
    }

    /**
     * Removes a person with a given name from a train with a given name, if a
     * person and train with the given names exist.
     * 
     * @param trainName  the name of the train from which the person should be
     *                   removed.
     * @param personName the name of the person to be removed from the train.
     * @return true if both a train and a person with the given names were found,
     *         and the person was successfully removed from the train; false
     *         otherwise.
     */
    public boolean deboardPassengerFromTrain(String trainName, String personName) {
        Person person = getPerson(personName);
        Train train = getTrain(trainName);

        boolean success = false;
        if (train != null && person != null) {
            success = train.deboardPassenger(person);
        }

        return success;
    }

    /**
     * For a train with a given name, finds the passenger car in which a passenger
     * with a given name is located.
     * 
     * @param trainName  the name of the train to be searched.
     * @param personName the name of the person to be searched for.
     * @return the passenger car in which the person is located, or null if no
     *         train or passenger car were found with the given names.
     */
    public iTrainCar findPassengerInTrain(String trainName, String personName) {
        Train train = getTrain(trainName);
        iTrainCar carToReturn = null;

        if (train != null) {
            carToReturn = train.findPassenger(personName);
        }

        return carToReturn;
    }

    /**
     * For a train with a given name, finds the luggage car in which a luggage
     * with a given label is located.
     * 
     * @param trainName the name of the train to be searched.
     * @param label     the label of the luggage to be searched for.
     * @return the luggage car in which the luggage is located, or null if no
     *         train or luggage car were found with the given names.
     */
    public iTrainCar findLuggageInTrain(String trainName, String label) {
        Train train = getTrain(trainName);
        Luggage luggage = getLuggage(label);
        iTrainCar carToReturn = null;

        if (train != null && luggage != null) {
            carToReturn = train.findLuggage(label);
        }

        return carToReturn;
    }

    // ==== Info & Metrics ====
    /**
     * @param trainName the name of the train for which maximum travel distance
     *                  should be calculated.
     * @return the maximum distance that a train with a given name can travel, or
     *         0 if no such train is found.
     */
    public int getMaxDistance(String trainName) {
        Train train = getTrain(trainName);
        int maxDistance = 0;
        if (train != null) {
            maxDistance = train.calculateMaxDistance();
        }
        return maxDistance;
    }

    /**
     * @param trainName the train whose toString() should be returned.
     * @return the toString() of the train with the given name, or "Train not
     *         found." if no train was found.
     */
    public String describeTrain(String trainName) {
        String output = "Train not found.";
        Train train = getTrain(trainName);
        if (train != null) {
            output = train.toString();
        }
        return output;
    }

    // ==== Shunting (Merging and Rearranging) ====
    /**
     * Sorts all cars of a train with a given name by type (in ascending
     * lexicographic order), if a train with that name exists.
     * 
     * @param trainName the name of the train to be sorted.
     * @return true if the train was found and its cars were successfully
     *         sorted; false otherwise.
     */
    public boolean sortTrain(String trainName) {
        boolean success = false;

        Train train = getTrain(trainName);
        if (train != null) {
            train.sortCarsByType();
            success = true;
        }

        return success;
    }

    /**
     * Detaches all train cars from a source train with a given name and appends
     * them to a target train with a given name if both trains exist and are
     * not the same.
     * 
     * @param fromTrain the train from which cars should be detached.
     * @param toTrain   the train to which cars should be attached.
     * @return true if both the source train and the target train were found and
     *         are not the same, and if the train cars were successfully removed
     *         from the source train and appended onto the target train; false
     *         otherwise.
     */
    public boolean shuntWholeTrain(String fromTrain, String toTrain) {
        Train from = null, to = null;
        from = getTrain(fromTrain);
        to = getTrain(toTrain);

        boolean success = false;
        if (from != null && to != null && !fromTrain.toLowerCase().trim().equals(toTrain.toLowerCase().trim())) {
            to.attachCars(from.detachAtIndex(0));
            this.trains.remove(from);
            success = true;
        }

        return success;
    }

    /**
     * Detaches train cars from a source train with a given name starting at the
     * first car of a given type, then appends the detached cars onto a target
     * train with a given name. Only proceeds if both trains exist and are not
     * the same.
     * 
     * @param fromTrain the train from which cars should be detached.
     * @param toTrain   the train to which cars should be appended.
     * @param carType   the train car type whose first occurrence should be
     *                  detached from the source train.
     * @return true if both the source train and the target train were found and
     *         are not the same, if the given type is not null, and if the
     *         appropriate train cars were successfully removed from the source
     *         train and appended onto the target train; false otherwise.
     */
    public boolean shuntFromCarType(String fromTrain, String toTrain, String carType) {
        Train from = null, to = null;
        from = getTrain(fromTrain);
        to = getTrain(toTrain);

        boolean success = false;
        if (carType != null && from != null && to != null && !fromTrain.equals(toTrain)) {
            iTrainCar firstMatching = from.findFirstCarOfType(carType);
            if (firstMatching != null) {
                to.attachCars(from.detachAt(firstMatching));
                success = true;
            }
        }

        return success;
    }

    /**
     * Detaches train cars from a source train with a given name starting at a
     * given index, then appends the detached cars onto a target train with a
     * given name. Only proceeds if both trains exist and are not the same, and
     * if the index if greater than 0 and less than the size of the source train.
     * 
     * @param fromTrain the train from which cars should be detached.
     * @param toTrain   the train to which cars should be appended.
     * @return true if both the source train and the target train were found and
     *         are not the same, if the given type is not null, and if the
     *         appropriate train cars were successfully removed from the source
     *         train and appended onto the target train; false otherwise.
     */
    public boolean shuntFromIndex(String fromTrain, String toTrain, int index) {
        Train from = null, to = null;
        from = getTrain(fromTrain);
        to = getTrain(toTrain);

        boolean success = false;
        if (from != null && to != null && !fromTrain.equals(toTrain) && index >= 0 && index < from.size()) {
            to.attachCars(from.detachAtIndex(index));
            success = true;
        }

        return success;
    }

    /**
     * Returns a String representation of the current train yard, including a
     * description of each train.
     * 
     * @return a String representation of the current train yard.
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < this.trains.size() - 1; i++) {
            output += this.trains.get(i).toString() + "\n";
        }
        if (this.trains.size() > 0) {
            output += this.trains.get(this.trains.size() - 1);
        }
        return output;
    }

    /**
     * @param s the string to be validated.
     * @return true if the given string is not null, empty, or only whitespace;
     *         false otherwise.
     */
    private boolean validateString(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
