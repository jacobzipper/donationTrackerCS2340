package a5x.cs2340.donationtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import a5x.cs2340.donationtracker.users.User;

public class PostLoginActivity extends AppCompatActivity {
    protected User user;
    private String authenticationKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        Intent loginIntent = getIntent();
        user = loginIntent.getParcelableExtra(LoginActivity.LOGGED_IN_USER);
        authenticationKey = loginIntent.getStringExtra(LoginActivity.CURRENT_AUTHENTICATION_KEY);
        TextView postLoginTextView = findViewById(R.id.postLoginTextView);
        postLoginTextView.setText(getString(R.string.post_login_welcome_string, user.getName()));
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
        user = null;
        LoginActivity.removeKey(authenticationKey);
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!LoginActivity.checkKey(authenticationKey)) {
            AlertDialog.Builder errorDialogBuilder = (new AlertDialog.Builder(this))
                    .setMessage(getString(R.string.invalid_auth_token)).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            logoutBackToWelcome();
                        }
                    });
            errorDialogBuilder.show();

        }
    }
}
