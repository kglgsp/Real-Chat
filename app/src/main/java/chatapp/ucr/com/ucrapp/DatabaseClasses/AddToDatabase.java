package chatapp.ucr.com.ucrapp.DatabaseClasses;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import chatapp.ucr.com.ucrapp.Chat.ChatUserDataArrayList;
import chatapp.ucr.com.ucrapp.Message.Message;

public class AddToDatabase {


    private DatabaseReference myRef;
    private final String TAG = "AddToDatabase";


    public AddToDatabase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void addUser(String userID, UserInformation userInfo) {
        myRef.child("users").child(userID).setValue(userInfo);
    }

    public void addMessage(Message newMessage, String chatID) {
        myRef.child("messages").child(chatID).push().setValue(newMessage);
    }

    public void addChatMetaData(ChatMetaData metaData, String chatID){
        myRef.child("chats").child(chatID).setValue(metaData);
    }

    public void addFriendsList(FriendsList friendsList, String userID){
        myRef.child("friendLists").child(userID).setValue(friendsList);
    }

    public void addChatList(ChatList chatList, String userID){
        myRef.child("chatLists").child(userID).setValue(chatList);
    }

    public void addUsersList(UsersList usersList){
        myRef.child("usersList").setValue(usersList);
    }

    public String createChat(String userID,
                             final ChatMetaData chatMetaData,
                             final ArrayList<String> chatMemberIDs,
                             final ArrayList<String> chatMemberUsernames) {
        //need to create new unique chatID in database
        //store it and return unique chatID



        final String chatID = myRef.child("messages").push().getKey();
        chatMetaData.setChatID(chatID);

        String title = chatMetaData.getTitle();
        String details = chatMetaData.getDetails();

        ArrayList<ChatUserData> userDataList = new ArrayList<>();

        for (int i = 0; chatMemberIDs.size() > i; i++) {
            ChatUserData chatUserData = new ChatUserData();
            chatUserData.setUserID(chatMemberIDs.get(i));
            chatUserData.setUsername(chatMemberUsernames.get(i));

            userDataList.add(chatUserData);
        }

        ChatUserDataArrayList chatUserDataArrayList = new ChatUserDataArrayList(userDataList);

        //chatUserDataArrayList.setChatMembers(userDataList);

        myRef.child("ChatUserData").child(chatID).setValue(chatUserDataArrayList);

        myRef.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                if (userInformation != null) {
                    chatMetaData.setUsername(userInformation.getUserName());
                } else {
                    chatMetaData.setUsername("Anonymous");
                }

                myRef.child("chats").child(chatID).setValue(chatMetaData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return chatID;
    }

    public void addUserToChat(final String chatID, final String userID){
        myRef.child("chatLists").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("chatList").exists()){
                    ChatList chatList = dataSnapshot.getValue(ChatList.class);
                    chatList.addChat(chatID);
                    addChatList(chatList, userID);
                }
                else{
                    ChatList chatList = new ChatList();
                    chatList.addChat(chatID);
                    addChatList(chatList, userID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "addUserToChat()");
            }
        });


    }

    public void registerNewUser(final String userID, String username, String email){
        UserInformation userInfo = new UserInformation(userID, username, email);
        addUser(userID, userInfo);

        myRef.child("usersList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("usersList").exists()){
                    UsersList usersList = dataSnapshot.getValue(UsersList.class);

                    usersList.addNewUser(userID);
                    addUsersList(usersList);
                }
                else {
                    UsersList x = new UsersList();
                    x.addNewUser(userID);
                    addUsersList(x);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "registerNewUser()");
            }
        });
    }

    public void addUsersToChat(String chatID, ArrayList<String> users){

        for(int i = 0; users.size() > i; i++){
            addUserToChat(chatID, users.get(i));
        }
    }
}
