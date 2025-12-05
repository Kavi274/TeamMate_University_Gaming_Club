package TeamMate;

public class PersonalityClassifier {

    /**
     * Classify score into personality type based on the brief:
     * Leader: 90-100
     * Balanced: 70-89
     * Thinker: 50-69
     * Anything else -> UNKNOWN
     */
    public static PersonalityType classify(int score) {
        if (score >= 90 && score <= 100) return PersonalityType.LEADER;
        if (score >= 70 && score <= 89) return PersonalityType.BALANCED;
        if (score >= 50 && score <= 69) return PersonalityType.THINKER;
        return PersonalityType.UNKNOWN;
    }
}

