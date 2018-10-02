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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import a5x.cs2340.donationtracker.users.Account;
import a5x.cs2340.donationtracker.users.Admin;
import a5x.cs2340.donationtracker.users.LocationEmployee;
import a5x.cs2340.donationtracker.users.Manager;
import a5x.cs2340.donationtracker.users.User;
import a5x.cs2340.donationtracker.webservice.RestService;
import a5x.cs2340.donationtracker.webservice.bodies.LoginBody;
import a5x.cs2340.donationtracker.webservice.responses.LoginResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // Constants representing identifiers for data to be passed with Intent to PostLogin
    public static final String LOGGED_IN_USER = "donationTracker.successfulUser";
    public static final String CURRENT_AUTHENTICATION_KEY = "donationTracker.currentAuthKey";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private RestService service = retrofit.create(RestService.class);

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
        mPasswordView = findViewById(R.id.password);
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

        // Check for a valid password, if the account entered one.
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
            // perform the account login attempt.
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
        return username.length() >= Constants.MIN_USERNAME_LENGTH;
    }

    /**
     * Checks if the password meets password specs (currently just minimum length)
     *
     * @param password the password to check
     * @return true if the password meets specifications
     */
    private boolean isPasswordValid(String password) {
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
     * the account.
     */
    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPassword;
        private final String mUsername;
        private Account account;
        private String jwt;

        UserLoginTask(String username, String password) {
            mPassword = password;
            mUsername = username;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Response<LoginResponse> loginAttempt;
            try {
                loginAttempt = service.login(new LoginBody(mUsername, mPassword)).execute();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            // TODO: Error messages for each error code from backend
            LoginResponse loginResponse = loginAttempt.body();
            if (loginAttempt.code() == 200 && loginResponse != null && loginResponse.getError() == 0) {
                jwt = loginResponse.getJwt();
                switch (loginResponse.getRole()) {
                    case "admins":
                        account = new Admin(loginResponse.getFirstname(), loginResponse.getLastname(), mUsername, mPassword);
                        break;
                    case "users":
                        account = new User(loginResponse.getFirstname(), loginResponse.getLastname(), mUsername, mPassword);
                        break;
                    case "employees":
                        account = new LocationEmployee(loginResponse.getFirstname(), loginResponse.getLastname(), mUsername, mPassword);
                        break;
                    case "managers":
                        account = new Manager(loginResponse.getFirstname(), loginResponse.getLastname(), mUsername, mPassword);
                        break;
                    default:
                        account = new User(loginResponse.getFirstname(), loginResponse.getLastname(), mUsername, mPassword);
                        break;
                }
                return true;
            }
            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                goToPostLogin(account, jwt);
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
     * @param account the account of the successfully logged in person
     */
    protected void goToPostLogin(Account account, String jwt) {
        Intent goToPostLoginIntent = new Intent(this, PostLoginActivity.class);
        goToPostLoginIntent.putExtra(LOGGED_IN_USER, account);
        goToPostLoginIntent.putExtra(CURRENT_AUTHENTICATION_KEY, jwt);
        startActivity(goToPostLoginIntent);
    }

    /**
     * Transition back to the welcome screen
     */
    protected void goBackToWelcome() {
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }
}

