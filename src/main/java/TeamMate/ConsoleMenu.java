package TeamMate;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * ConsoleMenu for TeamMate application.
 * Uses LoggerConfig, CSVHandler, TeamBuilder and other classes you already implemented.
 *
 * Usage: Run this class (it is the interactive UI).
 */
public class ConsoleMenu {
    private static final Logger LOGGER = Logger.getLogger(ConsoleMenu.class.getName());
    private static final String DEFAULT_CSV = "input_participants.csv";
    private static final String OUTPUT_CSV = "formed_teams.csv";

    public static void main(String[] args) {
        // Ensure logging is configured
        LoggerConfig.setup();

        Scanner sc = new Scanner(System.in);
        String inputPath = DEFAULT_CSV;
        List<Team> lastTeams = null;

        while (true) {
            System.out.println("\n=== TeamMate Menu ===");
            System.out.println("1. Load participants from CSV (current: " + inputPath + ")");
            System.out.println("2. View loaded participants summary");
            System.out.println("3. Build teams (choose N)");
            System.out.println("4. Save last built teams to CSV (" + OUTPUT_CSV + ")");
            System.out.println("5. Show last built teams");
            System.out.println("6. Change CSV path");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            String opt = sc.nextLine().trim();

            try {
                switch (opt) {
                    case "1": {
                        System.out.println("Loading from: " + Paths.get(inputPath).toAbsolutePath());
                        List<Participant> participants = CSVHandler.loadParticipants(inputPath);
                        System.out.println("Loaded participants: " + participants.size());
                        LOGGER.info("Loaded participants: " + participants.size());
                        break;
                    }
                    case "2": {
                        try {
                            List<Participant> participants = CSVHandler.loadParticipants(inputPath);
                            System.out.println("=== Participants ===");
                            participants.forEach(p -> System.out.printf("%s | %s | %s | %s | skill=%d | score=%d | %s%n",
                                    p.getId(), p.getName(), p.getInterest(), p.getRole(), p.getSkillLevel(), p.getScore(), p.getPersonality()));
                        } catch (IOException e) {
                            System.err.println("Cannot load participants: " + e.getMessage());
                            LOGGER.severe("Failed to load participants: " + e.getMessage());
                        }
                        break;
                    }
                    case "3": {
                        System.out.print("Enter team size N (default 5): ");
                        String nline = sc.nextLine().trim();
                        int teamSize = 5;
                        if (!nline.isEmpty()) {
                            try {
                                teamSize = Integer.parseInt(nline);
                                if (teamSize <= 0) {
                                    System.out.println("Team size must be > 0. Using default 5.");
                                    teamSize = 5;
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid number, using default 5.");
                                teamSize = 5;
                            }
                        }
                        List<Participant> participants = CSVHandler.loadParticipants(inputPath);
                        TeamBuilder builder = new TeamBuilder(teamSize, Math.max(1, Runtime.getRuntime().availableProcessors()));
                        lastTeams = builder.buildTeams(participants);
                        System.out.println("Teams built: " + lastTeams.size());
                        LOGGER.info("Teams built: " + lastTeams.size());
                        break;
                    }
                    case "4": {
                        if (lastTeams == null) {
                            System.out.println("No teams built yet.");
                            break;
                        }
                        try {
                            CSVHandler.saveTeams(OUTPUT_CSV, lastTeams);
                            System.out.println("Saved to: " + OUTPUT_CSV);
                            LOGGER.info("Saved teams to: " + Paths.get(OUTPUT_CSV).toAbsolutePath());
                        } catch (IOException e) {
                            System.err.println("Failed saving teams: " + e.getMessage());
                            LOGGER.severe("Failed to save teams: " + e.getMessage());
                        }
                        break;
                    }
                    case "5": {
                        if (lastTeams == null) {
                            System.out.println("No teams built yet.");
                            break;
                        }
                        System.out.println("\n--- Teams ---");
                        for (Team t : lastTeams) {
                            System.out.println(t.summary());
                            t.getMembers().forEach(m -> System.out.printf("  - %s | %s | %s | skill=%d | score=%d | %s%n",
                                    m.getId(), m.getName(), m.getInterest(), m.getSkillLevel(), m.getScore(), m.getPersonality()));
                        }
                        break;
                    }
                    case "6": {
                        System.out.print("Enter new CSV path (absolute or relative): ");
                        String p = sc.nextLine().trim();
                        if (!p.isEmpty()) inputPath = p;
                        System.out.println("CSV path updated to: " + inputPath);
                        LOGGER.info("CSV path changed to: " + inputPath);
                        break;
                    }
                    case "0": {
                        System.out.println("Exiting. Goodbye.");
                        LOGGER.info("User exited via console menu.");
                        sc.close();
                        return;
                    }
                    default:
                        System.out.println("Invalid option. Choose 0-6.");
                }
            } catch (IOException ioe) {
                System.err.println("I/O error: " + ioe.getMessage());
                LOGGER.severe("I/O error in menu: " + ioe.getMessage());
            } catch (InterruptedException ie) {
                System.err.println("Operation interrupted.");
                LOGGER.severe("InterruptedException in menu.");
                Thread.currentThread().interrupt();
                return;
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                LOGGER.severe("Unexpected error in menu: " + e.getMessage());
            }
        }
    }
}

