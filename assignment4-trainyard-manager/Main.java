/**
 * Main.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 4, N/A
 * 
 * @author Michel Préjet
 * @version August 19th, 2025
 * 
 *          PURPOSE: Program demo. Demonstrates core functions of the trainyard
 *          manager, including adding and modifying various trains, train cars,
 *          passengers, and luggage, and shunting train cars from one train to
 *          the other.
 */

public class Main {
    public static void main(String[] args) {
        // Create a trainyard.
        TrainYard yard1 = new TrainYard();

        // Add people to the trainyard and assign them luggage items.
        yard1.createPerson("Alice");
        yard1.createLuggage("Book", 2, "Alice");
        yard1.createLuggage("Clothes", 5, "Alice");

        yard1.createPerson("John");
        yard1.createLuggage("iPad", 2, "John");
        yard1.createLuggage("Toothbrush", 1, "John");
        yard1.createLuggage("Jewelry", 6, "John");

        yard1.createPerson("Mary");
        yard1.createLuggage("Money", 4, "Mary");
        yard1.createLuggage("Map", 1, "Mary");

        // Add a train to the trainyard.
        yard1.createTrain("Train1");
        Train train1 = yard1.getTrain("Train1");

        // Add train cars to the train.
        FuelCar t1fuel1 = new FuelCar(100);
        FuelCar t1fuel2 = new FuelCar(90);
        FuelCar t1fuel3 = new FuelCar(105);
        LuggageCar t1luggage1 = new LuggageCar(5);
        PassengerCar t1passenger1 = new PassengerCar(3, 5);
        train1.addCar(t1fuel1);
        train1.addCar(t1luggage1);
        train1.addCar(t1fuel2);
        train1.addCar(t1passenger1);
        train1.addCar(t1fuel3);

        // Refuel all the fuel cars.
        t1fuel1.refuel(90);
        t1fuel2.refuel(90);
        t1fuel3.refuel(100);

        // Initialize luggage compartments.
        t1luggage1.initializeCompartment(0, 3);
        t1luggage1.initializeCompartment(1, 2);
        t1luggage1.initializeCompartment(2, 5);

        // Board all passengers and their luggage.
        yard1.addPassengerToTrain("Train1", "Alice");
        yard1.addPassengerToTrain("Train1", "John");
        yard1.addPassengerToTrain("Train1", "Mary");

        // Consume fuel.
        t1fuel1.consumeFuel(10);
        t1fuel2.consumeFuel(40);
        t1fuel2.consumeFuel(90);

        // Print train and train cars added up to now.
        System.out.println("\n>> Printing Train1 info:");
        System.out.println(train1);
        System.out.println(t1fuel1);
        System.out.println(t1fuel1.toStringContents());
        System.out.println(t1luggage1);
        System.out.print(t1luggage1.toStringContents());
        System.out.println(t1passenger1);
        System.out.println(t1passenger1.toStringContents());
        System.out.println("Maximum distance: " + train1.calculateMaxDistance());

        // Sort train cars.
        train1.sortCarsByType();
        System.out.println("\n>> Sorted train cars.");

        // Remove some train cars and passengers, and refuel.
        train1.removeCar(t1fuel3);
        System.out.println("\n>> Removed fuel car 3.");
        train1.deboardPassenger(yard1.getPerson("Alice"));
        System.out.println("\n>> Removed Alice (and her luggage).");
        t1fuel1.refuel(20);
        System.out.println("\n>> Refueled fuel car 1.");

        // Print the above information again.
        System.out.println("\n>> Printing Train1 info:");
        System.out.println(train1);
        System.out.println(t1fuel1);
        System.out.println(t1fuel1.toStringContents());
        System.out.println(t1luggage1);
        System.out.print(t1luggage1.toStringContents());
        System.out.println(t1passenger1);
        System.out.println(t1passenger1.toStringContents());
        System.out.println("Maximum distance: " + train1.calculateMaxDistance());

        // Add more trains.
        yard1.createTrain("Train2");
        Train train2 = yard1.getTrain("Train2");

        FuelCar t2fuel1 = new FuelCar(100);
        PassengerCar t2passenger1 = new PassengerCar(3, 3);
        LuggageCar t2luggage1 = new LuggageCar(10);
        train2.addCar(t2fuel1);
        train2.addCar(t2passenger1);
        train2.addCar(t2luggage1);

        yard1.createTrain("Train3");
        Train train3 = yard1.getTrain("Train3");

        FuelCar t3fuel1 = new FuelCar(50);
        FuelCar t3fuel2 = new FuelCar(100);
        train3.addCar(t3fuel1);
        train3.addCar(t3fuel2);

        System.out.println("\n>> Added two more trains to the trainyard.");

        // Print the trainyard.
        System.out.println("\n>> Printing trainyard:");
        System.out.println(yard1);

        // Shunt train cars between trains.
        yard1.shuntFromIndex("Train2", "Train3", 1);
        System.out.println("\n>> Shunted Train2 from index 1 onto Train3.");

        System.out.println("\n>> Printing trainyard:");
        System.out.println(yard1);

        yard1.shuntFromCarType("Train1", "Train2", "Passenger");
        System.out.println("\n>> Shunted Train1 from the first passenger car onto Train2.");

        System.out.println("\n>> Printing trainyard:");
        System.out.println(yard1);

        yard1.shuntWholeTrain("Train3", "Train1");
        System.out.println("\n>> Shunted all of Train3 onto Train1.");

        System.out.println("\n>> Printing trainyard:");
        System.out.println(yard1);

    }
}