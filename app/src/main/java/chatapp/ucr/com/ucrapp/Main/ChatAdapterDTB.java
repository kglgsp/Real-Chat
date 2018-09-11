package chatapp.ucr.com.ucrapp.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import chatapp.ucr.com.ucrapp.DatabaseClasses.AddToDatabase;
import chatapp.ucr.com.ucrapp.Message.Message;

public class ChatAdapterDTB {

    private DatabaseReference root;
    private ArrayList<Message> messageList = new ArrayList<>();
    private String chatID;
    private String userID;
    private Context c;
    private ChatAdapter adapter;

    public ChatAdapterDTB(Context c, DatabaseReference ref, String userID, String chatID) {
        root = ref;
        this.chatID = chatID;
        this.userID = userID;
        this.c = c;
        adapter = new ChatAdapter(c, retrieve(), chatID);
    }

    private void createChatInfoAdapter(Context c, ArrayList<Message> messageList) {
    }

    public ChatAdapter getAdapter() {
        return adapter;
    }

    public void updateAdapter() {
        this.messageList = retrieve();
    }

    public ArrayList<Message> retrieve() {

        retrieveMessageList();
        return messageList;
    }


    private void retrieveMessageList() {
            root.child("messages").child(chatID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    messageList.clear();
                    fetchdata(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


    private void fetchdata(DataSnapshot dataSnapshot) {

        if(dataSnapshot.exists()){
            for(DataSnapshot iterSnapshot : dataSnapshot.getChildren()){
                    messageList.add(iterSnapshot.getValue(Message.class));

            }
            adapter.notifyDataSetChanged();
        }
    }
}
