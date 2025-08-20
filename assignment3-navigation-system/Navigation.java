
/**
 * Navigation.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 3, 4
 * 
 * @author Michel Préjet
 * @version August 1st, 2025
 * 
 *          PURPOSE: Represents a navigation system's core functionality. 
 *          Contains a public ArrayList of StarSystem containing all star systems 
 *          in the navigation system. Provides methods to load star systems,
 *          hyperspace lanes, and starships from CSV files, to find star systems
 *          by name, and to find all possible routes between two star systems.
 *          Also contains methods taking in an ArrayList of Route which allow the
 *          user to get the shortest and longest routes in terms of distance, the
 *          route with the highest danger level, the route with the lowest cost,
 *          all routes that don't involve enemy encounters, and all routes that
 *          are toll free.
 * 
 *          NOTE: the skeleton of this class was provided as a template by
 *          instructor Simon Wermie. Implementation logic was written by me.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Navigation {
    public static ArrayList<StarSystem> systems = new ArrayList<>();

    /**
     * Populates the systems ArrayList by reading information from a CSV file.
     * Splits each line into tokens and validates them, then creates a new
     * star system and adds it to systems if it hasn't already been added. Clears
     * the systems ArrayList before adding any new star systems.
     * 
     * @param filename the name of the CSV file from which information should be
     *                 read.
     * @throws IOException if a problem is encountered while trying to read the
     *                     given file.
     */
    public static void loadStarSystems(String filename) throws IOException {
        systems.clear();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String tokens[];

        String line = br.readLine();
        line = br.readLine(); // Skip header.
        while (line != null) {
            tokens = line.split(",");

            // If the tokens are valid, create a new star system.
            if (validateStarSystemTokens(tokens)) {
                // Get parameters from tokens.
                String name = tokens[0];
                String faction = "Neutral";
                if (!tokens[1].trim().isEmpty()) {
                    faction = tokens[1];
                }
                int danger = Integer.parseInt(tokens[2]);

                // Check that the star system hasn't already been added.
                StarSystem toAdd = new StarSystem(name, faction, danger);
                boolean alreadyAdded = false;
                for (StarSystem sys : systems) {
                    if (sys.equals(toAdd)) {
                        alreadyAdded = true;
                    }
                }

                if (!alreadyAdded) {
                    systems.add(toAdd);
                }
            }

            line = br.readLine();
        }

        br.close();
    }

    /**
     * Populates an ArrayList of HyperspaceLane by reading information from a
     * CSV file. Splits each line into tokens and validates them, then creates a
     * new hyperspace lane and adds it to the ArrayList to be returned if it hasn't
     * already been added, and if both the hyperspace lane's systems are found in
     * the systems ArrayList. Also adds each created hyperspace lane to both
     * appropriate star system's list of connected lanes.
     * 
     * @param filename the name of the CSV file from which information should be
     *                 read.
     * @return an ArrayList of HyperspaceLane containing each successfully created
     *         hyperspace lane.
     * @throws IOException if a problem is encountered while trying to read the
     *                     given file.
     */
    public static ArrayList<HyperspaceLane> loadHyperspaceLanes(String filename) throws IOException {
        ArrayList<HyperspaceLane> lanes = new ArrayList<HyperspaceLane>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] tokens;

        String line = br.readLine();
        line = br.readLine(); // Skip header.
        while (line != null) {
            tokens = line.split(",");

            // If the tokens are valid, create a new hyperspace lane.
            if (validateHyperspaceLaneTokens(tokens)) {
                // Get parameters from tokens.
                String fromSystem = tokens[0];
                String toSystem = tokens[1];
                double distance = Double.parseDouble(tokens[2]);
                double tollCost = Double.parseDouble(tokens[3]);

                // Check that the hyperspace lane hasn't already been added.
                HyperspaceLane toAdd = new HyperspaceLane(fromSystem, toSystem, distance, tollCost);
                boolean alreadyAdded = false;
                for (HyperspaceLane lane : lanes) {
                    if (lane.equals(toAdd)) {
                        alreadyAdded = true;
                    }
                }

                // Check that both of the hyperspace lane's systems are found
                // in the systems ArrayList.
                boolean fromSystemExists = false;
                boolean toSystemExists = false;
                StarSystem fromSysObj = null;
                StarSystem toSysObj = null;
                for (StarSystem sys : systems) {
                    if (sys.getName().equals(fromSystem)) {
                        fromSystemExists = true;
                        fromSysObj = sys;
                    }
                    if (sys.getName().equals(toSystem)) {
                        toSystemExists = true;
                        toSysObj = sys;
                    }
                }

                if (!alreadyAdded && fromSystemExists && toSystemExists) {
                    lanes.add(toAdd);
                    fromSysObj.addLane(toAdd);
                    toSysObj.addLane(toAdd);
                }
            }

            line = br.readLine();
        }

        br.close();

        return lanes;
    }

    /**
     * Populates an ArrayList of Starship by reading information from a CSV file.
     * Splits each line into tokens and validates them, then creates a new
     * starship lane and adds it to the ArrayList to be returned if it hasn't
     * already been added.
     * 
     * @param filename the name of the CSV file from which information should be
     *                 read.
     * @return an ArrayList of Starship containing each successfully created
     *         starship.
     * @throws IOException if a problem is encountered while trying to read the
     *                     given file.
     */
    public static ArrayList<Starship> loadStarships(String filename) throws IOException {
        ArrayList<Starship> ships = new ArrayList<Starship>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] tokens;

        String line = br.readLine();
        line = br.readLine(); // Skip header.
        while (line != null) {
            tokens = line.split(",");

            // If the tokens are valid, create a new starship.
            if (validateStarshipTokens(tokens)) {
                // Get parameters from tokens.
                String name = tokens[0];
                String faction = tokens[1];
                double amuLY = Double.parseDouble(tokens[2]);
                double costPerAMU = Double.parseDouble(tokens[3]);

                // Populate enemyFactions, if such a token was provided.
                ArrayList<String> enemyFactions = new ArrayList<String>();
                if (tokens.length == 5) {
                    String enemyList = tokens[4];
                    while (enemyList.indexOf("|") != -1) {
                        int barIndex = enemyList.indexOf("|");
                        enemyFactions.add(enemyList.substring(0, barIndex).trim());
                        enemyList = enemyList.substring(barIndex + 1);
                    }
                    if (!enemyList.isEmpty()) {
                        enemyFactions.add(enemyList.trim());
                    }
                }

                // Check that the starship hasn't already been added.
                Starship toAdd = new Starship(name, faction, costPerAMU, amuLY, enemyFactions);
                boolean alreadyExists = false;
                for (Starship ship : ships) {
                    if (ship.equals(toAdd)) {
                        alreadyExists = true;
                    }
                }

                if (!alreadyExists) {
                    ships.add(toAdd);
                }
            }

            line = br.readLine();
        }

        br.close();

        return ships;
    }

    /**
     * Searches the systems ArrayList for a star system matching the given name.
     * 
     * @param name the name of the star system to be searched for.
     * @return the star system matching the given name, or null if no such star
     *         system is found.
     */
    public static StarSystem findSystem(String name) {
        StarSystem toReturn = null;
        for (StarSystem sys : systems) {
            if (sys.getName().toLowerCase().trim().equals(name.toLowerCase().trim())) {
                toReturn = sys;
            }
        }
        return toReturn;
    }

    /**
     * Finds all valid routes between two star systems with given names using
     * a given starship, and adds them an ArrayList of Route.
     * See bootstrap method for more details.
     * 
     * @param start       the star system of origin.
     * @param end         the destination star system.
     * @param currentShip the starship used to travel the routes.
     * @return an ArrayList of Route containing all valid routes between the
     *         star system of origin and the destination star system.
     */
    public static ArrayList<Route> findAllRoutes(String start, String end, Starship currentShip) {
        ArrayList<Route> routes = new ArrayList<Route>();
        if (start == null || end == null || findSystem(start) == null || findSystem(end) == null) {
            return routes;
        }
        return findAllRoutes(start, start, end, currentShip, new Route(), routes);
    }

    /**
     * Bootstrap method for findAllRoutes(). Iterates through every neighbor
     * of the starting star system, finds the hyperspace lane connecting that
     * neighbor, then checks whether that neighbor is the destination star system,
     * and whether it corresponds to the original starting point.
     * If the neighbor corresponds to the origin, the current path is aborted and
     * the algorithm moves to the next neighbor. If the neighbor corresponds to
     * the destination, it is added to systemsVisited for the current instance,
     * and that route is added to the ArrayList to be returned if it doesn't
     * already exist. If the neighbor isn't the destination, but isn't contained
     * in the current route, it is added to systemsVisited, then the method is
     * called recursively on that neighbor. Neighbors will continue to be appended
     * onto the current route until a star system is reached that isn't connected
     * to any unadded neighbors, or until the destination system is reached in
     * a unique way.
     * 
     * @param origin      the star system which serves as the starting point of all
     *                    routes (excluded from systemsVisited).
     * @param start       the current star system for which neighbors are to be
     *                    found and added to systemsVisited appropriately.
     * @param end         the destination star system for all routes (included in
     *                    systemsVisited).
     * @param currentShip the starship travelling the route (needed in order to
     *                    add hops to the route).
     * @param route       the current route that is being explored/built.
     * @param routes      the ArrayList of Route containing valid routes found
     *                    between the origin and the destination star systems (may
     *                    be incomplete).
     * @return an ArrayList of Route containing all valid routes found between the
     *         origin and the destination star systems.
     */
    private static ArrayList<Route> findAllRoutes(String origin, String start, String end, Starship currentShip,
            Route route,
            ArrayList<Route> routes) {
        for (String next : findSystem(start).getNeighboringSystems()) {
            // Get the hyperspace lane joining the starting point and its neighbor.
            HyperspaceLane lane = null;
            for (HyperspaceLane currLane : findSystem(start).getConnectedLanes()) {
                if (currLane.containsSystem(next)) {
                    lane = currLane;
                }
            }

            // If the neighbor is the endpoint, add it to a deep copy of the
            // route. If the path doesn't already exist, add that route to the
            // ArrayList.
            if (next.equalsIgnoreCase(end) && !next.equalsIgnoreCase(origin)) {
                Route copy = new Route(route);
                copy.addHop(findSystem(next), lane, currentShip);
                if (!routeExists(routes, copy)) {
                    routes.add(copy);
                }
            }

            // If the neighbor is not the endpoint and isn't already found in
            // the path, add it to a deep copy of the route, then call
            // findAllRoutes recursively on the neighbor.
            else if (!systemExistsInRoute(route, findSystem(next)) && !next.equalsIgnoreCase(origin)) {
                Route copy = new Route(route);
                copy.addHop(findSystem(next), lane, currentShip);
                findAllRoutes(origin, next, end, currentShip, copy, routes);
            }
        }

        return routes;
    }

    /**
     * Returns the route with the shortest total distance in a given ArrayList
     * of Route.
     * 
     * @param routes the ArrayList in which the route with the shortest total
     *               distance is to be retrieved.
     * @return the route with the shortest total distance in the given ArrayList,
     *         or null if the ArrayList is null or empty.
     */
    public static Route getShortestDistance(ArrayList<Route> routes) {
        Route shortest;
        if (routes == null || routes.size() == 0) {
            shortest = null;
        } else {
            shortest = routes.get(0);
        }

        for (Route route : routes) {
            if (route.getTotalDistance() < shortest.getTotalDistance()) {
                shortest = route;
            }
        }

        return shortest;
    }

    /**
     * Returns the route with the greatest total distance in a given ArrayList
     * of Route.
     * 
     * @param routes the ArrayList in which the route with the greatest total
     *               distance is to be retrieved.
     * @return the route with the greatest total distance in the given ArrayList,
     *         or null if the ArrayList is null or empty.
     */
    public static Route getLongestDistance(ArrayList<Route> routes) {
        Route longest;
        if (routes == null || routes.size() == 0) {
            longest = null;
        } else {
            longest = routes.get(0);
        }

        for (Route route : routes) {
            if (route.getTotalDistance() > longest.getTotalDistance()) {
                longest = route;
            }
        }

        return longest;
    }

    /**
     * Returns the route with the highest total danger level in a given ArrayList
     * of Route.
     * 
     * @param routes the ArrayList in which the route with the highest total
     *               danger level is to be retrieved.
     * @return the route with the highest total danger level in the given ArrayList,
     *         or null if the ArrayList is null or empty.
     */
    public static Route getMostHostile(ArrayList<Route> routes) {
        Route mostHostile;
        if (routes == null || routes.size() == 0) {
            mostHostile = null;
        } else {
            mostHostile = routes.get(0);
        }

        for (Route route : routes) {
            if (route.getTotalDanger() > mostHostile.getTotalDanger()) {
                mostHostile = route;
            }
        }

        return mostHostile;
    }

    /**
     * Returns the route with the lowest total cost in a given ArrayList of
     * Route.
     * 
     * @param routes the ArrayList in which the route with the lowest total
     *               cost is to be retrieved.
     * @return the route with the lowest total cost in the given ArrayList,
     *         or null if the ArrayList is null or empty.
     */
    public static Route getLeastExpensive(ArrayList<Route> routes) {
        Route leastExpensive;
        if (routes == null || routes.size() == 0) {
            leastExpensive = null;
        } else {
            leastExpensive = routes.get(0);
        }

        for (Route route : routes) {
            if (route.getTotalCost() < leastExpensive.getTotalCost()) {
                leastExpensive = route;
            }
        }

        return leastExpensive;
    }

    /**
     * Returns an ArrayList of all routes in a given ArrayList where the starship
     * did not encounter any enemy factions.
     * 
     * @param routes the ArrayList of Route to be filtered based on enemy encounter
     *               status.
     * @return an ArrayList of Route of all routes in the given ArrayList where
     *         the ship did not encounter any enemy factions.
     */
    public static ArrayList<Route> getSafeRoutes(ArrayList<Route> routes) {
        ArrayList<Route> safeRoutes = new ArrayList<Route>();
        for (Route route : routes) {
            if (!route.hasEnemyEncounter()) {
                safeRoutes.add(route);
            }
        }
        return safeRoutes;
    }

    /**
     * Returns an ArrayList of all routes in a given ArrayList that are toll free.
     * 
     * @param routes the ArrayList of Route to be filtered based on toll status.
     * @return an ArrayList of Route of all the routes in the given ArrayList that
     *         are toll free.
     */
    public static ArrayList<Route> getTollFreeRoutes(ArrayList<Route> routes) {
        ArrayList<Route> tollFreeRoutes = new ArrayList<Route>();
        for (Route route : routes) {
            if (route.isTollFree()) {
                tollFreeRoutes.add(route);
            }
        }
        return tollFreeRoutes;
    }

    /**
     * Checks whether the tokens read from a line in a file are valid and will
     * allow for a new star system to be created. Checks that there are exactly
     * three tokens, neither of which are null, and that the first and third
     * tokens are not empty or only whitespace. Also checks that the third
     * token contains only digits.
     * 
     * @param tokens the array of String containing the tokens to be validated.
     * @return true if the tokens are valid; false otherwise.
     */
    private static boolean validateStarSystemTokens(String[] tokens) {
        boolean isValid = true;

        if (tokens.length != 3) {
            isValid = false;
        } else if (nullEmptyWhitespace(tokens[0])) {
            isValid = false;
        } else if (tokens[1] == null) {
            isValid = false;
        } else if (nullEmptyWhitespace(tokens[2])) {
            isValid = false;
        } else if (!onlyDigits(tokens[2])) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Checks whether the tokens read from a line in a file are valid and will
     * allow for a new hyperspace lane to be created. Checks that there are exactly
     * four tokens, neither of which are null, empty, or whitespace, and that the
     * third and fourth tokens contain only digits.
     * 
     * @param tokens the array of String containing the tokens to be validated.
     * @return true if the tokens are valid; false otherwise.
     */
    private static boolean validateHyperspaceLaneTokens(String[] tokens) {
        boolean isValid = true;

        if (tokens.length != 4) {
            isValid = false;
        } else if (nullEmptyWhitespace(tokens[0]) || nullEmptyWhitespace(tokens[1]) || nullEmptyWhitespace(tokens[2])
                || nullEmptyWhitespace(tokens[3])) {
            isValid = false;
        } else if (!onlyDigits(tokens[2]) || !onlyDigits(tokens[3])) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Checks whether the tokens read from a line in a file are valid and will
     * allow for a new starship to be created. Checks that there are exactly
     * four or five tokens, neither of which are null, empty, or whitespace, and
     * that the third and fourth tokens contain only digits.
     * 
     * @param tokens the array of String containing the tokens to be validated.
     * @return true if the tokens are valid; false otherwise.
     */
    private static boolean validateStarshipTokens(String[] tokens) {
        boolean isValid = true;

        if (tokens.length != 4 && tokens.length != 5) {
            isValid = false;
        } else if (nullEmptyWhitespace(tokens[0]) || nullEmptyWhitespace(tokens[1]) || nullEmptyWhitespace(tokens[2])
                || nullEmptyWhitespace(tokens[3])) {
            isValid = false;
        } else if (!onlyDigits(tokens[2]) || !onlyDigits(tokens[3])) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Checks whether a given string is null, empty, or only whitespace.
     * 
     * @param s the string to be validated.
     * @return true if the string is null, empty, or only whitespace; false
     *         otherwise.
     */
    private static boolean nullEmptyWhitespace(String s) {
        return (s == null) || (s.trim().isEmpty());
    }

    /**
     * Checks whether a given string contains only digits (with the possible
     * exception of a single period).
     * 
     * @param s the string to be validated.
     * @return true if the given string contains only digits with the exception of
     *         up to one period; false otherwise.
     */
    private static boolean onlyDigits(String s) {
        boolean toReturn = true;

        if (s.indexOf(".") != s.lastIndexOf(".")) {
            toReturn = false;
        }

        String noDecimalPlace = s.replace(".", "");
        for (int i = 0; i < noDecimalPlace.length(); i++) {
            if (!Character.isDigit(noDecimalPlace.charAt(i))) {
                toReturn = false;
            }
        }

        return toReturn;
    }

    /**
     * Checks whether a given star system already exists in a given route.
     * 
     * @param route  the route whose systemsVisited list should be searched through.
     * @param system the star system whose is existence is to be determined in the
     *               given route.
     * @return true if the given star system was found in the given route; false
     *         otherwise.
     */
    private static boolean systemExistsInRoute(Route route, StarSystem system) {
        boolean systemExists = false;
        for (String sys : route.getSystemsVisited()) {
            if (sys.equals(system.getName())) {
                systemExists = true;
            }
        }
        return systemExists;
    }

    /**
     * Checks whether a given route already exists in a given ArrayList of Route.
     * Equality is based on the sequence of star systems visited in the route
     * (where order is significant).
     * 
     * @param routes the ArrayList of Route to search through.
     * @param route  the route whose existence is to be determined in the given
     *               ArrayList.
     * @return true if the given route is found in the given ArrayList; false
     *         otherwise.
     */
    private static boolean routeExists(ArrayList<Route> routes, Route route) {
        boolean alreadyExists = false;
        for (Route currRoute : routes) {
            if (currRoute.equals(route)) {
                alreadyExists = true;
            }
        }
        return alreadyExists;
    }
}
