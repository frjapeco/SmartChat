package smartchat.domains;

public class Message {

    private String sender;
    private String content;
    
    public Message() {
        
    }
    
    public Message(String username, String content) {
        super();
        this.sender = username;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }   
    
}
