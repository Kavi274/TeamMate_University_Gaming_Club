package TeamMate;

import java.util.List;

/**
 * Main
 * Entry point for the TeamMate University Gaming Club CLI application.
 *
 * Responsibilities:
 * - Load participant data from CSV
 * - Invoke team formation logic
 * - Write formed teams to output CSV
 *
 * No business logic is implemented here.
 */
public class Main {

    // File names used by the CLI
    private static final String INPUT_FILE = "input_participants.csv";
    private static final String OUTPUT_FILE = "formed_teams.csv";

    // Team configuration
    private static final int TEAM_SIZE = 5;

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println(" TeamMate – University Gaming Club Team Builder ");
        System.out.println("==============================================");

        /* STEP 1: Load participants */
        List<Participant> participants = CSVHandler.readParticipants(INPUT_FILE);

        if (participants.isEmpty()) {
            System.out.println("ERROR: No participants loaded.");
            System.out.println("Please check the input_participants.csv file.");
            return;
        }

        System.out.println("Participants loaded successfully: " + participants.size());

        /* STEP 2: Build teams */
        TeamBuilder teamBuilder = new TeamBuilder(TEAM_SIZE);
        List<Team> teams = teamBuilder.buildTeams(participants);

        if (teams.isEmpty()) {
            System.out.println("ERROR: No teams were formed.");
            return;
        }

        System.out.println("Teams formed successfully: " + teams.size());

        /* STEP 3: Write output CSV */
        CSVHandler.writeTeams(OUTPUT_FILE, teams);

        System.out.println("Output written to file: " + OUTPUT_FILE);
        System.out.println("==============================================");
        System.out.println(" Team formation process completed successfully ");
        System.out.println("==============================================");
    }
}
