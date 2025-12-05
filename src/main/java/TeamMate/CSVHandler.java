package TeamMate;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVHandler {

    private static final Logger LOGGER = Logger.getLogger(CSVHandler.class.getName());

    /**
     * Example CSV expected columns (header):
     * id,name,interest,role,skillLevel,score
     */
    public static List<Participant> loadParticipants(String path) throws IOException {
        Path p = Paths.get(path);
        if (!Files.exists(p)) {
            LOGGER.severe("Input CSV not found: " + p.toAbsolutePath());
            throw new FileNotFoundException("Input CSV not found: " + path);
        }

        List<String> lines = Files.readAllLines(p);
        if (lines.isEmpty()) {
            LOGGER.warning("CSV file is empty: " + path);
            throw new IOException("CSV file is empty: " + path);
        }

        String header = lines.get(0).trim();
        LOGGER.fine("CSV header: " + header);

        List<String> data = lines.stream().skip(1).filter(l -> !l.isBlank()).collect(Collectors.toList());
        List<Participant> participants = new ArrayList<>();

        int lineNum = 1;
        for (String line : data) {
            lineNum++;
            String[] cols = splitCsv(line);
            if (cols.length < 6) {
                String msg = "Malformed CSV line " + lineNum + ": " + line;
                LOGGER.warning(msg);
                throw new IllegalArgumentException(msg);
            }
            String id = cols[0].trim();
            String name = cols[1].trim();
            String interest = cols[2].trim();
            String role = cols[3].trim();
            int skill = parseIntSafe(cols[4], 0);
            int score = parseIntSafe(cols[5], 0);
            Participant pObj = new Participant(id, name, interest, role, skill, score);
            participants.add(pObj);
            LOGGER.fine("Parsed participant: " + pObj);
        }
        LOGGER.info("Total participants parsed: " + participants.size());
        return participants;
    }

    public static void saveTeams(String path, List<Team> teams) throws IOException {
        Path p = Paths.get(path);
        try (BufferedWriter bw = Files.newBufferedWriter(p, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.write("teamId,memberId,memberName,interest,role,skill,score,personality");
            bw.newLine();
            for (Team t : teams) {
                for (Participant m : t.getMembers()) {
                    bw.write(String.join(",",
                            t.getId(),
                            escape(m.getId()),
                            escape(m.getName()),
                            escape(m.getInterest()),
                            escape(m.getRole()),
                            String.valueOf(m.getSkillLevel()),
                            String.valueOf(m.getScore()),
                            m.getPersonality().name()
                    ));
                    bw.newLine();
                }
            }
            LOGGER.info("Teams successfully written to: " + p.toAbsolutePath());
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, "Error writing teams to " + p.toAbsolutePath() + " : " + ioe.getMessage(), ioe);
            throw ioe;
        }
    }

    // helpers unchanged
    private static String[] splitCsv(String line) {
        return line.split(",", -1);
    }

    private static int parseIntSafe(String s, int defaultVal) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return defaultVal; }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace(",", " ");
    }
}
