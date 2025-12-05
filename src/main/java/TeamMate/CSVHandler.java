package TeamMate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVHandler
 * Handles reading participants from CSV
 * and writing formed teams to CSV.
 */
public class CSVHandler {

    /**
     * Read participants from input_participants.csv
     */
    public static List<Participant> readParticipants(String filePath) {

        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {

                // Skip header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                // Expect exactly 10 columns
                if (data.length != 10) {
                    System.err.println("Skipping invalid row: " + line);
                    continue;
                }

                String participantId = data[0].trim();
                String name = data[1].trim();
                String preferredGame = data[2].trim();
                String preferredRole = data[3].trim();
                int skillLevel = Integer.parseInt(data[4].trim());

                int q1 = Integer.parseInt(data[5].trim());
                int q2 = Integer.parseInt(data[6].trim());
                int q3 = Integer.parseInt(data[7].trim());
                int q4 = Integer.parseInt(data[8].trim());
                int q5 = Integer.parseInt(data[9].trim());

                Participant participant = new Participant(
                        participantId,
                        name,
                        preferredGame,
                        preferredRole,
                        skillLevel,
                        q1, q2, q3, q4, q5
                );

                participants.add(participant);
            }

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in CSV: " + e.getMessage());
        }

        return participants;
    }

    /**
     * Write formed teams to formed_teams.csv
     */
    public static void writeTeams(String filePath, List<Team> teams) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Write header
            writer.write("TeamID,ParticipantID,Name,PreferredGame,Role,PersonalityType,SkillLevel");
            writer.newLine();

            int teamId = 1;

            for (Team team : teams) {
                for (Participant p : team.getMembers()) {

                    writer.write(
                            teamId + "," +
                                    p.getParticipantId() + "," +
                                    p.getName() + "," +
                                    p.getPreferredGame() + "," +
                                    p.getPreferredRole() + "," +
                                    p.getPersonalityType() + "," +
                                    p.getSkillLevel()
                    );
                    writer.newLine();
                }
                teamId++;
            }

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
}
