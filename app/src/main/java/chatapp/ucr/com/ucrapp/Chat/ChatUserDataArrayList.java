package chatapp.ucr.com.ucrapp.Chat;

import java.util.ArrayList;

import chatapp.ucr.com.ucrapp.DatabaseClasses.ChatUserData;

public class ChatUserDataArrayList {

    private ArrayList<ChatUserData> chatMembers;

    public ChatUserDataArrayList(){
        chatMembers = new ArrayList<>();
    }

    public ChatUserDataArrayList(ArrayList<ChatUserData> chatUserData){
        chatMembers = chatUserData;
    }

    public ArrayList<ChatUserData> getChatMembers() {
        return chatMembers;
    }

    public void setChatMembers(ArrayList<ChatUserData> chatMembers) {
        this.chatMembers = chatMembers;
    }
}