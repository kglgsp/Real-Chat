package chatapp.ucr.com.ucrapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import chatapp.ucr.com.ucrapp.DatabaseClasses.UserInformation;
import chatapp.ucr.com.ucrapp.Main.ChatActivity;
import chatapp.ucr.com.ucrapp.Message.Message;

public class CustomizeActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private static final int PICK_IMAGE_REQUEST = 1 ;
    private StorageReference mStorageRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        profileImageView.setClickable(true);
        setuplocalButton();
        setupImageViewListener();


    }

    private void setupImageViewListener() {
        FirebaseDatabase.getInstance().getReference().getRoot().child("users")
                .child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);

                if (!userInformation.getPicUrl().isEmpty()){
                    Picasso.get().load(userInformation.getPicUrl()).resize(154,143).into(profileImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setuplocalButton() {
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocatfile();
            }
        });
    }

    private void showLocatfile() {
        Intent gallryIntent = new Intent();
        gallryIntent.setType("image/*");
        gallryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallryIntent, "SELECT IMAGE"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            //Toast.makeText( ChatActivity.this, "uploading ....1 ", Toast.LENGTH_SHORT ).show();
            final Uri imageUri = data.getData();
            final StorageReference filepath = mStorageRef.child("profile_images").child( imageUri.getLastPathSegment() + ".jpg");

            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText( CustomizeActivity.this, "uploading .... working", Toast.LENGTH_SHORT ).show();
                        filepath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String image_downurl = uri.toString();
                                Toast.makeText( CustomizeActivity.this, "uploading .... working", Toast.LENGTH_SHORT ).show();

                                FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(userID).child("picUrl").setValue(image_downurl);
                            }
                        } );

                    }
                }
            });

        }
    }

}
