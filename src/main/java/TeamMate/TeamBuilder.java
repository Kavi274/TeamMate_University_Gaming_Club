package TeamMate;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TeamBuilder {
    private static final Logger LOGGER = Logger.getLogger(TeamBuilder.class.getName());

    private final int teamSize;
    private final ExecutorService executor;

    public TeamBuilder(int teamSize, int threads) {
        if (teamSize <= 0) throw new IllegalArgumentException("teamSize must be > 0");
        this.teamSize = teamSize;
        this.executor = Executors.newFixedThreadPool(Math.max(1, threads));
        LOGGER.info("TeamBuilder created with teamSize=" + teamSize + " threads=" + threads);
    }

    public List<Team> buildTeams(List<Participant> participants) throws InterruptedException {
        LOGGER.info("Starting team building for " + participants.size() + " participants.");
        if (participants == null) return Collections.emptyList();
        List<Participant> pool = new ArrayList<>(participants);
        Collections.shuffle(pool, new Random(System.nanoTime()));

        int numTeams = (int) Math.ceil((double) pool.size() / teamSize);
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) teams.add(new Team("T" + (i+1), teamSize));

        for (Participant p : pool) {
            Team best = null;
            double bestScore = Double.NEGATIVE_INFINITY;

            List<Callable<Map.Entry<Team, Double>>> tasks = new ArrayList<>();
            for (Team t : teams) {
                tasks.add(() -> Map.entry(t, scoreIfAdded(t, p)));
            }

            List<Future<Map.Entry<Team, Double>>> futures;
            try {
                futures = executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Team scoring interrupted", e);
                Thread.currentThread().interrupt();
                throw e;
            }

            for (Future<Map.Entry<Team, Double>> f : futures) {
                try {
                    Map.Entry<Team, Double> entry = f.get();
                    Team candidate = entry.getKey();
                    double sc = entry.getValue();
                    if (!candidate.isFull()) {
                        if (sc > bestScore) { bestScore = sc; best = candidate; }
                    }
                } catch (ExecutionException ee) {
                    LOGGER.log(Level.WARNING, "Error during scoring task: " + ee.getMessage(), ee);
                }
            }

            if (best == null) {
                Team overflow = new Team("Overflow", teamSize);
                overflow.addMember(p);
                teams.add(overflow);
                LOGGER.warning("All teams full - created overflow team and added: " + p.getId());
            } else {
                best.addMember(p);
                LOGGER.fine("Assigned participant " + p.getId() + " to team " + best.getId() + " score=" + bestScore);
            }
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        LOGGER.info("Team building complete. Total teams: " + teams.size());
        return teams;
    }

    // scoring unchanged, but log fine-grained info if needed
    private double scoreIfAdded(Team t, Participant p) {
        Set<String> interests = t.getMembers().stream().map(Participant::getInterest).collect(Collectors.toSet());
        Set<String> roles = t.getMembers().stream().map(Participant::getRole).collect(Collectors.toSet());
        Set<PersonalityType> ptypes = t.getMembers().stream().map(Participant::getPersonality).collect(Collectors.toSet());

        double score = 0.0;
        if (!interests.contains(p.getInterest())) score += 3.0;
        if (!roles.contains(p.getRole())) score += 2.0;
        if (!ptypes.contains(p.getPersonality())) score += 2.0;
        score -= interests.size() * 0.1;
        double currentAvg = t.getMembers().stream().mapToInt(Participant::getSkillLevel).average().orElse(p.getSkillLevel());
        double newAvg = (currentAvg * t.size() + p.getSkillLevel()) / Math.max(1, t.size()+1);
        score -= Math.abs(newAvg - 50) / 100.0;
        score += (teamSize - t.size()) * 0.01;
        return score;
    }
}
