package chatapp.ucr.com.ucrapp.DatabaseClasses;

import java.util.ArrayList;

public class UsersList {

    private ArrayList<String> usersList;

    public UsersList(){
        usersList = new ArrayList<String>();
    }

    public void addNewUser(String userID){
        usersList.add(userID);
    }

    public ArrayList<String> getUsersList(){
        return usersList;
    }
}