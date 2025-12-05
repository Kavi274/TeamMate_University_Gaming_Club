package TeamMate;

import java.util.Objects;

public class Participant {
    private final String id;
    private final String name;
    private final String interest;     // e.g., Valorant, Dota
    private final String role;         // e.g., Defender, Strategist
    private final int skillLevel;      // 1-100 or 0-100
    private final int score;           // sum of 5 personality Qs (0-100 expected)
    private final PersonalityType personality;

    public Participant(String id, String name, String interest, String role, int skillLevel, int score) {
        this.id = id;
        this.name = name;
        this.interest = interest;
        this.role = role;
        this.skillLevel = clamp(skillLevel, 0, 100);
        this.score = clamp(score, 0, 100);
        this.personality = PersonalityClassifier.classify(this.score);
    }

    private static int clamp(int v, int lo, int hi) {
        if (v < lo) return lo;
        if (v > hi) return hi;
        return v;
    }

    // getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getInterest() { return interest; }
    public String getRole() { return role; }
    public int getSkillLevel() { return skillLevel; }
    public int getScore() { return score; }
    public PersonalityType getPersonality() { return personality; }

    @Override
    public String toString() {
        return String.format("%s(%s) [%s/%s/%d/%s]", name, id, interest, role, skillLevel, personality);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Participant)) return false;
        Participant p = (Participant) o;
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

