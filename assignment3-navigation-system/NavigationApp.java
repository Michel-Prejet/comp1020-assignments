
/**
 * NavigationApp.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 3, Bonus
 * 
 * @author Michel Préjet
 * @version August 1st, 2025
 * 
 *          PURPOSE: Provides a main interface for a starship navigation system,
 *          where users can load star systems, hyperspace lanes, and starships
 *          from CSV files, view these components and their properties, use 
 *          them to explore and analyze possible travel routes, and view all
 *          relevant route statistics.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NavigationApp {
    public static ArrayList<HyperspaceLane> spaceLanes;
    public static ArrayList<Starship> ships;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the Starship Navigation System");

        // Load data, prompting user for file names.
        loadStarSystems(in);
        loadHyperspaceLanes(in);
        loadStarships(in);

        displayStarships();

        // Print main menu and get selection from user.
        boolean promptAgain = true;
        do {
            printMainMenu();
            String choice = in.next().trim();
            switch (choice) {
                case "1":
                    displaySortedStarSystems();
                    break;
                case "2":
                    displaySortedHyperspaceLanes();
                    break;
                case "3":
                    routeAnalysis(in);
                    break;
                case "0":
                    promptAgain = false;
                    break;
                default:
                    break;
            }
        } while (promptAgain);

        System.out.println("Exiting navigation system.");

        in.close();
    }

    /**
     * Prints the main menu for the navigation app.
     */
    public static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. View star systems (sorted)");
        System.out.println("2. View hyperspace lanes (sorted)");
        System.out.println("3. Analyze routes for a given ship");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    /**
     * Prints all starships in the ships ArrayList by calling their toString()
     * method.
     */
    public static void displayStarships() {
        System.out.println("\nAvailable Starships:");
        for (int i = 0; i < ships.size(); i++) {
            System.out.println(i + ": " + ships.get(i).toString());
        }
    }

    /**
     * Loads star systems into the Navigation class from a CSV file whose name
     * is provided by the user. If the given file name doesn't correspond to any
     * file in the current directory, prints an error message and prompts the
     * user again.
     * 
     * @param in the Scanner object used to get user input.
     */
    public static void loadStarSystems(Scanner in) {
        String filename;
        boolean fileFound;
        do {
            System.out.print("Enter path to Star Systems file (include .csv):");
            filename = in.next();

            fileFound = true;
            try {
                Navigation.loadStarSystems(filename);
            } catch (IOException e) {
                fileFound = false;
                System.out.printf("Error loading Star Systems: %s\n", e.getMessage());
            }

        } while (!fileFound);
    }

    /**
     * Loads hyperspace lanes into the spaceLanes ArrayList from a CSV file whose
     * name is provided by the user. If the given file name doesn't correspond to
     * any file in the current directory, prints an error message and prompts the
     * user again.
     * 
     * @param in the Scanner object used to get user input.
     */
    public static void loadHyperspaceLanes(Scanner in) {
        String filename;
        boolean fileFound;
        do {
            System.out.print("Enter path to Hyperspace Lanes file (include .csv):");
            filename = in.next();

            fileFound = true;
            try {
                spaceLanes = Navigation.loadHyperspaceLanes(filename);
            } catch (IOException e) {
                fileFound = false;
                System.out.printf("Error loading Hyperspace Lanes: %s\n", e.getMessage());
            }

        } while (!fileFound);
    }

    /**
     * Loads starships into the ships ArrayList from a CSV file whose name is
     * provided by the user. If the given file name doesn't correspond to any
     * file in the current directory, prints an error message and prompts the
     * user again.
     * 
     * @param in the Scanner object used to get user input.
     */
    public static void loadStarships(Scanner in) {
        String filename;
        boolean fileFound;
        do {
            System.out.print("Enter path to Starships file (include .csv):");
            filename = in.next();

            fileFound = true;
            try {
                ships = Navigation.loadStarships(filename);
            } catch (IOException e) {
                fileFound = false;
                System.out.printf("Error loading Starships: %s\n", e.getMessage());
            }

        } while (!fileFound);
    }

    /**
     * Makes a copy of the ArrayList of all star systems in the Navigation class,
     * sorts it using selection sort, then prints each star system in order.
     */
    public static void displaySortedStarSystems() {
        ArrayList<StarSystem> systems = new ArrayList<StarSystem>(Navigation.systems);
        StarSystem.selectionSortByName(systems);
        for (StarSystem sys : systems) {
            System.out.println(sys);
        }
    }

    /**
     * Makes a copy of the spaceLanes ArrayList, sorts it using insertion
     * sort, then prints each space lane in order.
     */
    public static void displaySortedHyperspaceLanes() {
        ArrayList<HyperspaceLane> lanes = new ArrayList<HyperspaceLane>(spaceLanes);
        HyperspaceLane.insertionSortByFromName(lanes);
        for (HyperspaceLane lane : lanes) {
            System.out.println(lane);
        }
    }

    /**
     * Allows the user to view and analyze all valid routes between two given
     * star systems on a given starship. Prompts the user for the starting
     * system, the destination system, and the starship, then prints the
     * route analysis menu and acts according to the user's menu selection,
     * continuing to print the menu until the user enters "0" to quit.
     * 
     * @param in the Scanner object used to get user input.
     */
    public static void routeAnalysis(Scanner in) {
        // Get start and destination systems.
        System.out.print("Enter start system: ");
        String start = in.next();
        System.out.print("Enter destination system: ");
        String end = in.next();

        // Get starship.
        displayStarships();
        System.out.print("Select a ship for route analysis: ");
        int shipIndex = Integer.parseInt(in.next());
        Starship ship = ships.get(shipIndex);

        // Get all routes.
        ArrayList<Route> allRoutes = Navigation.findAllRoutes(start, end, ship);

        // Print analysis menu and get selection from user.
        boolean promptAgain;
        do {
            printAnalysisMenu();
            String choice = in.next();
            promptAgain = true;
            switch (choice) {
                case "1":
                    displayAllRoutes(allRoutes);
                    break;
                case "2":
                    printShortestRoute(allRoutes);
                    break;
                case "3":
                    printLongestRoute(allRoutes);
                    break;
                case "4":
                    printCheapestRoute(allRoutes);
                    break;
                case "5":
                    printMostHostile(allRoutes);
                    break;
                case "6":
                    printTollFreeRoutes(allRoutes);
                    break;
                case "7":
                    printSafeRoutes(allRoutes);
                    break;
                case "0":
                    promptAgain = false;
                    break;
                default:
                    break;
            }
        } while (promptAgain);

    }

    /**
     * Prints a menu containing options to analyze a given set of routes.
     */
    public static void printAnalysisMenu() {
        System.out.println("\n--- Route Analysis ---");
        System.out.println("1. Show all routes");
        System.out.println("2. Shortest distance");
        System.out.println("3. Longest distance");
        System.out.println("4. Least expensive");
        System.out.println("5. Most hostile");
        System.out.println("6. Toll-free routes");
        System.out.println("7. Safe routes (no enemy encounters)");
        System.out.println("0. Back to main menu");
        System.out.println("Enter choice: ");
    }

    /**
     * Prints all routes in a given ArrayList of Route.
     * 
     * @param routes the ArrayList of Route to be displayed.
     */
    public static void displayAllRoutes(ArrayList<Route> routes) {
        int numRoutes = 0;
        for (Route route : routes) {
            System.out.println();
            System.out.println(route);
            numRoutes++;
        }
        System.out.println("Total routes found: " + numRoutes);
    }

    /**
     * Prints the shortest route in a given ArrayList of Route.
     * 
     * @param routes the ArrayList of Route from which the shortest route should
     *               be printed.
     */
    public static void printShortestRoute(ArrayList<Route> routes) {
        System.out.println("\nShortest route:");
        Route shortest = Navigation.getShortestDistance(routes);
        if (shortest != null) {
            System.out.print(shortest);
        }
        System.out.println();
    }

    /**
     * Prints the longest route in a given ArrayList of Route.
     * 
     * @param routes the ArrayList of Route from which the longest route should
     *               be printed.
     */
    public static void printLongestRoute(ArrayList<Route> routes) {
        System.out.println("\nLongest route:");
        Route longest = Navigation.getLongestDistance(routes);
        if (longest != null) {
            System.out.print(longest);
        }
        System.out.println();
    }

    /**
     * Prints the least expensive route in a given ArrayList of Route.
     * 
     * @param routes the ArrayList of Route from which the least expensive route
     *               should be printed.
     */
    public static void printCheapestRoute(ArrayList<Route> routes) {
        System.out.println("\nLeast expensive route:");
        Route cheapest = Navigation.getLeastExpensive(routes);
        if (cheapest != null) {
            System.out.println(cheapest);
        }
        System.out.println();
    }

    /**
     * Prints the route in a given ArrayList of Route with the highest total
     * danger.
     * 
     * @param routes the ArrayList of Route from which the route with the
     *               highest total danger should be printed.
     */
    public static void printMostHostile(ArrayList<Route> routes) {
        System.out.println("\nMost hostile route:");
        Route mostHostile = Navigation.getMostHostile(routes);
        if (mostHostile != null) {
            System.out.println(mostHostile);
        }
        System.out.println();
    }

    /**
     * Prints all safe routes in a given ArrayList of Route (those without
     * enemy encounters) or "No safe routes found" if none exist.
     * 
     * @param routes the ArrayList of Route from which all safe routes should
     *               be printed.
     */
    public static void printSafeRoutes(ArrayList<Route> routes) {
        ArrayList<Route> safeRoutes = Navigation.getSafeRoutes(routes);
        if (safeRoutes.size() > 0) {
            System.out.println("\nSafe routes:");
            for (Route route : safeRoutes) {
                System.out.println();
                System.out.println(route);
            }
        } else {
            System.out.println("No safe routes found.");
        }
    }

    /**
     * Prints all toll free routes in a given ArrayList of Route or "No toll-free
     * routes found" if none exist.
     * 
     * @param routes the ArrayList of Route from which all toll free routes should
     *               be printed.
     */
    public static void printTollFreeRoutes(ArrayList<Route> routes) {
        ArrayList<Route> tollFreeRoutes = Navigation.getTollFreeRoutes(routes);
        if (tollFreeRoutes.size() > 0) {
            System.out.println("\nToll-free routes:");
            for (Route route : tollFreeRoutes) {
                System.out.println();
                System.out.println(route);
            }
        } else {
            System.out.println("No toll-free routes found.");
        }
    }
}
