package a5x.cs2340.donationtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import a5x.cs2340.donationtracker.users.Account;

public class PostLoginActivity extends AppCompatActivity {
    protected Account account;
    private String authenticationKey;

    @SuppressLint("StringFormatMatches") // This fixes an android studio bug
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        Intent loginIntent = getIntent();
        account = loginIntent.getParcelableExtra(LoginActivity.LOGGED_IN_USER);
        authenticationKey = loginIntent.getStringExtra(LoginActivity.CURRENT_AUTHENTICATION_KEY);
        TextView postLoginTextView = findViewById(R.id.postLoginTextView);
        postLoginTextView.setText(getString(R.string.post_login_welcome_string, account.getUserType().getLabel(), account.getName()));
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
        account = null;
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }
}
