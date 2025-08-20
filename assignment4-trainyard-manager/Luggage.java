/**
 * Luggage.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, N/A
 * 
 * @author Simon Wermie
 * @version N/A
 * 
 *          PURPOSE: Represents a luggage item with a label and a weight.
 *          Provides getter methods for each of these instance variables.
 * 
 *          NOTE: the entirety of this class was provided by my instructor,
 *          Simon Wermie. I do not take credit for any of the code contained
 *          within this file.
 */

public class Luggage {
    private final String label;
    private final int weight;

    public Luggage(String label, int weight) {
        this.label = label;
        this.weight = weight;
    }

    public String getLabel() {
        return label;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return label + " [" + weight + "kg]";
    }
}
