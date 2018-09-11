package chatapp.ucr.com.ucrapp.DatabaseClasses;

import java.util.ArrayList;

public class ChatList {

    private ArrayList<String> chatList;

    public ChatList(){
        chatList = new ArrayList<String>();
    }

    public void addChat(String chatID){
        chatList.add(chatID);
    }
    
    public ArrayList<String> getChatList(){
        return chatList;
    }

}
