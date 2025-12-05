package TeamMate;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TeamBuilderTest
 * Unit tests for TeamBuilder class.
 */
public class TeamBuilderTest {

    private static final int TEAM_SIZE = 5;

    /**
     * Create a small set of dummy participants for testing.
     */
    private List<Participant> createTestParticipants() {

        List<Participant> participants = new ArrayList<>();

        participants.add(new Participant("P1", "A", "Valorant", "Attacker", 80, 5, 4, 4, 4, 5));
        participants.add(new Participant("P2", "B", "FIFA", "Strategist", 70, 3, 4, 4, 4, 3));
        participants.add(new Participant("P3", "C", "CSGO", "Defender", 65, 2, 4, 5, 3, 3));
        participants.add(new Participant("P4", "D", "Valorant", "Supporter", 75, 4, 3, 4, 5, 4));
        participants.add(new Participant("P5", "E", "FIFA", "Coordinator", 68, 4, 4, 4, 4, 4));

        participants.add(new Participant("P6", "F", "CSGO", "Attacker", 85, 5, 4, 3, 4, 5));
        participants.add(new Participant("P7", "G", "Valorant", "Strategist", 72, 4, 5, 4, 3, 4));
        participants.add(new Participant("P8", "H", "FIFA", "Defender", 60, 2, 4, 5, 3, 3));
        participants.add(new Participant("P9", "I", "CSGO", "Supporter", 78, 4, 3, 4, 5, 4));
        participants.add(new Participant("P10", "J", "Valorant", "Coordinator", 71, 4, 4, 4, 4, 4));

        return participants;
    }

    @Test
    void testTeamsAreCreated() {

        TeamBuilder builder = new TeamBuilder(TEAM_SIZE);
        List<Team> teams = builder.buildTeams(createTestParticipants());

        assertNotNull(teams);
        assertFalse(teams.isEmpty(), "Teams list should not be empty");
    }

    @Test
    void testTeamSizeIsRespected() {

        TeamBuilder builder = new TeamBuilder(TEAM_SIZE);
        List<Team> teams = builder.buildTeams(createTestParticipants());

        for (Team team : teams) {
            assertTrue(
                    team.size() <= TEAM_SIZE,
                    "Team size should not exceed configured TEAM_SIZE"
            );
        }
    }

    @Test
    void testAllParticipantsAssigned() {

        List<Participant> participants = createTestParticipants();
        TeamBuilder builder = new TeamBuilder(TEAM_SIZE);
        List<Team> teams = builder.buildTeams(participants);

        int totalAssigned = teams.stream()
                .mapToInt(Team::size)
                .sum();

        assertEquals(
                participants.size(),
                totalAssigned,
                "All participants should be assigned to teams"
        );
    }
}
