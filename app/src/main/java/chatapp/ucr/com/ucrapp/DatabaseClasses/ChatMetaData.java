package chatapp.ucr.com.ucrapp.DatabaseClasses;

import java.util.ArrayList;

public class ChatMetaData {
    private String lastMessage;
    private String timeStamp;
    private String username;
    private String title;
    private String details;
    private String chatID;

    public ChatMetaData(){
        lastMessage = "";
        timeStamp = "";
        username = "";
        title = "";
        details = "";
        chatID = "";
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setLastMessage(String message){ lastMessage = message; }

    public String getLastMessage(){ return lastMessage; }

    public void setTimeStamp(String time){ timeStamp = time; }

    public String getTimeStamp(){ return timeStamp; }

    public void setUsername(String user){ username = user; }

    public String getUsername(){ return username; }

    public void setTitle(String chatTitle){ title = chatTitle; }

    public String getTitle(){ return title; }

}
