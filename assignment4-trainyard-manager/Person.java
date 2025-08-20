
/**
 * Person.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, 1
 * 
 * @author Michel Préjet
 * @version August 4th, 2025
 * 
 *          PURPOSE: Represents a Person as a potential passenger of a train, 
 *          including their name, their unique ticket ID, and their luggage.
 *          Provides getter methods for each of these instance variables, as
 *          well as methods to board the person on a train, check whether or not
 *          they have boarded a train, and add luggage.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.util.ArrayList;

public class Person {
    private final String name;
    private int ticketID;
    private ArrayList<Luggage> luggage;
    private static int currTicket = 1;

    /**
     * Constructs a new Person who has not boarded the train with a given name,
     * a ticket ID of 0, and an empty luggage list. Throws an
     * IllegalArgumentException if the given name is null.
     * 
     * @param name the name of the person.
     */
    public Person(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Person constructor cannot accept null arguments.");
        }

        this.name = name;
        this.ticketID = 0;
        this.luggage = new ArrayList<Luggage>();
    }

    /**
     * Constructs a new Person who has already boarded the train with a given
     * name, a given luggage list, and a unique ticket ID. Throws an
     * IllegalArgumentException if the given name or luggage list are null.
     * 
     * @param name the name of the person.
     * @param l    an ArrayList of Luggage representing the person's luggage
     *             list.
     */
    public Person(String name, ArrayList<Luggage> l) {
        if (name == null || l == null) {
            throw new IllegalArgumentException("Person constructor cannot accept null arguments.");
        }

        this.name = name;
        this.ticketID = currTicket++;
        this.luggage = l;
    }

    public String getName() {
        return name;
    }

    public int getTicketID() {
        return ticketID;
    }

    public ArrayList<Luggage> getLuggages() {
        return new ArrayList<>(luggage);
    }

    /**
     * Determines if the current person has boarded the train - that is, they
     * have a unique ticket ID greater than 0.
     * 
     * @return true if the current person has a ticket ID greater than 0; false
     *         otherwise.
     */
    public boolean boarded() {
        return this.ticketID != 0;
    }

    /**
     * Adds a given luggage to the current person's ArrayList of Luggage if it
     * isn't already in the list and is not null.
     * 
     * @param l the luggage to be added to the current person's list.
     */
    public void addLuggage(Luggage l) {
        // Check whether the given luggage has already been added and that it is
        // not null.
        boolean alreadyExists = false;
        if (l != null) {
            for (Luggage item : this.luggage) {
                if (item != null && item.getLabel().equals(l.getLabel()) && item.getWeight() == l.getWeight()) {
                    alreadyExists = true;
                }
            }
        }

        if (!alreadyExists) {
            this.luggage.add(l);
        }
    }

    /**
     * Boards the current person onto the train by assigning them a unique
     * ticket ID.
     * 
     * @return the unique ticket ID assigned to the current person.
     */
    public int board() {
        this.ticketID = currTicket++;
        return this.ticketID;
    }

    /**
     * Returns a String representation of the current person, including their
     * name, their ticket ID, and their luggage information.
     * 
     * @return a String representation of the current person.
     */
    public String toString() {
        String output = String.format("%s (Ticket #%d) ", this.name, this.ticketID);

        // Concatenate luggage information.
        if (this.luggage.isEmpty()) {
            output += "with no luggage";
        } else {
            int luggageNum = this.luggage.size();
            output += "with " + luggageNum + " luggage: [";
            for (int i = 0; i < luggageNum - 1; i++) {
                output += this.luggage.get(i).getLabel() + ", ";
            }
            output += this.luggage.get(luggageNum - 1).getLabel() + "]";
        }

        return output;
    }

}
