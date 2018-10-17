package a5x.cs2340.donationtracker.activities.postlogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.WelcomeActivity;
import a5x.cs2340.donationtracker.activities.login.LoginActivity;
import a5x.cs2340.donationtracker.webservice.Webservice;

public class PostLoginActivity extends AppCompatActivity {
    private GetLocationsTask mGetLocationsTask = null;

    @SuppressLint("StringFormatMatches") // This fixes an android studio bug
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Webservice.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        setContentView(R.layout.activity_post_login);
        TextView postLoginTextView = findViewById(R.id.postLoginTextView);
        postLoginTextView.setText(getString(R.string.post_login_welcome_string, Webservice.getAccountLoggedIn().getUserType().getLabel(), Webservice.getAccountLoggedIn().getName()));
        Button logoutButton = findViewById(R.id.logoutButton);
        mGetLocationsTask = new GetLocationsTask(this);
        mGetLocationsTask.execute((Void) null);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutBackToWelcome();
                if (mGetLocationsTask != null) {
                    mGetLocationsTask.cancel(true);
                }
            }
        });
    }

    /**
     * Transitions back to the welcome screen
     */
    protected void logoutBackToWelcome() {
        Webservice.logOut();
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }
}
