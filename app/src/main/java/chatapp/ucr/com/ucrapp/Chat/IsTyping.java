package chatapp.ucr.com.ucrapp.Chat;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import chatapp.ucr.com.ucrapp.DatabaseClasses.AddToDatabase;
import chatapp.ucr.com.ucrapp.DatabaseClasses.ChatMetaData;
import chatapp.ucr.com.ucrapp.DatabaseClasses.ChatUserData;

public class IsTyping {

    private DatabaseReference root;
    private String userID;
    private String chatID;
    private TextView whoIsTypingEditText;
    private final String IS_TYPING = "Is Typing";
    private String output;

    public IsTyping(DatabaseReference root, TextView whoIsTypingEditText,
                    String userID, String chatID){
        this.root = root;
        this.userID = userID;
        this.chatID = chatID;
        output = IS_TYPING;
        this.whoIsTypingEditText = whoIsTypingEditText;
        whoIsTypingEditText.setText(output);
    }

    //Update the database if user is typing
    public void setTyping(final Boolean typing){
        root.child("ChatUserData").child(chatID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ChatUserDataArrayList chatUserDataArrayList
                        = dataSnapshot.getValue(ChatUserDataArrayList.class);

                ArrayList<ChatUserData> userDataList = chatUserDataArrayList.getChatMembers();

                for(int i = 0; userDataList.size() > i; i++){
                    if (userDataList.get(i).getUserID().equals(userID)){
                        if(userDataList.get(i).getIsTyping() != typing)
                            userDataList.get(i).setIsTyping(typing);
                    }
                }

                chatUserDataArrayList.setChatMembers(userDataList);

                root.child("ChatUserData").child(chatID).setValue(chatUserDataArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Create necessary listeners to track and update whether user is typing.
    public void addListener(){
        whoIsTypingEditText.setText(IS_TYPING);

        root.child("ChatUserData").child(chatID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<ChatUserData> userDataList
                        = dataSnapshot.getValue(ChatUserDataArrayList.class).getChatMembers();

                output = IS_TYPING;

                for(int i = 0; userDataList.size() > i; i++){
                    if (userDataList.get(i).getIsTyping()){
                        output = userDataList.get(i).getUsername() + ", " + output;
                    }
                }

                whoIsTypingEditText.setText(output);

                if(whoIsTypingEditText.getText().toString().equals(IS_TYPING)){
                    whoIsTypingEditText.setVisibility(View.INVISIBLE);
                }else{
                    whoIsTypingEditText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
