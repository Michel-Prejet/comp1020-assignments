
/**
 * CSSApp.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 1, N/A
 * 
 * @author Simon Wermie
 * @version N/A
 * 
 *          PURPOSE: Provides the main interface for the starship fleet management
 *          system, where users can create and clone starships, add various types
 *          of modules, send ships on expeditions, and access all information 
 *          relevant to the fleet.
 * 
 *          NOTE: this interface was fully created by Simon Wermie, my
 *          instructor, as a template for this assignment. I do not take credit
 *          for any of the work contained in this specific file.
 */

import java.util.Random;
import java.util.Scanner;

public class CSSApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FleetManager fleetManager = new FleetManager(50); // number of ships in the fleet
        Random random = new Random();

        Starship selectedShip = null;
        boolean running = true;

        // Main loop
        while (running) {
            if (selectedShip == null) { // we are not in the ship menu if we don't have one selected (so that means we
                                        // are in the fleet menu)
                // fleet-level menu
                System.out.println(
                        "\nFleet Command Menu\n" +
                                "1. Create New Starship\n" +
                                "2. Create New Starship From Copy\n" +
                                "3. List All Starships\n" +
                                "4. Select Starship\n" +
                                "5. Send Expedition\n" +
                                "6. View Expedition Log\n" +
                                "7. Exit");
                System.out.print("Choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice == 1) {
                    System.out.print("Enter starship name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter module capacity: ");
                    int cap = scanner.nextInt();
                    scanner.nextLine();
                    String result = (fleetManager.addStarship(name, cap)) ? "CSS " + name + " has joind the fleet!"
                            : "Starship failed to create.";
                    System.out.println(result);
                } else if (choice == 2) {
                    System.out.print("Enter name of starship to copy: ");
                    String sourceName = scanner.nextLine();

                    System.out.print("Enter name for the new starship: ");
                    String newName = scanner.nextLine();

                    boolean copied = fleetManager.copyStarship(sourceName, newName);

                    if (copied) {
                        System.out.println("Starship CSS \"" + newName + "\" created from \"" + sourceName + "\".");
                    } else {
                        System.out.println("Failed to copy ship. Check the source name or fleet capacity.");
                    }
                } else if (choice == 3) {
                    System.out.println(fleetManager.listStarships());
                } else if (choice == 4) {
                    System.out.print("Enter starship name to select: ");
                    String selName = scanner.nextLine();
                    selectedShip = fleetManager.findStarshipByName(selName);
                    if (selectedShip == null) {
                        System.out.println("Ship not found.");
                    }
                } else if (choice == 5) {
                    System.out.print("Enter starship for expedition (enter for random): ");
                    String selName = scanner.nextLine();
                    Starship expShip = null;

                    if (selName.isEmpty()) {
                        String list = fleetManager.listStarships();
                        if (!list.isEmpty()) {
                            String[] shipNames = list.split("\\n");
                            String randomName = shipNames[random.nextInt(shipNames.length)];
                            expShip = fleetManager.findStarshipByName(randomName);
                            if (expShip != null) {
                                System.out.println("Randomly selected: " + expShip.getName());
                            }
                        }

                        if (expShip == null) {
                            System.out.println("No ships available.");
                        }

                    } else {
                        expShip = fleetManager.findStarshipByName(selName);
                        if (expShip == null) {
                            System.out.println("Ship not found.");
                        }
                    }

                    if (expShip != null) {
                        Expedition outcome = fleetManager.sendExpedition(expShip, random);
                        if (outcome != null) {
                            System.out.println("Expedition Outcome: " + outcome);
                        } else {
                            System.out.println(expShip.getName()
                                    + " could not depart on the expedition as it does not have the required modules (Engine & Life Support) installed or they are damaged");
                        }
                    }
                } else if (choice == 6) {
                    System.out.println(fleetManager.getExpeditionLog());
                } else if (choice == 7) {
                    running = false;
                } else {
                    System.out.println("Invalid choice.");
                }

            } else {
                // ship-level menu (a ship has been selected)
                System.out.println(
                        "\n" + selectedShip.toString() + "\n\nStarship Command Menu\n" +
                                "1. Add Module\n" +
                                "2. Perform Maintenance\n" +
                                "3. Return to Fleet Menu");
                System.out.print("Choice: ");
                int subChoice = scanner.nextInt();
                scanner.nextLine();

                if (subChoice == 1) {
                    // adding a module
                    System.out.println(
                            "Choose module type:\n" +
                                    "1. Engine\n" +
                                    "2. Life Support\n" +
                                    "3. Weapon\n" +
                                    "4. Storage");
                    System.out.print("Choice: ");
                    int type = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter module name: ");
                    String modName = scanner.nextLine();
                    boolean result = false;

                    if (type == 1) {
                        System.out.print("Enter fuel capacity: ");
                        int fuel = scanner.nextInt();
                        scanner.nextLine();
                        result = selectedShip.addModule(new EngineModule(modName, fuel));
                    } else if (type == 2) {
                        System.out.print("Enter O2 capacity: ");
                        int o2 = scanner.nextInt();
                        scanner.nextLine();
                        result = selectedShip.addModule(new LifeSupportModule(modName, o2));
                    } else if (type == 3) {
                        System.out.print("Enter ammo count: ");
                        int ammo = scanner.nextInt();
                        scanner.nextLine();
                        result = selectedShip.addModule(new WeaponModule(modName, ammo));
                    } else if (type == 4) {
                        System.out.print("Enter storage capacity: ");
                        int capacity = scanner.nextInt();
                        scanner.nextLine();
                        result = selectedShip.addModule(new StorageModule(modName, capacity));
                    } else {
                        System.out.println("Invalid module type.");
                    }

                    String resultPrint = result ? "Module added."
                            : "Module failed to be added: not enough module slots remain.";
                    System.out.println(resultPrint);

                } else if (subChoice == 2) {
                    selectedShip.maintainAllModules();
                    System.out.println("All modules maintained.");
                } else if (subChoice == 3) {
                    selectedShip = null; // return to fleet menu
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        }

        System.out.println("Exiting Fleet Command.");
        scanner.close();
    }
}
