package chatapp.ucr.com.ucrapp.Message;

import java.text.SimpleDateFormat;

public class Message {
    private String message;
    private String username;
    private String userID;
    private String url;
    private long timestamp;
    private String date;

    public Message() {
        url = "";
        this.message = "";
        username = "";
        timestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy\n hh:mm:ss a");
        date = sdf.format(timestamp);
        userID = "";

    }

//    public Message(String username, String imageUrl) {
//        this.message = "";
//        this.username = username;
//        this.timestamp = System.currentTimeMillis();
//        this.url = imageUrl;
//    }

    public Message(String message, String username, String userID) {
        this.message = message;
        this.username = username;
        this.userID = userID;
        this.url = "";
        timestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy\n hh:mm:ss a");
        date = sdf.format(timestamp);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
