# COMP 1020 Assignment 3: Navigation System

This project was built as an assignment for COMP 1020: Introductory Computer Science 2 at the University of Manitoba. The assignment emphasizes recursion, sorting algorithms, ArrayList, and OOP (object-oriented programming) as well as file I/O, exception handling, and string manipulation in Java. This includes insertion sort and selection sort implementations, recursive DFS (depth-first search) solutions, and instantiating objects using data read from CSV files. The assignment also involves writing a simple CLI interface for the program as a bonus step.
The program itself is CLI-based and simulates a starship navigation and routing system across a connected network of star systems. The main function of the program is to find all possible routes between two given star systems using a given starship, and to allow the user to analyze a specific route by providing information such as distance, cost, and safety.

---

## Context

The simulation consists of a network of star systems that:

- Belong to a faction,
- Are connected to other systems via hyperspace lanes,
- Have a specific danger level.

Starships are the vehicles used to travel between star systems. They have the following properties:

- Belong to a faction,
- Have a list of enemy factions,
- Consume antimatter units (AMUs),
- Consume a given amount of AMUs per light year,
- Have a specific cost in credits per antimatter unit (cr/AMU).

Note that hyperspace lanes are undirected - they can be travelled both ways in a route.

---

## General Overview

As a user, you can:

- Load starships, hyperspace lanes, and star systems from CSV files.
- Get a list of all starships and their properties.
- Get a list of all star systems (sorted alphabetically by name) and their properties.
- Get a list of all hyperspace lanes (sorted alphabetically by the name of their system of origin) and their properties.
- View and analyze all routes between two given star systems using a given starship.
  -  Get the route with the shortest/longest distance, the lowest cost, or the highest danger level. 
  -  Get a list of all toll-free routes or all safe routes.

---

## Technologies

- Built with Java (JDK 23).
- No external libraries.

---

## How To Run

Navigate to the project directory in the command line, then type the following:

```bash
javac *.java
java NavigationApp
```

---

## File Structure

The program consists of 6 files:
- `NavigationApp.java` - the main interface for the program.
- `Navigation.java` - provides core functionality. Loads starships, hyperspace lanes, and star systems from CSV files, and allows the user to find and analyze all valid routes between systems. 
- `Starship.java` - starship implementation.
- `StarSystem.java` - star system implementation.
- `HyperspaceLane.java` - hyperspace lane implementation.
- `Route.java` - route implementation.

---

## CSV Formatting

Place three CSV files in the project directory with any valid name and a header row. There should be one file containing starship information, one containing hyperspace lane information, and another containing star system information. For example:

`starships.csv`
Name,Faction,AntimatterPerLY,CostPerAntimatterUnit,EnemyFactions
Endeavour,Earth,3.5,5.7,Empire|Rebels
Raider,Empire,2.8,10.6,Earth|Neutral

`hyperspacelanes.csv`
FromSystem,ToSystem,Distance,TollCost
Sol,AlphaCentauri,5.0,1.0
AlphaCentauri,Draconis,7.0,2.0
Sol,Draconis,15.0,0.0

`starsystems.csv`
Name,Faction,DangerLevel
Sol,Earth,2
AlphaCentauri,Neutral,4

---

## Sample Output
```
Welcome to the Starship Navigation System
Enter path to Star Systems file (include .csv):starsystems.csv
Enter path to Hyperspace Lanes file (include .csv):hyperspacelanes.csv
Enter path to Starships file (include .csv):starships.csv

Available Starships:
0: Starship[name=Endeavour, faction=Earth, fuel=3.50 AMU/LY at 5.70 cr/AMU, enemies=[Empire, Rebels]]
1: Starship[name=Raider, faction=Empire, fuel=2.80 AMU/LY at 10.60 cr/AMU, enemies=[Earth, Neutral]]
2: Starship[name=Whisper, faction=None, fuel=4.00 AMU/LY at 15.50 cr/AMU, enemies=[]]
3: Starship[name=Diplomat, faction=Neutral, fuel=3.00 AMU/LY at 8.50 cr/AMU, enemies=[Empire]]
4: Starship[name=Vanguard, faction=Rebels, fuel=3.20 AMU/LY at 3.20 cr/AMU, enemies=[Empire, Earth]]

--- Main Menu ---
1. View star systems (sorted)
2. View hyperspace lanes (sorted)
3. Analyze routes for a given ship
0. Exit
Enter choice: 1
StarSystem[name=AlphaCentauri, faction=Neutral, dangerLevel=4]
StarSystem[name=Draconis, faction=Empire, dangerLevel=8]
StarSystem[name=Sirius, faction=Rebels, dangerLevel=6]
StarSystem[name=Sol, faction=Earth, dangerLevel=2]
StarSystem[name=Vega, faction=Neutral, dangerLevel=3]

--- Main Menu ---
1. View star systems (sorted)
2. View hyperspace lanes (sorted)
3. Analyze routes for a given ship
0. Exit
Enter choice: 2
Lane[from=AlphaCentauri, to=Draconis, 7.00 LY, 2.00 cr toll]
Lane[from=AlphaCentauri, to=Vega, 4.00 LY, 1.00 cr toll]
Lane[from=Draconis, to=Vega, 6.00 LY, 1.50 cr toll]
Lane[from=Sol, to=AlphaCentauri, 5.00 LY, 1.00 cr toll]
Lane[from=Sol, to=Draconis, 15.00 LY, 0.00 cr toll]
Lane[from=Sol, to=Sirius, 10.00 LY, 3.00 cr toll]
Lane[from=Vega, to=Sirius, 8.00 LY, 2.50 cr toll]

--- Main Menu ---
1. View star systems (sorted)
2. View hyperspace lanes (sorted)
3. Analyze routes for a given ship
0. Exit
Enter choice: 3
Enter start system: alphacentauri
Enter destination system: sol

Available Starships:
0: Starship[name=Endeavour, faction=Earth, fuel=3.50 AMU/LY at 5.70 cr/AMU, enemies=[Empire, Rebels]]
1: Starship[name=Raider, faction=Empire, fuel=2.80 AMU/LY at 10.60 cr/AMU, enemies=[Earth, Neutral]]
2: Starship[name=Whisper, faction=None, fuel=4.00 AMU/LY at 15.50 cr/AMU, enemies=[]]
3: Starship[name=Diplomat, faction=Neutral, fuel=3.00 AMU/LY at 8.50 cr/AMU, enemies=[Empire]]
4: Starship[name=Vanguard, faction=Rebels, fuel=3.20 AMU/LY at 3.20 cr/AMU, enemies=[Empire, Earth]]
Select a ship for route analysis: 1

--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
1

Route: Sol
Distance: 5.00 LY | Danger: 2 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 149.40 cr

Route: Draconis -> Sol
Distance: 22.00 LY | Danger: 10 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 654.96 cr

Route: Draconis -> Vega -> Sirius -> Sol
Distance: 31.00 LY | Danger: 19 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 929.08 cr

Route: Vega -> Draconis -> Sol
Distance: 25.00 LY | Danger: 13 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 744.50 cr

Route: Vega -> Sirius -> Sol
Distance: 22.00 LY | Danger: 11 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 659.46 cr
Total routes found: 5

--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
2

Shortest route:
Route: Sol
Distance: 5.00 LY | Danger: 2 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 149.40 cr

--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
3

Longest route:
Route: Draconis -> Vega -> Sirius -> Sol
Distance: 31.00 LY | Danger: 19 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 929.08 cr

--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
4

Least expensive route:
Route: Sol
Distance: 5.00 LY | Danger: 2 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 149.40 cr


--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
5

Most hostile route:
Route: Draconis -> Vega -> Sirius -> Sol
Distance: 31.00 LY | Danger: 19 | Enemy Encountered: YES | Toll Free: NO | Total Cost: 929.08 cr


--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
6
No toll-free routes found.

--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
7
No safe routes found.

--- Route Analysis ---
1. Show all routes
2. Shortest distance
3. Longest distance
4. Least expensive
5. Most hostile
6. Toll-free routes
7. Safe routes (no enemy encounters)
0. Back to main menu
Enter choice:
0

--- Main Menu ---
1. View star systems (sorted)
2. View hyperspace lanes (sorted)
3. Analyze routes for a given ship
0. Exit
Enter choice: 0
Exiting navigation system.
```

---

## Important Note

The instructor for the course, Simon Wermie, provided templates for each class except for `NavigationApp.java`. I do not take credit for the class skeletons of all files other than `NavigationApp.java`; however, all implementation logic and the entirety of `NavigationApp.java` is my own work. 

---

## License

All code is licensed under the MIT license, with the exception of instructor-provided code, as described above.

---

## Author

Michel Préjet
Computer Science Student, University of Manitoba
[prejetm@myumanitoba.ca](mailto:prejetm@myumanitoba.ca)

_Last updated: August 18, 2025_
