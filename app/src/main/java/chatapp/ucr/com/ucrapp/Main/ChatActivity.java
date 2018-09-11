package chatapp.ucr.com.ucrapp.Main;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.util.HashMap;
import java.util.Map;

import chatapp.ucr.com.ucrapp.Chat.IsTyping;
import chatapp.ucr.com.ucrapp.DatabaseClasses.AddToDatabase;
import chatapp.ucr.com.ucrapp.Message.Message;
import chatapp.ucr.com.ucrapp.R;
public class ChatActivity extends AppCompatActivity {
    private String userID;
    private ChatAdapter adapter;
    private AddToDatabase addToDatabase = new AddToDatabase();
    private String chatID;
    private String username = "ERROR";
    private String download_url;
    private ChatAdapterDTB helper;
    ListView mListView;
    private IsTyping isTyping;
    private Boolean wasTyping = false; //Used to check last instance of isTyping;
    private static final int PICK_IMAGE_REQUEST = 1 ; // if we set it to 234
    private static final int CAMRA_REQUEST_CODE = 2;
    private TextView isTypingTextView;
    FloatingActionButton openlocal, camera;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        isTypingTextView = (TextView) findViewById(R.id.isTypingTextView);

        getUserName();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mListView = findViewById(R.id.chatListView);
        chatID = getIntent().getStringExtra("chatapp.ucr.com.ucrapp.CHAT_ID");
        helper = new ChatAdapterDTB(this, FirebaseDatabase.getInstance().getReference().getRoot(), userID, chatID);
        adapter = helper.getAdapter();
        mListView.setAdapter(adapter);
        setupSendKey();
        setuplocalButton();
        setupCameraButton();
        setupIsTyping();
    }

    @Override
    public void onResume() {
        super.onResume();
        //helper.getAdapter(); // reload the items from database
//        helper.updateAdapter();
//        adapter = helper.getAdapter();
//        mListView.setAdapter(adapter)

    }

    private void setupIsTyping() {

        isTyping = new IsTyping(
                FirebaseDatabase.getInstance().getReference().getRoot(),
                isTypingTextView, userID, chatID);
        isTyping.addListener();

        final TextView messageTextView = (TextView) findViewById(R.id.messageEditText);

        messageTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ((!wasTyping) && (!messageTextView.getText().toString().isEmpty())) {

                    isTyping.setTyping(true);
                    wasTyping = true;
                } else {
                    if(wasTyping && messageTextView.getText().toString().isEmpty()) {

                        isTyping.setTyping(false);
                        wasTyping = false;
                    }
                }
            }
        });
    }

    private void setupCameraButton() {
        camera = findViewById( R.id.camera_button);
        camera.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( ChatActivity.this, "we prepaer to take picture", Toast.LENGTH_SHORT ).show();
                captureImageWithCamera();
            }
            private void captureImageWithCamera() {
                Intent cameraIntent = new Intent( MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                showLocatfile();
                startActivityForResult(cameraIntent, CAMRA_REQUEST_CODE);
                //showLocatfile();
            }
        } );
    }
    private void setuplocalButton() {
        openlocal = findViewById(R.id.image_button);
        openlocal.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocatfile();
            }
        } );
    }

    private void showLocatfile() {
        Intent gallryIntent = new Intent( );
        gallryIntent.setType( "image/*" );
        gallryIntent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( Intent.createChooser( gallryIntent,"SELECT IMAGE" ), PICK_IMAGE_REQUEST );
    }
    //This sets up the send key on the keyboard to send a message on the activity_chat.xml
    private void setupSendKey() {
        final EditText editText = (EditText) findViewById(R.id.messageEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    addToDatabase.addMessage(new Message(editText.getText().toString(), username, userID), chatID);
                    editText.setText("");
                    handled = true;
                }
                return handled;
            }
        });
    }
    //This gets the username for the user currently signed in.
    private void getUserName() {
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().getRoot().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(userID) != null) {
                            username = dataSnapshot.child(userID).child("userName")
                                    .getValue().toString();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            //Toast.makeText( ChatActivity.this, "uploading ....1 ", Toast.LENGTH_SHORT ).show();
            final Uri imageUri = data.getData();
            final StorageReference filepath = mStorageRef.child("message_images").child( imageUri.getLastPathSegment() + ".jpg");

            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText( ChatActivity.this, "uploading .... working", Toast.LENGTH_SHORT ).show();
                        filepath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String image_downurl = uri.toString();
                                Map messageMap = new HashMap();
                                Toast.makeText( ChatActivity.this, "uploading .... working", Toast.LENGTH_SHORT ).show();
                                Message newMessage = new Message();
                                newMessage.setUrl(image_downurl);
                                newMessage.setUsername(username);
                                addToDatabase.addMessage(newMessage, chatID);
                                download_url = "";
                            }
                        } );

                    }
                }
            });

        }
    }
}