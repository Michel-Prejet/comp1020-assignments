
/**
 * ElectionSimApp.java
 * 
 * COMP 1020 Section A01
 * INSTRUCTOR Simon Wermie
 * ASSIGNMENT 2, N/A
 * 
 * @author Simon Wermie
 * @version N/A
 * 
 *          PURPOSE: Provides the main interface for the election simulator, 
 *          where users can read riding and candidate information from CSV
 *          files, write an election summary to a TXT file, get the results
 *          for a specific electoral system, get the riding with highest/lowest
 *          turnout, and get a list of all candidates who live outside their
 *          province of riding.
 * 
 *          NOTE: this interface was fully created by Simon Wermie, my
 *          instructor, as a template for this assignment. I do not take credit
 *          for any of the work contained in this specific file.
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ElectionSimApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] PARTY_LIST = {
            "Liberal/Libéral",
            "NDP-New Democratic Party/NPD-Nouveau Parti démocratique",
            "Conservative/Conservateur",
            "People's Party - PPC/Parti populaire - PPC",
            "Green Party/Parti Vert",
            "Independent/Indépendant(e)",
            "Bloc Québécois/Bloc Québécois",
            "Free Party Canada/Parti Libre Canada",
            "Marxist-Leninist/Marxiste-Léniniste",
            "Maverick Party/Maverick Party",
            "Parti Rhinocéros Party/Parti Rhinocéros Party",
            "Communist/Communiste",
            "Christian Heritage Party/Parti de l'Héritage Chrétien",
            "Libertarian/Libertarien",
            "Pour l'Indépendance du Québec/Pour l'Indépendance du Québec",
            "Animal Protection Party/Parti Protection Animaux",
            "Marijuana Party/Parti Marijuana",
            "VCP/CAC",
            "No Affiliation/Aucune appartenance",
            "Centrist/Centriste",
            "National Citizens Alliance/Alliance Nationale Citoyens",
            "Parti Patriote/Parti Patriote",
            "CFF - Canada's Fourth Front/QFC - Quatrième front du Canada",
            "Nationalist/Nationaliste"
    };
    private static final int TOTAL_SEATS = 338;
    private static final int BAR_GRAPH_SCALE = 1;
    private static final String RIDINGS_FILE = "ridings.csv";
    private static final String CANDIDATES_FILE = "candidates.csv";
    private static final String FILE_TO_WRITE = "test_output.txt";

    private static ElectionSimulator sim = new ElectionSimulator();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    loadRidingsCandidates();
                    break;
                case "2":
                    writeResults();
                    break;
                case "3":
                    getSpecificMethodResult();
                    break;
                case "4":
                    getTurnoutInfo();
                    break;
                case "5":
                    printOutOfProvinceCandidates();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nElection Simulation CLI");
        System.out.println("1. Load Ridings & Candidates");
        System.out.println("2. Write results of all methods to file");
        System.out.println("3. Get Specific method result (NPV, FPTP, PR, MMP, W-FPTP)");
        System.out.println("4. Get riding with highest/lowest turnout");
        System.out.println("5. Get candidates who live outside their province of riding");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    private static void loadRidingsCandidates() {
        try {
            IOHelper.loadRidingsFromCSV(sim, RIDINGS_FILE);
            System.out.println("Ridings loaded from " + RIDINGS_FILE + ".");

            IOHelper.loadCandidatesFromCSV(sim, CANDIDATES_FILE, PARTY_LIST);
            System.out.println("Candidates loaded from " + CANDIDATES_FILE + ".");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            sim = new ElectionSimulator();
        }
    }

    private static void writeResults() {
        try {
            IOHelper.writeResultsToFile(sim, FILE_TO_WRITE, TOTAL_SEATS);
            System.out.println("Results written to " + FILE_TO_WRITE + ".");
        } catch (IOException e) {
            System.out.println("Error writing results: " + e.getMessage());
        }
    }

    private static void getSpecificMethodResult() {
        System.out.print("Enter method (NPV, FPTP, PR, MMP, W-FPTP): ");
        String method = scanner.nextLine().toUpperCase();

        switch (method) {
            case "NPV":
                System.out.println("Winner (NPV): " + sim.getNPVWinner());
                break;
            case "FPTP":
                System.out.println("FPTP Seat Distribution:");
                printBarGraph(sim.getFPTPSeatDistribution());
                break;
            case "PR":
                System.out.println("PR Seat Distribution:");
                printBarGraph(sim.getPRSeatDistribution(TOTAL_SEATS));
                break;
            case "MMP":
                System.out.println("MMP Seat Distribution:");
                printBarGraph(sim.getMMPSeatDistribution());
                break;
            case "W-FPTP":
                System.out.println("Weighted FPTP Seat Distribution:");
                printBarGraph(sim.getWeightedFPTPSeatDistribution(TOTAL_SEATS));
                break;
            default:
                System.out.println("Unknown method.");
        }
    }

    private static void getTurnoutInfo() {
        System.out.println(IOHelper.getHighestTurnoutRiding(sim));
        System.out.println(IOHelper.getLowestTurnoutRiding(sim));
    }

    private static void printOutOfProvinceCandidates() {
        System.out.println("Candidates Outside Province:\n" + IOHelper.getCandidatesOutsideTheirRiding(sim));
    }

    // Print a bar graph of PartyResult counts and show winner + runner-up
    private static void printBarGraph(ArrayList<PartyResult> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("No results to display.");
            return;
        }

        for (PartyResult pr : results) {
            int barLength = Math.max(1, (int) (pr.getCount() / BAR_GRAPH_SCALE)); // ensure at least 1 #
            String bar = "#".repeat(barLength);
            System.out.printf("%-60s | %s (%.2f)\n", pr.getParty(), bar, pr.getCount());
        }

        String[] topTwo = PartyResult.getTopTwoPartyNames(results);
        System.out.println();
        System.out.println("Winner    : " + topTwo[0]);
        System.out.println("Runner-up : " + topTwo[1]);
    }

}
