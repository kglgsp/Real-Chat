package chatapp.ucr.com.ucrapp.Main;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import chatapp.ucr.com.ucrapp.DatabaseClasses.ChatList;
import chatapp.ucr.com.ucrapp.DatabaseClasses.ChatMetaData;
import chatapp.ucr.com.ucrapp.DatabaseClasses.UserInformation;
import chatapp.ucr.com.ucrapp.DatabaseClasses.UsersList;

public class ChatInfoAdapterDTB {
    private DatabaseReference root;
    private ArrayList<UserInformation> userInformationList = new ArrayList<>();
    private String userID;
    private UsersList usersList;
    private int i;
    private ChatInfoAdapter adapter;
    private final String TAG = "ChatInfoAdapterDTB";

    public ChatInfoAdapterDTB(Context c, DatabaseReference ref, String userID) {
        root = ref;
        this.userID = userID;
        createChatInfoAdapter(c, retrieve());
    }

    private void createChatInfoAdapter(Context c, ArrayList<UserInformation> usersInformation) {
        adapter = new ChatInfoAdapter(c, usersInformation);
    }

    public ChatInfoAdapter getAdapter() {
        return adapter;
    }

    public ArrayList<UserInformation> retrieve() {

        retrieveChatInfo();

        return userInformationList;
    }

    public UsersList getUsersList() {
        return usersList;
    }

    private void retrieveChatInfo() {

        root.child("usersList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    usersList = dataSnapshot.getValue(UsersList.class);

                    for (int i = 0; usersList.getUsersList().size() > i; i++) {
                        fetchdata(i);
                    }
                } else {
                    root.child("usersLists").setValue(new UsersList());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "retrieveChatInfo()");
            }
        });

    }

    private void fetchdata(final int i){
        root.child("users").child(usersList.getUsersList().get(i)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(i == 0){
                    userInformationList.clear();
                }

                userInformationList.add(dataSnapshot.getValue(UserInformation.class));

                if ((usersList.getUsersList().size() - 1) == i) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
