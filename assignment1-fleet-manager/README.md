# COMP 1020 Assignment 1: Starship Fleet Management System

This project was built as an assignment for COMP 1020: Introductory Computer Science 2 at the University of Manitoba.. The assignment serves as an introduction to OOP (object-oriented programming) in Java, including constructors, access modifiers, encapsulation, getter and setter methods, and cloning. 
It is a CLI-based program which aims to simulate a starship fleet management system including various modules, starships, and expeditions.

---

## General Overview

As a user, you can:

- Create and clone starships.
- Add four different types of modules to each starship.
- Send starships on expeditions with various outcomes.
- Perform maintenance on each starship's modules.
- View the status of each module in the fleet.
- View a log of completed expeditions.

---

## Technologies

- Built with Java (JDK 23).
- No external libraries.

---

## How To Run

Navigate to the project directory in the command line, then type the following:

```bash
javac *.java
java CSSApp
```

---

## File Structure

The program consists of 8 files:

- `CSSApp.java` - the main interface (provided by instructor Simon Wermie).
- `FleetManager.java`- simulates a fleet consisting of several starships and expeditions.
- `Expedition.java` - simulates expeditions carried out by starships.
- `Starship.java` - simulates a starship and its modules.
- `EngineModule.java` - engine module implementation.
- `StorageModule.java` - storage module implementation.
- `LifeSupportModule.java` - life support module implementation.
- `WeaponModule.java` - weapon module implementation.

---

## Important Note

The instructor for the course, Simon Wermie, provided the entirety of `CSSApp.java` as well as a template for the other classes. I do not take credit for any code contained within `CSSApp.java` or the class skeletons provided for the other files; however, the remaining implementation logic is my own. 

---

## License

All code is licensed under the MIT license, with the exception of instructor-provided code, as described above.

---

## Author

Michel Préjet
Computer Science Student, University of Manitoba
[prejetm@myumanitoba.ca](mailto:prejetm@myumanitoba.ca)

_Last updated: July 14, 2025_
