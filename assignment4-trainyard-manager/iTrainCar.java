/**
 * iTrainCar.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, N/A
 * 
 * @author Simon Wermie
 * @version N/A
 * 
 *          PURPOSE: represents an interface for all train car implementations.
 *          Requires methods to get the train car type, get its maximum
 *          capacity, get/set the next train car in the linked list, get a
 *          visual representation of the train car, and a toString() override.
 * 
 *          NOTE: the entirety of this class was provided by my instructor,
 *          Simon Wermie. I do not take credit for any of the code contained
 *          within this file.
 */

public interface iTrainCar {
    String PASSENGER = "Passenger";
    String LUGGAGE = "Luggage";
    String FUEL = "Fuel";

    String getType();

    int getMaxCapacity();

    iTrainCar getNext();

    void setNext(iTrainCar next);

    String toStringContents();

    String toString();
}
