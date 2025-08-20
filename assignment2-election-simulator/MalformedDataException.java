/**
 * MalformedDataException.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, N/A
 * 
 * @author Simon Wermie
 * @version N/A
 * 
 *          PURPOSE: custom exception to be thrown when malformed data is
 *          encountered while reading a given row of a CSV file.
 * 
 *          NOTE: This exception was fully created by by Simon Wermie, my
 *          instructor. I do not take credit for any of the work contained in
 *          this specific file.
 */

public class MalformedDataException extends Exception {
    public MalformedDataException(int row, String message) {
        super("Row #" + row + ": " + message);
    }
}