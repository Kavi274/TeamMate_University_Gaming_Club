package TeamMate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonalityClassifierTest {

    @Test
    void testLeaderClassification() {
        assertEquals(PersonalityType.LEADER, PersonalityClassifier.classify(90));
        assertEquals(PersonalityType.LEADER, PersonalityClassifier.classify(100));
    }

    @Test
    void testBalancedClassification() {
        assertEquals(PersonalityType.BALANCED, PersonalityClassifier.classify(70));
        assertEquals(PersonalityType.BALANCED, PersonalityClassifier.classify(89));
    }

    @Test
    void testThinkerClassification() {
        assertEquals(PersonalityType.THINKER, PersonalityClassifier.classify(50));
        assertEquals(PersonalityType.THINKER, PersonalityClassifier.classify(69));
    }

    @Test
    void testUnknownClassification() {
        assertEquals(PersonalityType.UNKNOWN, PersonalityClassifier.classify(49));
        assertEquals(PersonalityType.UNKNOWN, PersonalityClassifier.classify(-20));
        assertEquals(PersonalityType.UNKNOWN, PersonalityClassifier.classify(105));
    }
}
