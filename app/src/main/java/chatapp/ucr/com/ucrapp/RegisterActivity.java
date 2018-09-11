package chatapp.ucr.com.ucrapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import chatapp.ucr.com.ucrapp.DatabaseClasses.AddToDatabase;
import chatapp.ucr.com.ucrapp.DatabaseClasses.FriendsList;
import chatapp.ucr.com.ucrapp.DatabaseClasses.UserInformation;
import chatapp.ucr.com.ucrapp.DatabaseClasses.UsersList;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register Page";

    //widget and component instances
    private TextInputLayout mRegUserName;
    private TextInputLayout mRegUserEmail;
    private TextInputLayout mRegUserPassword;
    private android.support.v7.widget.Toolbar mRegToolBar;

    //button
    private Button mRegCreateButton;

    //progress dialog
    private ProgressDialog mRegProgressDialog;

    /*Firebase Authentication*/
    private FirebaseAuth mAuth;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*initialize the FirebaseAuth instance*/
        mAuth = FirebaseAuth.getInstance();


        //assignment of fields and widgets
        mRegUserName = findViewById(R.id.reg_name_field);
        mRegUserEmail = findViewById(R.id.reg_email_field);
        mRegUserPassword = findViewById(R.id.reg_password_field);

        mRegCreateButton = findViewById(R.id.reg_create_button);

        mRegToolBar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mRegToolBar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgressDialog = new ProgressDialog(this);

        //listen for button click
        mRegCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get strings from all the field
                String user_name = mRegUserName.getEditText().getText().toString();
                String user_email = mRegUserEmail.getEditText().getText().toString();
                String user_password = mRegUserPassword.getEditText().getText().toString();

                //show the progress dialog
                mRegProgressDialog.setTitle("Registering user");
                mRegProgressDialog.setMessage("Please wait while we create your account");
                mRegProgressDialog.setCanceledOnTouchOutside(false);
                mRegProgressDialog.show();

                //create account
                createAccount(user_name, user_email, user_password);

            }
        });
    }
    //TODO: Make sure the user_name parameter in this method is being used for something
    private void createAccount(final String user_name, final String user_email, final String user_password) {
        Log.d(TAG, "createAccount:" + user_email);
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            //dismiss progress dialog
                            mRegProgressDialog.dismiss();

                            //add user name to firbase

                            //TODO: user variable not being used
                            //get firebase user
                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid();
                            /*NOTE:
                             * will also add user name to firebase I don't know how to do that
                             * yet if any of you know please add it*/

                            //store information in Database

                            AddToDatabase addToDTB = new AddToDatabase();

                            addToDTB.registerNewUser(userID, user_name, user_email);

                            sendToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            //hind the progress bar
                            mRegProgressDialog.hide();

                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void sendToMain() {
        //send user to MainActvity which is the users home page
        Intent mainIntent = new Intent(RegisterActivity.this,
                MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        //we don't want the user to return to the page
        finish();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mRegUserEmail.getEditText().getText().toString();
        if (TextUtils.isEmpty(email)) {
            mRegUserEmail.setError("Required.");
            valid = false;
        } else {
            mRegUserEmail.setError(null);
        }

        String password = mRegUserPassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)) {
            mRegUserPassword.setError("Required.");
            valid = false;
        } else {
            mRegUserPassword.setError(null);
        }

        return valid;
    }
}
