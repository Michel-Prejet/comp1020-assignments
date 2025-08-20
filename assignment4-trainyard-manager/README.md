# COMP 1020 Assignment 4: Trainyard Manager

This project was built as an assignment for COMP 1020: Introductory Computer Science 2 at the University of Manitoba. The assignment emphasizes linked lists, multidimensional arrays, and interfaces as well as OOP (object-oriented programming), string manipulation, sorting algorithms, and exception handling in Java.
The program itself is CLI-based and simulates a modular train management system in a trainyard. It supports adding and manipulating trains made up of multiple types of train cars (passenger, luggage, or fuel), adding/removing and locating/retrieving train cars, passengers and luggage, and computing train statistics. 

---

## General Overview

Core functions of the program include:

- Building trainyards with several trains, passengers, and luggage items.
  - Creating train cars of various types (fuel, luggage, passenger) and adding/removing them to/from a train.
    - Consuming fuel from a fuel car and refuelling it.
    - Initializing luggage compartments in a luggage car.
  - Creating passengers and assigning them luggage items.
  - Boarding/deboarding passengers to/from trains.
- Sorting all train cars in a train alphabetically according to their type.
- Shunting train cars between trains.
- Getting the maximum distance a train can travel.
- Locating and retrieving trains, train cars, passengers, and luggage.

---

## Additional Context

- Upon boarding a train, passengers are assigned a unique ticket ID.
  - The default ticket ID of unboarded passengers is 0.
  - The ticket ID does not return to 0 if the passenger is deboarded, however they will receive a new ID if they are boarded again.
- The maximum distance a train can travel is calculated by obtaining the combined capacity of all of its fuel cars, then dividing it by ten times the total number of train cars.
- Each type of train car has a toStringContents() method which returns a visual representation of the train car, whereas the overridden toString() methods provide a more concise representation.

## Technologies

- Built with Java (JDK 23).
- No external libraries.

---

## How To Run

This program does not have a proper interface. To interact with it, use the provided `Main.java` file, which already contains some demo code. Then, navigate to the project directory in the command line and type the following:

```bash
javac *.java
java Main
```

---

## File Structure

The program consists of 9 files:
- `Main.java` - used to interact with the program (already contains demo code).
- `TrainYard.java` - simulates a central trainyard manager.
- `Train.java` - simulates a train as a linked list of train cars.
- `iTrainCar.java` - interface for train car implementations.
- `LuggageCar.java` - luggage car implementation.
- `PassengerCar.java` - passenger car implementation.
- `FuelCar.java` - fuel car implementation.
- `Person.java` - passenger implementation.
- `Luggage.java` - luggage implementation.

---

## Important Notes

The instructor for the course, Simon Wermie, provided the entirety of `Luggage.java` as well as a template for the other classes. I do not take credit for any code contained within `Luggage.java` or the class skeletons provided for the other files; however, the remaining implementation logic is my own. 

---

## License

All code is licensed under the MIT license, with the exception of instructor-provided code, as described above.

---

## Author

Michel Préjet
Computer Science Student, University of Manitoba
[prejetm@myumanitoba.ca](mailto:prejetm@myumanitoba.ca)

_Last updated: August 19, 2025_
