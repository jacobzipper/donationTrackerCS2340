package a5x.cs2340.donationtracker;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.scoring.Result;

import static a5x.cs2340.donationtracker.Constants.AVERAGE_GUESSES;
import static a5x.cs2340.donationtracker.Constants.STRONG_GUESSES;
import static a5x.cs2340.donationtracker.Constants.VERY_WEAK_GUESSES;
import static a5x.cs2340.donationtracker.Constants.WEAK_GUESSES;

public class RegistrationActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView usernameTextView;
    private TextView passwordTextView;
    private TextView passwordVerifyTextView;
    private ProgressBar passwordStrengthMeter;
    private TextView passwordStrengthIndicatorText;
    private Nbvcxz passwordStrengthChecker = new Nbvcxz();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firstNameTextView = findViewById(R.id.registrationFirstName);
        lastNameTextView = findViewById(R.id.registrationLastName);
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
        passwordStrengthMeter = findViewById(R.id.passwordStrengthMeter);
        passwordStrengthIndicatorText = findViewById(R.id.passwordStrengthNotifier);
        passwordTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                updatePasswordStrength(s.toString());
            }
        });
        passwordVerifyTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Attempt to register the user with the currently entered credentials
     */
    protected void attemptRegister() {
        firstNameTextView.setError(null);
        lastNameTextView.setError(null);
        usernameTextView.setError(null);
        passwordTextView.setError(null);
        passwordVerifyTextView.setError(null);

        View focusView = null;
        boolean cancel = false;
        String firstName = firstNameTextView.getText().toString();
        String lastName = lastNameTextView.getText().toString();
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String passwordVerify = passwordVerifyTextView.getText().toString();
        //Error checking
        if (firstName.isEmpty()) {
            firstNameTextView.setError(getString(R.string.error_field_required));
            focusView = firstNameTextView;
            cancel = true;
        } else if (lastName.isEmpty()) {
            lastNameTextView.setError(getString(R.string.error_field_required));
            focusView = lastNameTextView;
            cancel = true;
        } else if (username.isEmpty()) {
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
            registerUser(firstName, lastName, username, password);
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
    protected void registerUser(String firstName, String lastName, String username, String password) {
        LoginActivity.registerUser(firstName, lastName, username, password);
    }
    /**
     * Updates the progress bar with the strength of the currently input password
     *
     */
    protected void updatePasswordStrength(String password) {
        if (password.isEmpty()) {
            passwordStrengthMeter.setVisibility(View.GONE);
            passwordStrengthIndicatorText.setVisibility(View.GONE);
        } else {
            passwordStrengthMeter.setVisibility(View.VISIBLE);
            passwordStrengthIndicatorText.setVisibility(View.VISIBLE);
            Result passwordStrengthEstimate = passwordStrengthChecker.estimate(password);
            BigDecimal guesses = passwordStrengthEstimate.getGuesses();
            if (guesses.compareTo(VERY_WEAK_GUESSES) < 0) {
                passwordStrengthMeter.setProgress(0);
                passwordStrengthMeter.setProgressTintList(ColorStateList.valueOf(Color.RED));
                passwordStrengthIndicatorText.setText(getString(R.string.password_strength_very_weak));
            } else if (guesses.compareTo(WEAK_GUESSES) < 0) {
                passwordStrengthMeter.setProgress(25);
                passwordStrengthMeter.setProgressTintList(ColorStateList.valueOf(Color.RED));
                passwordStrengthIndicatorText.setText(getString(R.string.password_strength_weak));
            } else if (guesses.compareTo(AVERAGE_GUESSES) < 0) {
                passwordStrengthMeter.setProgress(50);
                passwordStrengthMeter.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                passwordStrengthIndicatorText.setText(getString(R.string.password_strength_average));
            } else if (guesses.compareTo(STRONG_GUESSES) < 0) {
                passwordStrengthMeter.setProgress(75);
                passwordStrengthMeter.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                passwordStrengthIndicatorText.setText(getString(R.string.password_strength_strong));
            } else {
                passwordStrengthMeter.setProgress(100);
                passwordStrengthMeter.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                passwordStrengthIndicatorText.setText(getString(R.string.password_strength_very_strong));
            }
        }
    }
}
