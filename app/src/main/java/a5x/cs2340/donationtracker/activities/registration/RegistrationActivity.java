package a5x.cs2340.donationtracker.activities.registration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import a5x.cs2340.donationtracker.Constants;
import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.WelcomeActivity;
import a5x.cs2340.donationtracker.activities.postlogin.PostLoginActivity;
import a5x.cs2340.donationtracker.models.users.UserType;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.bodies.RegistrationBody;
import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.scoring.Result;

import static a5x.cs2340.donationtracker.Constants.AVERAGE_GUESSES;
import static a5x.cs2340.donationtracker.Constants.REGISTRATION_PASSWORD_CHECK_DELAY;
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
    private Spinner userTypeSpinner;
    private Nbvcxz passwordStrengthChecker = new Nbvcxz();
    private Timer strengthTimer = new Timer();

    private AccountRegistrationTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Webservice.isLoggedIn()) {
            startActivity(new Intent(this, PostLoginActivity.class));
            return;
        }
        setContentView(R.layout.activity_registration);
        firstNameTextView = findViewById(R.id.registrationFirstName);
        lastNameTextView = findViewById(R.id.registrationLastName);
        usernameTextView = findViewById(R.id.registrationUserName);
        passwordTextView = findViewById(R.id.registrationPassword);
        passwordVerifyTextView = findViewById(R.id.registrationPasswordVerify);
        Button registerButton = findViewById(R.id.registerButton);
        Button backButton = findViewById(R.id.registerBackButton);
        userTypeSpinner = findViewById(R.id.registrationUserTypeSpinner);
        ArrayAdapter<UserType> userTypeArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, UserType.values());
        userTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(userTypeArrayAdapter);
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
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                strengthTimer.cancel();
                strengthTimer.purge();
                strengthTimer = new Timer();
                strengthTimer.schedule(getNewTimerTask(new PasswordStrengthTask((s.toString()))),
                        REGISTRATION_PASSWORD_CHECK_DELAY);
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

    private TimerTask getNewTimerTask(final PasswordStrengthTask strengthTask) {
        return new TimerTask() {
            @Override
            public void run() {
                strengthTask.execute();
                cancel();
            }
        };
    }

    /**
     * Attempt to register the account with the currently entered credentials
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
        UserType userType = (UserType) userTypeSpinner.getSelectedItem();
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
//        } else if (LoginActivity.checkExistingUsername(username)) {
//            usernameTextView.setError(getString(R.string.error_username_already_exists));
//            focusView = usernameTextView;
//            cancel = true;
        } else if (password.length() < Constants.MIN_PASSWORD_LENGTH) {
            passwordTextView.setError(getString(R.string.error_invalid_password));
            focusView = passwordTextView;
            cancel = true;
        } else if (!passwordVerify.equals(password)) {
            passwordVerifyTextView.setError(getString(R.string.error_password_mismatch));
            focusView = passwordVerifyTextView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            //No errors, register the new credentials
            registerUser(firstName, lastName, username, password, userType);
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
    protected void registerUser(String firstName, String lastName, String username,
                                String password, UserType type) {
        mAuthTask = new AccountRegistrationTask(this, new RegistrationBody(username,
                password, type.getAPIType(), firstName, lastName));
        mAuthTask.execute((Void) null);
    }

    public enum PasswordStrength {
        NONE(0, 0, Color.BLACK),
        VERY_WEAK(0, R.string.password_strength_very_weak, Color.RED),
        WEAK(25, R.string.password_strength_weak, Color.RED),
        AVERAGE(50, R.string.password_strength_average, Color.YELLOW),
        STRONG(75, R.string.password_strength_strong, Color.GREEN),
        VERY_STRONG(100, R.string.password_strength_very_strong, Color.GREEN);

        private int progress;
        private int stringId;
        private int color;

        PasswordStrength(int progress, int stringId, int color) {
            this.progress = progress;
            this.stringId = stringId;
            this.color = color;
        }
    }

    /**
     * Async task to update the password strength
     */
    @SuppressLint("StaticFieldLeak")
    public class PasswordStrengthTask extends AsyncTask<Void, Void, PasswordStrength> {

        private String password;

        public PasswordStrengthTask(String password) {
            this.password = password;
        }

        @Override
        protected PasswordStrength doInBackground(Void... params) {
            return updatePasswordStrength(password);
        }

        @Override
        protected void onPostExecute(PasswordStrength strength) {
            if (strength == PasswordStrength.NONE) {
                passwordStrengthMeter.setVisibility(View.GONE);
                passwordStrengthIndicatorText.setVisibility(View.GONE);
            } else {
                passwordStrengthMeter.setVisibility(View.VISIBLE);
                passwordStrengthIndicatorText.setVisibility(View.VISIBLE);
                passwordStrengthMeter.setProgress(strength.progress);
                passwordStrengthMeter.setProgressTintList(ColorStateList.valueOf(strength.color));
                passwordStrengthIndicatorText.setText(getString(strength.stringId));
            }
        }

        /**
         * Updates the progress bar with the strength of the currently input password
         */
        private PasswordStrength updatePasswordStrength(String password) {
            if (password.isEmpty()) {
                return PasswordStrength.NONE;
            } else {
                Result passwordStrengthEstimate = passwordStrengthChecker.estimate(password);
                BigDecimal guesses = passwordStrengthEstimate.getGuesses();
                if (guesses.compareTo(VERY_WEAK_GUESSES) < 0) {
                    return PasswordStrength.VERY_WEAK;
                } else if (guesses.compareTo(WEAK_GUESSES) < 0) {
                    return PasswordStrength.WEAK;
                } else if (guesses.compareTo(AVERAGE_GUESSES) < 0) {
                    return PasswordStrength.AVERAGE;
                } else if (guesses.compareTo(STRONG_GUESSES) < 0) {
                    return PasswordStrength.STRONG;
                } else {
                    return PasswordStrength.VERY_STRONG;
                }
            }
        }

    }
}
