package TeamMate;

import java.util.*;

/**
 * TeamBuilder
 * Responsible for forming balanced teams based on
 * role diversity, personality mix, game variety, and skill balance.
 */
public class TeamBuilder {

    private final int teamSize;

    public TeamBuilder(int teamSize) {
        this.teamSize = teamSize;
    }

    /**
     * Build teams from a list of participants.
     */
    public List<Team> buildTeams(List<Participant> participants) {

        // Defensive copy
        List<Participant> pool = new ArrayList<>(participants);

        // Shuffle first to ensure fairness
        Collections.shuffle(pool);

        // Sort by skill descending (helps balance)
        pool.sort((a, b) -> Integer.compare(b.getSkillLevel(), a.getSkillLevel()));

        int numberOfTeams = pool.size() / teamSize;
        List<Team> teams = new ArrayList<>();

        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(new Team());
        }

        // Assign participants one by one
        for (Participant p : pool) {

            Team bestTeam = null;

            for (Team team : teams) {
                if (canAddToTeam(team, p)) {
                    bestTeam = team;
                    break;
                }
            }

            // Fallback: add to smallest team if no perfect match
            if (bestTeam == null) {
                bestTeam = teams.stream()
                        .min(Comparator.comparingInt(t -> t.getMembers().size()))
                        .orElseThrow();
            }

            bestTeam.addMember(p);
        }

        return teams;
    }

    /**
     * Check whether a participant can be added to a team
     * while respecting constraints.
     */
    private boolean canAddToTeam(Team team, Participant p) {

        // Team size limit
        if (team.getMembers().size() >= teamSize) {
            return false;
        }

        // Game variety: max 2 per game
        long sameGameCount = team.getMembers().stream()
                .filter(m -> m.getPreferredGame().equalsIgnoreCase(p.getPreferredGame()))
                .count();
        if (sameGameCount >= 2) {
            return false;
        }

        // Personality constraints
        long leaders = team.getMembers().stream()
                .filter(m -> m.getPersonalityType() == PersonalityType.LEADER)
                .count();

        long thinkers = team.getMembers().stream()
                .filter(m -> m.getPersonalityType() == PersonalityType.THINKER)
                .count();

        if (p.getPersonalityType() == PersonalityType.LEADER && leaders >= 1) {
            return false;
        }

        if (p.getPersonalityType() == PersonalityType.THINKER && thinkers >= 2) {
            return false;
        }

        // Role diversity (soft constraint)
        if (team.getMembers().size() >= 3) {
            Set<String> roles = new HashSet<>();
            for (Participant m : team.getMembers()) {
                roles.add(m.getPreferredRole());
            }
            roles.add(p.getPreferredRole());

            if (roles.size() < 3) {
                return false;
            }
        }

        return true;
    }
}

