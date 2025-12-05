package TeamMate;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CSVHandlerTest
 * Unit tests for CSVHandler class.
 */
public class CSVHandlerTest {

    private static final String INPUT_FILE = "input_participants.csv";
    private static final String OUTPUT_FILE = "test_formed_teams.csv";

    @Test
    void testReadParticipants() {

        List<Participant> participants =
                CSVHandler.readParticipants(INPUT_FILE);

        assertNotNull(participants, "Participants list should not be null");
        assertFalse(participants.isEmpty(), "Participants list should not be empty");

        // Check first participant basic fields
        Participant p = participants.get(0);
        assertNotNull(p.getParticipantId());
        assertNotNull(p.getName());
        assertNotNull(p.getPreferredGame());
        assertNotNull(p.getPreferredRole());

        // Skill and personality validation
        assertTrue(p.getSkillLevel() >= 0 && p.getSkillLevel() <= 100);
        assertNotNull(p.getPersonalityType());
    }

    @Test
    void testWriteTeams() {

        // Load participants
        List<Participant> participants =
                CSVHandler.readParticipants(INPUT_FILE);

        TeamBuilder builder = new TeamBuilder(5);
        List<Team> teams = builder.buildTeams(participants);

        // Write output CSV
        CSVHandler.writeTeams(OUTPUT_FILE, teams);

        File outputFile = new File(OUTPUT_FILE);
        assertTrue(outputFile.exists(), "Output CSV file should be created");

        // Cleanup (optional but good practice)
        outputFile.delete();
    }
}
