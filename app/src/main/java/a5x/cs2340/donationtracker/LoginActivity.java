package a5x.cs2340.donationtracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Random;

import a5x.cs2340.donationtracker.users.Admin;
import a5x.cs2340.donationtracker.users.LocationEmployee;
import a5x.cs2340.donationtracker.users.RegularUser;
import a5x.cs2340.donationtracker.users.User;
import a5x.cs2340.donationtracker.users.UserSet;
import a5x.cs2340.donationtracker.users.UserType;

import static a5x.cs2340.donationtracker.Constants.AUTHENTICATION_UPPER_BOUND;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private static UserSet validUsers = new UserSet();
    private static HashSet<String> validAuthenticationTokens = new HashSet<>();
    public static final String LOGGED_IN_USER = "donationTracker.successfulUser";
    public static final String CURRENT_AUTHENTICATION_KEY = "donationTracker.currentAuthKey";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = findViewById(R.id.username);
        //populateAutoComplete(); //Uncomment if we implement autocompletion of usernames
        if (validUsers.isEmpty()) {
            createDummyUser();
        }
        mPasswordView =  findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUsernameSignInButton = findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        Button backToWelcomeButton = findViewById(R.id.fromLoginToWelcomeButton);
        backToWelcomeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToWelcome();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Adds dummy credentials "user:Password" to set of valid credentials
     */
    private void createDummyUser() {
        validUsers.add(new RegularUser("DEFAULT", "USER","user", sha256Hash("pass")));
    }

    /**
     * Convert string to hash using SHA-256 algorithm
     *
     * @param starting the string to convert
     * @return the result of the hashing
     */
    private static String sha256Hash(String starting) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            byte[] startingBytes = starting.getBytes("UTF-8");
            hasher.update(startingBytes);
            StringBuilder sb = new StringBuilder();
            for (byte startingByte : startingBytes) {
                sb.append(Integer.toString((startingByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            Log.d("Encryption", "Exception was thrown trying to hash");
            return starting;
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Checks if username is valid (currently just if the username is in the valid credentials set)
     *
     * @param username the username to check
     * @return true if the username exists in the valid credentials
     */
    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return validUsers.containsUsername(username);
    }

    /**
     * Checks if the password meets password specs (currently just minimum length)
     *
     * @param password the password to check
     * @return true if the password meets specifications
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= Constants.MIN_PASSWORD_LENGTH;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPassword;
        private final User user;
        UserLoginTask(String username, String password) {
            mPassword = password;
            this.user = validUsers.getUser(username);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.d("test", "oops");
                return false;
            }

            return user.checkPassword(sha256Hash(mPassword));

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            Log.d("test", "On PostExecute with success = " + success);
            if (success) {
                Log.d("test", "Attempting to go to PostLogin");
                goToPostLogin(user);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Transitions from the login screen to the post-login screen
     *
     * @param user the user of the successfully logged in person
     */
    protected void goToPostLogin(User user) {
        Intent goToPostLoginIntent = new Intent(this, PostLoginActivity.class);
        goToPostLoginIntent.putExtra(LOGGED_IN_USER, user);
        String authenticationKey = sha256Hash(Integer.toString((new Random()).nextInt(AUTHENTICATION_UPPER_BOUND)));
        goToPostLoginIntent.putExtra(CURRENT_AUTHENTICATION_KEY, authenticationKey);
        validAuthenticationTokens.add(authenticationKey);
        startActivity(goToPostLoginIntent);
    }

    /**
     * Public method to check if username exists in valid credentials
     *
     * @param username the username to check
     * @return true if the username exists in the valid credentials
     */
    public static boolean checkExistingUsername(String username) {
        return validUsers.containsUsername(username);
    }

    /**
     * Adds the passed in credentials to the valid credentials
     *
     * @param username the username to add
     * @param password the plaintext password to hash and add
     */
    static void registerUser(String firstName, String lastName, String username, String password, UserType type) {
        switch(type) {
            case REGULAR_USER:
                validUsers.add(new RegularUser(firstName, lastName, username, sha256Hash(password)));
                break;
            case ADMIN:
                validUsers.add(new Admin(firstName, lastName, username, sha256Hash(password)));
                break;
            case LOCATION_EMPLOYEE:
                validUsers.add(new LocationEmployee(firstName, lastName, username, sha256Hash(password)));
                break;
            default:
                validUsers.add(new RegularUser(firstName, lastName, username, sha256Hash(password)));
        }

    }

    /**
     * Transition back to the welcome screen
     */
    protected void goBackToWelcome() {
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }

    /**
     * Validates whether the passed key is a valid authentication key
     *
     * @param key the key to check
     * @return true if the key is currently valid
     */
    static boolean checkKey(String key) {
        return validAuthenticationTokens.contains(key);
    }

    /**
     * Removes the key from the valid set
     *
     * @param key the key to remove
     */
    static void removeKey(String key) {
        validAuthenticationTokens.remove(key);
    }
}

