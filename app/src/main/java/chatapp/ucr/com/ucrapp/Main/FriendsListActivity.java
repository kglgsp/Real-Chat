package chatapp.ucr.com.ucrapp.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import chatapp.ucr.com.ucrapp.DatabaseClasses.AddToDatabase;
import chatapp.ucr.com.ucrapp.DatabaseClasses.ChatMetaData;
import chatapp.ucr.com.ucrapp.DatabaseClasses.FriendsList;
import chatapp.ucr.com.ucrapp.DatabaseClasses.UserInformation;
import chatapp.ucr.com.ucrapp.DatabaseClasses.UsersList;
import chatapp.ucr.com.ucrapp.MainActivity;
import chatapp.ucr.com.ucrapp.R;

public class FriendsListActivity extends AppCompatActivity {

    private String userID;
    private ChatInfoAdapter adapter;
    private AddToDatabase addToDatabase = new AddToDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Button addFriendsButton = findViewById(R.id.addButton);
        ListView mListView = findViewById(R.id.friendsListView);

        final ChatInfoAdapterDTB helper = new ChatInfoAdapterDTB(this, FirebaseDatabase.getInstance().getReference().getRoot(),userID);

        adapter = helper.getAdapter();
        mListView.setAdapter(adapter);

        String title = getIntent().getExtras().getString("TITLE");
        String description = getIntent().getExtras().getString("DESCRIPTION");

        final ChatMetaData chatMetaData = new ChatMetaData();
        chatMetaData.setUsername(userID);
        chatMetaData.setTitle(title);
        chatMetaData.setDetails(description);

        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Boolean> isCheckedList = adapter.getIsCheckedList();

                ArrayList<UserInformation> userInformationList = adapter.getUsersInformation();
                ArrayList<String> chatMemberIDs = new ArrayList<>();
                ArrayList<String> chatMemberUsernames = new ArrayList<>();

                for (int i = 0; i < isCheckedList.size(); i++) {
                    if (isCheckedList.get(i)) {
                        chatMemberIDs.add(userInformationList.get(i).getUserID());
                        chatMemberUsernames.add(userInformationList.get(i).getUserName());
                    }
                }

                String chatID = addToDatabase.createChat(userID, chatMetaData, chatMemberIDs, chatMemberUsernames);

                addToDatabase.addUsersToChat(chatID, chatMemberIDs);

                Intent intent = new Intent(FriendsListActivity.this, ChatActivity.class);
                intent.putExtra("chatapp.ucr.com.ucrapp.CHAT_ID", chatID);
                startActivity(intent);
            }
        });
    }
}
