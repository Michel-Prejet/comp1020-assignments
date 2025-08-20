# COMP 1020 Assignment 2: Election Simulator

This project was built as an assignment for COMP 1020: Introductory Computer Science 2 at the University of Manitoba. The goal of the assignment is to strengthen skills in OOP (object-oriented programming), file I/O, exception handling, string manipulation, array lists, and data-driven logic in Java. This includes reading and parsing large amounts of information from CSV files, instantiating objects and performing calculations using that information, and finally writing the results to a TXT file.
The program itself is CLI-based and aims to simulate an election under various systems, including national popular vote (NPV), first-past-the-post (FPTP), proportional representation (PR), mixed-member proportional representation (MMP), and weighted first-past-the-post (W-FPTP).

---

## General Overview

As a user, you can:

- Load ridings and candidates from CSV files.
- Get election results for a specific method (NPV, FPTP, PR, MMP, W-FPTP).
- Get the riding with the highest/lowest turnout.
- Get a list of all candidates who live outside the province of their riding.
- Write a full election summary to a TXT file.

---

## Technologies

- Built with Java (JDK 23).
- No external libraries.

---

## How To Run

Navigate to the project directory in the command line, then type the following:

```bash
javac *.java
java ElectionSimApp
```

---

## File Structure

The program consists of 8 files:

- `ElectionSimApp.java` - the main interface (provided by instructor Simon Wermie).
- `ElectionSimulator.java`- simulates an election under various electoral systems.
- `Candidate.java` - candidate implementation.
- `ProvinceTerritory.java` - province/territory implementation.
- `Riding.java` - riding implementation.
- `PartyResult.java` - political party implementation.
- `IOHelper.java` - populates the election simulator with ridings and candidates read from CSV files, and writes the election summary to a TXT file.
- `MalformedDataException.java` - custom exception thrown when data read from a file does not match the expected format (provided by instructor Simon Wermie).

---

## CSV Formatting

Place two CSV files in the project directory, both of which must contain a header. One file should be called `ridings.csv` and should contain riding information, the other should be called `candidates.csv` and should contain candidate information. For example:

`ridings.csv`
```
Province,Electoral District Name,Electoral District Number,Electors
Newfoundland and Labrador/Terre-Neuve-et-Labrador,Avalon,10001,Ken McDonald ** Liberal/Libéral,N.L./ T.-N.-L.,18608
Alberta,Calgary Forest Lawn,48006,73174
```

`candidates.csv`
```
Province,Electoral District Name,Electoral District Number,Candidate,Candidate Residence,Votes Obtained
Nova Scotia/Nouvelle-Écosse,Central Nova/Nova-Centre,12002,Sean Fraser ** Liberal/Libéral,N.S./ N.-É.,18682
Ontario,Nipissing--Timiskaming,35070,Steven Trahan Conservative/Conservateur,Ont.,15104
```

---

## Sample Output
```
Election Results Summary
=========================
Total Votes per Party:
	Liberal/Libéral: 139036.00
	Conservative/Conservateur: 180652.00
	NDP-New Democratic Party/NPD-Nouveau Parti démocratique: 89034.00
	People's Party - PPC/Parti populaire - PPC: 35403.00
	Green Party/Parti Vert: 23567.00
	Bloc Québécois/Bloc Québécois: 6830.00
	Parti Rhinocéros Party/Parti Rhinocéros Party: 418.00
	Marxist-Leninist/Marxiste-Léniniste: 115.00
	Independent/Indépendant(e): 4629.00
	Christian Heritage Party/Parti de l'Héritage Chrétien: 712.00
	Maverick Party/Maverick Party: 2738.00	VCP/CAC: 147.00

Method #1 - NPV Winner: Conservative/Conservateur

Method #2 - First-Past-The-Post (FPTP):
	Liberal/Libéral: 6.00	Conservative/Conservateur: 4.00	NDP-New Democratic Party/NPD-Nouveau Parti démocratique: 2.00
	Winner: Liberal/Libéral
	Runner-up: Conservative/Conservateur

Method #3 - Proportional Representation (PR):
	Liberal/Libéral: 3.00	Conservative/Conservateur: 4.00	NDP-New Democratic Party/NPD-Nouveau Parti démocratique: 2.00	People's Party - PPC/Parti populaire - PPC: 1.00	Green Party/Parti Vert: 1.00
	Winner: Conservative/Conservateur
	Runner-up: Liberal/Libéral

Method #4 - Mixed-Member Proportional (MMP):
	Liberal/Libéral: 6.00	Conservative/Conservateur: 4.00	NDP-New Democratic Party/NPD-Nouveau Parti démocratique: 2.00
	Winner: Liberal/Libéral
	Runner-up: Conservative/Conservateur

Method #5 - Weighted FPTP (W-FPTP):
	Liberal/Libéral: 5.00	Conservative/Conservateur: 5.00	NDP-New Democratic Party/NPD-Nouveau Parti démocratique: 2.00
	Winner: Liberal/Libéral
	Runner-up: Conservative/Conservateur
```

---

## Important Note

The instructor for the course, Simon Wermie, provided the entirety of `ElectionSimApp.java` and `MalformedDataException.java` as well as a template for the other classes and parts of `ProvinceTerritory.java`. I do not take credit for any code contained within `ElectionSimApp.java`, `MalformedDataException.java`, and the specified parts of `ProvinceTerritory.java`, or the class skeletons provided for the other files; however, the remaining implementation logic is my own. 

---

## License

All code is licensed under the MIT license, with the exception of instructor-provided code, as described above.

---

## Author

Michel Préjet
Computer Science Student, University of Manitoba
[prejetm@myumanitoba.ca](mailto:prejetm@myumanitoba.ca)

_Last updated: August 17, 2025_
