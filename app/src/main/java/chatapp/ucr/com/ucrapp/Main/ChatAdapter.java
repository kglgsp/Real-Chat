package chatapp.ucr.com.ucrapp.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chatapp.ucr.com.ucrapp.DatabaseClasses.UserInformation;
import chatapp.ucr.com.ucrapp.Message.Message;
import chatapp.ucr.com.ucrapp.R;

public class ChatAdapter extends BaseAdapter{
    private ArrayList<Message> messageList;
    private LayoutInflater mInflater;
    private String chatID;

    public ChatAdapter(Context c, ArrayList<Message> messageList, String chatID){
        this.messageList = messageList;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chatID = chatID;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        if(messageList.size() > position )
            return messageList.get(position);
        else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.received_messages, null);

        if(messageList.get(position).getUrl().isEmpty()) {
            TextView userNameTextView = v.findViewById(R.id.text_message_name);
            TextView messageBodyTextView = v.findViewById(R.id.text_message_body);
            TextView dateTextView = v.findViewById(R.id.text_message_time);
            final ImageView messageImageView = v.findViewById(R.id.image_message_profile);

            FirebaseDatabase.getInstance().getReference().getRoot().child("users")
                    .child(messageList.get(position).getUserID()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);

                    if (!userInformation.getPicUrl().isEmpty()){
                        Picasso.get().load(userInformation.getPicUrl()).resize(32,32).into(messageImageView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            userNameTextView.setText(messageList.get(position).getUsername());
            messageBodyTextView.setText(messageList.get(position).getMessage());
            dateTextView.setText(messageList.get(position).getDate());
        }
        else {
            v = mInflater.inflate(R.layout.image_received_messages, null);
            TextView userNameTextView = v.findViewById(R.id.text_message_name);
            ImageView imageMessageView = v.findViewById(R.id.image_message_body);
            TextView dateTextView = v.findViewById(R.id.text_message_time);

            userNameTextView.setText(messageList.get(position).getUsername());
            Picasso.get().load(messageList.get(position).getUrl()).resize(300,300).into(imageMessageView);
            dateTextView.setText(messageList.get(position).getDate());
        }
        return v;
    }


}
