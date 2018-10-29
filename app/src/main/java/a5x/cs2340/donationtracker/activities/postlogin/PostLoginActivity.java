package a5x.cs2340.donationtracker.activities.postlogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.WelcomeActivity;
import a5x.cs2340.donationtracker.activities.admintools.AdminToolsActivity;
import a5x.cs2340.donationtracker.activities.login.LoginActivity;
import a5x.cs2340.donationtracker.webservice.Webservice;

/**
 * Default activity users go to after they successfully log in
 */
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
        postLoginTextView.setText(getString(R.string.post_login_welcome_string,
                Webservice.getLoggedInUserType().getLabel(),
                Webservice.getAccountLoggedIn().getName()));
        Button logoutButton = findViewById(R.id.logoutButton);
        mGetLocationsTask = new GetLocationsTask(this, null);
        mGetLocationsTask.execute((Void) null);
        logoutButton.setOnClickListener(view -> {
            logoutBackToWelcome();
            if (mGetLocationsTask != null) {
                mGetLocationsTask.cancel(true);
            }
        });
        Button toAdminToolsButton = findViewById(R.id.toToolsButton);
        toAdminToolsButton.setOnClickListener(v -> toAdminTools());
    }

    /**
     * Transitions back to the welcome screen
     */
    private void logoutBackToWelcome() {
        Webservice.logOut();
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }

    /**
     * Transitions to the admin tools screen
     */
    private void toAdminTools() {
        Intent toAdminToolsIntent = new Intent(this, AdminToolsActivity.class);
        startActivity(toAdminToolsIntent);
    }
}
