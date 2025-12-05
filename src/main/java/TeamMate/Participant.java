package TeamMate;

/**
 * Participant model class
 * Represents a single participant in the University Gaming Club
 * Data is loaded from participants_sample.csv
 */
public class Participant {

    // Basic participant details
    private final String participantId;
    private final String name;
    private final String preferredGame;
    private final String preferredRole;
    private final int skillLevel;

    // Personality survey responses (1–5)
    private final int q1;
    private final int q2;
    private final int q3;
    private final int q4;
    private final int q5;

    // Derived attributes
    private final int personalityScore;          // Scaled to 0–100
    private final PersonalityType personalityType;

    /**
     * Constructor used when loading participants from CSV
     */
    public Participant(
            String participantId,
            String name,
            String preferredGame,
            String preferredRole,
            int skillLevel,
            int q1, int q2, int q3, int q4, int q5
    ) {
        this.participantId = participantId;
        this.name = name;
        this.preferredGame = preferredGame;
        this.preferredRole = preferredRole;
        this.skillLevel = clamp(skillLevel, 0, 100);

        // Clamp survey responses to valid range
        this.q1 = clamp(q1, 1, 5);
        this.q2 = clamp(q2, 1, 5);
        this.q3 = clamp(q3, 1, 5);
        this.q4 = clamp(q4, 1, 5);
        this.q5 = clamp(q5, 1, 5);

        // Personality score calculation (as per coursework guideline)
        int total = this.q1 + this.q2 + this.q3 + this.q4 + this.q5;
        this.personalityScore = total * 4; // Scale to 0–100

        // Personality classification handled by separate class
        this.personalityType = PersonalityClassifier.classify(this.personalityScore);
    }

    // --------------------
    // Utility
    // --------------------
    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    // --------------------
    // Getters
    // --------------------
    public String getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }

    public String getPreferredGame() {
        return preferredGame;
    }

    public String getPreferredRole() {
        return preferredRole;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getPersonalityScore() {
        return personalityScore;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }
}
