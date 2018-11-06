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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.WelcomeActivity;
import a5x.cs2340.donationtracker.activities.postlogin.PostLoginActivity;
import a5x.cs2340.donationtracker.models.users.UserType;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.bodies.RegistrationBody;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.scoring.Result;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Activity for registering new accounts
 */
public class RegistrationActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final BigDecimal VERY_WEAK_GUESSES = new BigDecimal("1000000");
    private static final BigDecimal WEAK_GUESSES = new BigDecimal("100000000");
    private static final BigDecimal AVERAGE_GUESSES = new BigDecimal("1000000000");
    private static final BigDecimal STRONG_GUESSES = new BigDecimal("10000000000");
    private static final int REGISTRATION_PASSWORD_CHECK_DELAY = 750;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView usernameTextView;
    private TextView passwordTextView;
    private TextView passwordVerifyTextView;
    private ProgressBar passwordStrengthMeter;
    private TextView passwordStrengthIndicatorText;
    private Spinner userTypeSpinner;
    private final Nbvcxz passwordStrengthChecker = new Nbvcxz();
    private Timer strengthTimer = new Timer();

    private final Webservice webservice = Webservice.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (webservice.isLoggedIn()) {
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
        backButton.setOnClickListener(view -> goBackToWelcome());
        registerButton.setOnClickListener(view -> attemptRegister());
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
        passwordVerifyTextView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                attemptRegister();
                return true;
            }
            return false;
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
    private void attemptRegister() {
        passwordTextView.setError(null);
        passwordVerifyTextView.setError(null);

        View focusView = null;
        CharSequence firstName = firstNameTextView.getText();
        CharSequence lastName = lastNameTextView.getText();
        CharSequence username = usernameTextView.getText();
        CharSequence password = passwordTextView.getText();
        CharSequence passwordVerify = passwordVerifyTextView.getText();
        String passwordVerifyString = passwordVerify.toString();
        String passwordString = password.toString();
        UserType userType = (UserType) userTypeSpinner.getSelectedItem();
        //Error checking
        if (checkRequiredField(firstNameTextView, firstName)) {
            focusView = firstNameTextView;
        } else if (checkRequiredField(lastNameTextView, lastName)) {
            focusView = lastNameTextView;
        } else if (checkRequiredField(usernameTextView, username)) {
            focusView = usernameTextView;
        } else if (checkRequiredField(passwordTextView, password)) {
            focusView = passwordTextView;
        } else if (checkRequiredField(passwordVerifyTextView, passwordVerify)) {
            focusView = passwordVerifyTextView;
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            passwordTextView.setError(getString(R.string.error_invalid_password));
            focusView = passwordTextView;
        } else if (!passwordVerifyString.equals(passwordString)) {
            passwordVerifyTextView.setError(getString(R.string.error_password_mismatch));
            focusView = passwordVerifyTextView;
        }
        if (focusView != null) {
            focusView.requestFocus();
        } else {
            //No errors, register the new credentials
            registerUser(firstName.toString(), lastName.toString(), username.toString(),
                    password.toString(), userType);
        }
    }

    private boolean checkRequiredField(TextView textView, CharSequence content) {
        if (content.length() == 0) {
            textView.setError(getString(R.string.error_field_required));
            return true;
        }
        textView.setError(null);
        return false;
    }

    /**
     * Return to the welcome screen
     */
    private void goBackToWelcome() {
        Intent goBackToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(goBackToWelcomeIntent);
    }

    /**
     * Send username and password to LoginActivity store to be registered as valid
     *
     * @param username username to register
     * @param password password to register
     */
    private void registerUser(String firstName, String lastName, String username,
                              String password, UserType type) {
        AccountRegistrationTask mAuthTask = new AccountRegistrationTask();
        mAuthTask.execute(new RegistrationBody(username,
                password, type.getAPIType(), firstName, lastName));
    }

    public enum PasswordStrength {
        NONE(0, 0, Color.BLACK),
        VERY_WEAK(0, R.string.password_strength_very_weak, Color.RED),
        WEAK(25, R.string.password_strength_weak, Color.RED),
        AVERAGE(50, R.string.password_strength_average, Color.YELLOW),
        STRONG(75, R.string.password_strength_strong, Color.GREEN),
        VERY_STRONG(100, R.string.password_strength_very_strong, Color.GREEN);

        private final int progress;
        private final int stringId;
        private final int color;

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
    class PasswordStrengthTask extends AsyncTask<Void, Void, PasswordStrength> {

        private final String password;

        PasswordStrengthTask(String password) {
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

    protected class AccountRegistrationTask extends WebserviceTask<RegistrationBody,
            Void, StandardResponse> {

        @Override
        public Response<StandardResponse> doRequest(RegistrationBody body) throws IOException {
            if (Webservice.getInstance().isLoggedIn()) {
                Call<StandardResponse> standardResponseCall = Webservice.getInstance()
                        .getAccountService().register(body);
                return standardResponseCall.execute();
            }
            return null;
        }

        @SuppressLint({"NewApi", "LocalSuppress"})
        @Override
        protected void onPostExecute(StandardResponse response) {
            if ((response == null) || (response.getError() != 0)) {
                Toast failedRegistrationToast = Toast.makeText(getApplicationContext(),
                        "Registration Failed (try new username?)",
                        Toast.LENGTH_LONG);
                failedRegistrationToast.show();
                return;
            }
            Toast successfulRegistrationToast = Toast.makeText(getApplicationContext(),
                    "Registration Successful", Toast.LENGTH_LONG);
            successfulRegistrationToast.show();
            goBackToWelcome();
        }
    }
}
