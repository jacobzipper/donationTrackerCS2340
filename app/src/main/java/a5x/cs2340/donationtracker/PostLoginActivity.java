package a5x.cs2340.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PostLoginActivity extends AppCompatActivity {
    protected String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        Intent loginIntent = getIntent();
        username = loginIntent.getStringExtra(LoginActivity.LOGGED_IN_USERNAME);
        TextView postLoginTextView = findViewById(R.id.postLoginTextView);
        postLoginTextView.setText("Welcome " + username);
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutBackToWelcome();
            }
        });
    }

    /**
     * Transitions back to the welcome screen
     */
    protected void logoutBackToWelcome() {
        username = null;
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }
}
