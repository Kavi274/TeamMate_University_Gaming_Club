package TeamMate;

/**
 * PersonalityClassifier
 * Classifies participants into personality types
 * based on the calculated personality score.
 */
public class PersonalityClassifier {

    /**
     * Classify personality based on score.
     *
     * @param personalityScore score scaled to 0–100
     * @return PersonalityType
     */
    public static PersonalityType classify(int personalityScore) {

        if (personalityScore >= 90) {
            return PersonalityType.LEADER;
        }
        else if (personalityScore >= 70) {
            return PersonalityType.BALANCED;
        }
        else {
            return PersonalityType.THINKER;
        }
    }
}


