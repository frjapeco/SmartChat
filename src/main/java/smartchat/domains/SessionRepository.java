package smartchat.domains;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionRepository {
    private List<Session> sessions = new ArrayList<Session>();
    
    public Session findById(String id) {
        for (Session session : sessions)
            if (session.getId() == id)
                return session;
        return null;
    }
    
    public List<Session> findAllByUsername(String username) {
        List<Session> sessions = new ArrayList<Session>();
        
        for (Session session : this.sessions)
            if (session.getUsername() == username)
                sessions.add(session);
        return sessions;
    }
    
    public void add(Session session) {
        sessions.add(session);
    }
    
    public void remove(Session session) {
        sessions.remove(session);
    }
    
    public List<Session> list() {
        return sessions;
    }

    public Set<String> findAllOnlineUsers() {
        return new HashSet<String>(sessions.stream().map(Session::getUsername).collect(Collectors.toList()));       
    }
    
}
