package TeamMate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PersonalityClassifierTest
 * Unit tests for PersonalityClassifier.
 */
public class PersonalityClassifierTest {

    @Test
    void testLeaderClassification() {
        PersonalityType type = PersonalityClassifier.classify(92);
        assertEquals(PersonalityType.LEADER, type);
    }

    @Test
    void testBalancedClassification() {
        PersonalityType type = PersonalityClassifier.classify(78);
        assertEquals(PersonalityType.BALANCED, type);
    }

    @Test
    void testThinkerClassification() {
        PersonalityType type = PersonalityClassifier.classify(60);
        assertEquals(PersonalityType.THINKER, type);
    }

    @Test
    void testBoundaryValues() {
        assertEquals(PersonalityType.LEADER,
                PersonalityClassifier.classify(90));

        assertEquals(PersonalityType.BALANCED,
                PersonalityClassifier.classify(70));

        assertEquals(PersonalityType.THINKER,
                PersonalityClassifier.classify(69));
    }
}
