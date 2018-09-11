package chatapp.ucr.com.ucrapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    //widget and component instances
    private Button mStartSignInButton;
    private Button mStartRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //buttons
        mStartRegisterButton = findViewById(R.id.start_register_button);
        mStartSignInButton = findViewById(R.id.start_signin_button);

        //listen for register button click
        mStartRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sending the user to register activity
                Intent regIntent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(regIntent);

            }
        });

        //listen for sign in button click
        mStartSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sending the user to login activity
                Intent loginIntent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
