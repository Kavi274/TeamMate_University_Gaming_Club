package TeamMate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVHandlerTest {

    @Test
    void testLoadParticipants(@TempDir Path tempDir) throws IOException {
        // Create sample CSV
        Path csv = tempDir.resolve("participants.csv");
        String data = "id,name,interest,role,skillLevel,score\n" +
                "P1,Asha,Valorant,Attacker,80,92\n" +
                "P2,Kaveen,FIFA,Striker,70,75\n";
        Files.writeString(csv, data);

        List<Participant> participants = CSVHandler.loadParticipants(csv.toString());
        assertEquals(2, participants.size());
        assertEquals("P1", participants.get(0).getId());
        assertEquals(92, participants.get(0).getScore());
    }

    @Test
    void testSaveTeams(@TempDir Path tempDir) throws IOException {
        // Create a dummy team
        Team team = new Team("T1", 5);
        team.addMember(new Participant("P1", "Asha", "Valorant", "Attacker", 80, 92));

        Path out = tempDir.resolve("teams.csv");
        CSVHandler.saveTeams(out.toString(), List.of(team));

        assertTrue(Files.exists(out));
        String result = Files.readString(out);
        assertTrue(result.contains("teamId,memberId,memberName"));
        assertTrue(result.contains("T1"));
        assertTrue(result.contains("Asha"));
    }
}
