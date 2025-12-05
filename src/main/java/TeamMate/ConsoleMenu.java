package TeamMate;

import java.util.List;
import java.util.Scanner;

/**
 * ConsoleMenu
 * Handles user interaction for the TeamMate CLI application.
 */
public class ConsoleMenu {

    private static final String INPUT_FILE = "input_participants.csv";
    private static final String OUTPUT_FILE = "formed_teams.csv";
    private static final int TEAM_SIZE = 5;

    private final Scanner scanner;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
    }

    /**
     * Start the CLI menu loop.
     */
    public void start() {

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readChoice();

            switch (choice) {
                case 1 -> generateTeams();
                case 2 -> displayInfo();
                case 3 -> {
                    System.out.println("Exiting program. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Display menu options.
     */
    private void printMenu() {
        System.out.println("\n========== TeamMate Menu ==========");
        System.out.println("1. Generate Teams");
        System.out.println("2. View System Information");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Read user menu choice safely.
     */
    private int readChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Generate teams and write output CSV.
     */
    private void generateTeams() {

        System.out.println("\nLoading participants from CSV...");

        List<Participant> participants =
                CSVHandler.readParticipants(INPUT_FILE);

        if (participants.isEmpty()) {
            System.out.println("ERROR: No participants loaded.");
            return;
        }

        System.out.println("Participants loaded: " + participants.size());

        TeamBuilder teamBuilder = new TeamBuilder(TEAM_SIZE);
        List<Team> teams = teamBuilder.buildTeams(participants);

        CSVHandler.writeTeams(OUTPUT_FILE, teams);

        System.out.println("Teams generated successfully!");
        System.out.println("Output file: " + OUTPUT_FILE);
    }

    /**
     * Display basic system information.
     */
    private void displayInfo() {
        System.out.println("\n========== System Information ==========");
        System.out.println("System Name : TeamMate");
        System.out.println("Purpose     : University Gaming Club Team Formation");
        System.out.println("Team Size   : " + TEAM_SIZE);
        System.out.println("Input File  : " + INPUT_FILE);
        System.out.println("Output File : " + OUTPUT_FILE);
        System.out.println("=======================================");
    }
}

