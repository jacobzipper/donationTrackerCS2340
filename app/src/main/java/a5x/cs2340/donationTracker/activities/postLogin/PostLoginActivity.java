package a5x.cs2340.donationTracker.activities.postLogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import a5x.cs2340.donationTracker.R;
import a5x.cs2340.donationTracker.WelcomeActivity;
import a5x.cs2340.donationTracker.activities.adminTools.AdminToolsActivity;
import a5x.cs2340.donationTracker.activities.login.LoginActivity;
import a5x.cs2340.donationTracker.webservice.Webservice;

/**
 * Default activity users go to after they successfully log in
 */
public class PostLoginActivity extends AppCompatActivity {
    private GetLocationsTask mGetLocationsTask;
    private final Webservice webservice = Webservice.getInstance();
    @SuppressLint("StringFormatMatches") // This fixes an android studio bug
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!webservice.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        setContentView(R.layout.activity_post_login);
        TextView postLoginTextView = findViewById(R.id.postLoginTextView);
        postLoginTextView.setText(getString(R.string.post_login_welcome_string,
                webservice.getUserTypeLabel(),
                webservice.getAccountName()));
        Button logoutButton = findViewById(R.id.logoutButton);
        mGetLocationsTask = new GetLocationsTask(this);
        mGetLocationsTask.execute((Void) null);
        logoutButton.setOnClickListener(view -> {
            logoutBackToWelcome();
            if (mGetLocationsTask != null) {
                mGetLocationsTask.cancel(true);
            }
        });
        Button toAdminToolsButton = findViewById(R.id.toToolsButton);
        toAdminToolsButton.setOnClickListener(v -> toAdminTools());
        Button toMapButton = findViewById(R.id.toMapButton);
        toMapButton.setOnClickListener(v -> toMapScreen());
    }

    /**
     * Transitions back to the welcome screen
     */
    private void logoutBackToWelcome() {
        webservice.logOut();
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
    /**
     * Transitions to map screen
     */
    private void toMapScreen() {
        Intent toMapIntent = new Intent(this, LocationsMapActivity.class);
        startActivity(toMapIntent);
    }
}
