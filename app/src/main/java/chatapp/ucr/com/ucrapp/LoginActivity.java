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

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "Register Page";

    //widget and component instances
    private TextInputLayout mUserEmail;
    private TextInputLayout mUserPassword;
    private android.support.v7.widget.Toolbar mLoginToolBar;

    //button
    private Button mLoginButton;

    //progress dialog
    private ProgressDialog mProgressDialog;

    /*Firebase Authentication*/
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*initialize the FirebaseAuth instance*/
        mAuth = FirebaseAuth.getInstance();

        //assignment of fields and widgets
        mUserEmail = findViewById(R.id.login_email_field);
        mUserPassword = findViewById(R.id.login_password_field);

        mLoginButton = findViewById(R.id.login_signin_button);

        mLoginToolBar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mLoginToolBar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressDialog = new ProgressDialog(this);

        //listen for button click
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get strings from all the fields
                String user_email = mUserEmail.getEditText().getText().toString();
                String user_password = mUserPassword.getEditText().getText().toString();

                //show the progress dialog
                mProgressDialog.setTitle("Signing in...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                //create account
                signIn(user_email, user_password);
            }
        });

    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //dismiss progress dialog
                            mProgressDialog.dismiss();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            //TODO: user variable needs to be used
                            FirebaseUser user = mAuth.getCurrentUser();

                            //move to the MainActivity
                            sendToMain();

                        } else {
                            //hide progress dialog
                            mProgressDialog.hide();

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mUserEmail.getEditText().getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUserEmail.setError("Required.");
            valid = false;
        } else {
            mUserEmail.setError(null);
        }

        String password = mUserPassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)) {
            mUserPassword.setError("Required.");
            valid = false;
        } else {
            mUserPassword.setError(null);
        }

        return valid;
    }
    private void sendToMain() {

        //send user to MainActvity which is the users home page
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        //we don't want the user to return to the page
        finish();
    }
}
