package TeamMate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Team {
    private final String id;
    private final List<Participant> members = new ArrayList<>();
    private final int maxSize;

    public Team(String id, int maxSize) {
        this.id = id;
        this.maxSize = maxSize;
    }

    public synchronized boolean addMember(Participant p) {
        if (members.size() >= maxSize) return false;
        members.add(p);
        return true;
    }

    public int size() {
        return members.size();
    }

    public List<Participant> getMembers() {
        return List.copyOf(members);
    }

    public boolean isFull() {
        return members.size() >= maxSize;
    }

    public String getId() { return id; }

    public String summary() {
        String interests = members.stream().map(Participant::getInterest).distinct().collect(Collectors.joining(","));
        String roles = members.stream().map(Participant::getRole).distinct().collect(Collectors.joining(","));
        String ptypes = members.stream().map(m -> m.getPersonality().name()).distinct().collect(Collectors.joining(","));
        return String.format("Team %s: size=%d interests=[%s] roles=[%s] personalities=[%s]",
                id, members.size(), interests, roles, ptypes);
    }
}

