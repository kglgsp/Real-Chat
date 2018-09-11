package chatapp.ucr.com.ucrapp.DatabaseClasses;

public class ChatUserData{
    private String userID;
    private String username;
    private Boolean isTyping;

    public ChatUserData(){
        userID = "";
        username = "";
        isTyping = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserID(String id){ userID = id; }
    public String getUserID(){ return userID; }
    public void setIsTyping(Boolean bool){ isTyping = bool; }
    public Boolean getIsTyping(){ return isTyping; }
}