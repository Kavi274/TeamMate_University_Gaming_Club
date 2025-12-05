package TeamMate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Team
 * Represents a team of participants.
 */
public class Team {

    private final List<Participant> members;

    public Team() {
        this.members = new ArrayList<>();
    }

    /**
     * Add a participant to the team.
     */
    public void addMember(Participant participant) {
        members.add(participant);
    }

    /**
     * Get unmodifiable list of team members.
     */
    public List<Participant> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Get current team size.
     */
    public int size() {
        return members.size();
    }

    /**
     * Calculate average skill level of the team.
     * Useful for debugging and evaluation.
     */
    public double getAverageSkill() {
        if (members.isEmpty()) {
            return 0;
        }

        int totalSkill = 0;
        for (Participant p : members) {
            totalSkill += p.getSkillLevel();
        }
        return (double) totalSkill / members.size();
    }
}
