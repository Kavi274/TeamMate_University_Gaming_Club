package TeamMate;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TeamBuilderTest {

    private Participant p(String id, String name, String interest, String role, int skill, int score) {
        return new Participant(id, name, interest, role, skill, score);
    }

    @Test
    void testTeamBuilding() throws InterruptedException {

        List<Participant> list = new ArrayList<>();
        list.add(p("P1","Asha","Valorant","Attacker",80,92));
        list.add(p("P2","Bob","FIFA","Striker",65,70));
        list.add(p("P3","Sara","Dota","Support",54,60));
        list.add(p("P4","Nimal","Valorant","Sniper",72,83));
        list.add(p("P5","Kamal","FIFA","Midfielder",60,69));

        TeamBuilder builder = new TeamBuilder(2, Runtime.getRuntime().availableProcessors());
        List<Team> teams = builder.buildTeams(list);

        // All participants assigned
        int total = teams.stream().mapToInt(Team::size).sum();
        assertEquals(list.size(), total);

        // No participant appears twice
        Set<String> ids = new HashSet<>();
        for (Team t : teams) {
            for (Participant p : t.getMembers()) {
                assertTrue(ids.add(p.getId()), "Duplicate participant found!");
            }
        }

        // At least ceil(n/teamSize) teams
        int expectedMinTeams = (int)Math.ceil(list.size() / 2.0);
        assertTrue(teams.size() >= expectedMinTeams);
    }
}
