package a5x.cs2340.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {


    private TextView usernameTextView;
    private TextView passwordTextView;
    private TextView passwordVerifyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameTextView = findViewById(R.id.registrationUserName);
        passwordTextView = findViewById(R.id.registrationPassword);
        passwordVerifyTextView = findViewById(R.id.registrationPasswordVerify);
        Button registerButton = findViewById(R.id.registerButton);
        Button backButton = findViewById(R.id.registerBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToWelcome();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    /**
     * Attempt to register the user with the currently entered credentials
     */
    protected void attemptRegister() {
        usernameTextView.setError(null);
        passwordTextView.setError(null);
        passwordVerifyTextView.setError(null);

        View focusView = null;
        boolean cancel = false;
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String passwordVerify = passwordVerifyTextView.getText().toString();
        //Error checking
        if (username.isEmpty()) {
            usernameTextView.setError(getString(R.string.error_field_required));
            focusView = usernameTextView;
            cancel = true;
        } else if (password.isEmpty()) {
            passwordTextView.setError(getString(R.string.error_field_required));
            focusView = passwordTextView;
            cancel = true;
        } else if (passwordVerify.isEmpty()) {
            passwordVerifyTextView.setError(getString(R.string.error_field_required));
            focusView = passwordVerifyTextView;
            cancel = true;
        } else if (LoginActivity.checkExistingUsername(username)) {
            usernameTextView.setError(getString(R.string.error_username_already_exists));
            focusView = usernameTextView;
            cancel = true;
        } else if (password.length() < Constants.MIN_PASSWORD_LENGTH) {
            passwordTextView.setError(getString(R.string.error_invalid_password));
            focusView = passwordTextView;
            cancel = true;
        } else if (!passwordVerify.equals(password)) {
            passwordVerifyTextView.setError(getString(R.string.error_passwords_dont_match));
            focusView = passwordVerifyTextView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            //No errors, register the new credentials
            registerUser(username, password);
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();
            goBackToWelcome();
        }
    }

    /**
     * Return to the welcome screen
     */
    protected void goBackToWelcome() {
        Intent goBackToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(goBackToWelcomeIntent);
    }

    /**
     * Send username and password to LoginActivity store to be registered as valid
     *
     * @param username username to register
     * @param password password to register
     */
    protected void registerUser(String username, String password) {
        LoginActivity.registerUser(username, password);
    }
}
