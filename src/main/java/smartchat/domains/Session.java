package smartchat.domains;

public class Session {
    private String id;
    private String username;
    
    public Session(String id, String username) {
        this.id = id;
        this.username = username;
    }
    
    public String getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
}
