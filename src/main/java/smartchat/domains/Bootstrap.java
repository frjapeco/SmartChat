package smartchat.domains;

import java.util.Set;

public class Bootstrap {
    private String me;
    private Set<String> onlineUsers;

    public Bootstrap() {
        super();
    }

    public Bootstrap(String me,Set<String> onlineUsers) {
        super();
        this.me = me;
        this.onlineUsers = onlineUsers;
    }
    
    public String getMe() {
        return me;
    }
    
    public Set<String> getOnlineUsers() {
        return onlineUsers;
    }
}
