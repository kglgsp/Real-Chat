package chatapp.ucr.com.ucrapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import chatapp.ucr.com.ucrapp.Chat.Chat;
import chatapp.ucr.com.ucrapp.DatabaseClasses.AddToDatabase;
import chatapp.ucr.com.ucrapp.DatabaseClasses.ChatList;
import chatapp.ucr.com.ucrapp.Message.Message;


public class ChatTabFragment extends Fragment {
//
//    private DatabaseReference root;
//    private String userID;
//    private Chat chat;
//    private AddToDatabase addData;
//    private ChatList chtList;
//
//    protected FirebaseListAdapter<TextMessage> adapter;
//
//    public ChatTabFragment() {
//            //Required empty constructor
//    }
//
//    @Override
//    public void onAttach(final Context context) {
//        super.onAttach(context);
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View rootView = inflater.inflate(R.layout.chat_tab, container, false);
//        //ListView l;
//        //final ArrayList<String> days = new ArrayList<>();
//
////        displayChatMessage(rootView);
//
//        FloatingActionButton fab;
//        fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final EditText input = (EditText)rootView.findViewById(R.id.text_box);
//
//                // Read the input field and push a new instance
//                // of ChatMessage to the Firebase database
//                final Message msg = new TextMessage(input.getText().toString());
//                final AddToDatabase database = new AddToDatabase();
//
//
//                //Test Not real implementation, Requires create a new chat button and implementation
//                root.child("chatLists").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        ChatList chatListObject = dataSnapshot.getValue(ChatList.class);
//
//                        ArrayList<String> chatList = chatListObject.getChatList();
//
//                        if (!chatList.isEmpty()) {
//
//                            String chatID = chatList.get(0);
//
//                            //needs to be passed the unique chatID for the given chat
//                            database.addTextMessage(msg, chatID);
//
//
//                            displayChatMessage(rootView, chatID);
//
//                            // Clear the input
//                            input.setText("");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
////                //needs to be passed the unique chatID for the given chat
////                database.addTextMessage(msg);
////
////
////                displayChatMessage(rootView);
////
////                // Clear the input
////                input.setText("");
//            }
//        });
//
////        l = (ListView) rootView.findViewById(R.id.list);
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, days);
////        l.setAdapter(adapter);
//
//
//        return rootView;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//    }
////
//    public void displayChatMessage(View view, String chatID) {
//        ListView listOfMessages = (ListView) view.findViewById(R.id.list);
//
//        //Suppose you want to retrieve "chats" in your Firebase DB:
//        Query query = FirebaseDatabase.getInstance().getReference().child("messages").child(chatID);
////The error said the constructor expected FirebaseListOptions - here you create them:
//        FirebaseListOptions<TextMessage> options = new FirebaseListOptions.Builder<TextMessage>()
//                .setQuery(query, TextMessage.class)
//                .setLifecycleOwner(this)
//                .setLayout(android.R.layout.simple_list_item_1)
//                .build();
//        //Finally you pass them to the constructor here:
//
//        adapter = new FirebaseListAdapter<TextMessage>(options) {
//
//            @Override
//            protected void populateView(View v, TextMessage model, int position) {
//                TextView messageText = (TextView)v.findViewById(android.R.id.text1);
////                TextView messageText2 = (TextView)v.findViewById(android.R.id.text2);
//
//                messageText.setText(model.getMessage());
////                messageText2.setText(model.getTitle());
//            }
//
//        };
//        listOfMessages.setAdapter(adapter);
////    }

}
