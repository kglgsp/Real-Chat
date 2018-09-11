package chatapp.ucr.com.ucrapp.DatabaseClasses;

import java.util.ArrayList;

public class FriendsList {

    private ArrayList<String> friendList;

    public FriendsList(){
        friendList = new ArrayList<String>();
    }

    public void addFriend(String friendUID){
        friendList.add(friendUID);
    }

    public ArrayList<String> getFriendList(){
        return friendList;
    }
}
