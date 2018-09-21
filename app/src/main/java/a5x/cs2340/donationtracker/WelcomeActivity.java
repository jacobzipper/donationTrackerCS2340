package a5x.cs2340.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button goToLoginButton = (Button) findViewById(R.id.welcomeLoginButton);
        goToLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }
    protected void goToLogin() {
        Intent goToLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(goToLoginIntent);
    }

}
