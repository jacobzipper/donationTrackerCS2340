package a5x.cs2340.donationtracker.activities.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.WelcomeActivity;
import a5x.cs2340.donationtracker.activities.postlogin.PostLoginActivity;
import a5x.cs2340.donationtracker.models.users.Account;
import a5x.cs2340.donationtracker.models.users.Admin;
import a5x.cs2340.donationtracker.models.users.LocationEmployee;
import a5x.cs2340.donationtracker.models.users.Manager;
import a5x.cs2340.donationtracker.models.users.User;
import a5x.cs2340.donationtracker.webservice.AccountService;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.bodies.LoginBody;
import a5x.cs2340.donationtracker.webservice.responses.LoginResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MIN_USERNAME_LENGTH = 1;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    @Nullable
    private AccountLoginTask mAuthTask;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;

    private final Webservice webservice = Webservice.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If user is logged in we go directly to the PostLogin screen
        if (webservice.isLoggedIn()) {
            goToPostLogin();
            return;
        }
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mUsernameSignInButton = findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(view -> attemptLogin());
        Button backToWelcomeButton = findViewById(R.id.fromLoginToWelcomeButton);
        backToWelcomeButton.setOnClickListener(view -> goBackToWelcome());
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
        CharSequence username = mUsernameView.getText();
        CharSequence password = mPasswordView.getText();

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
            mAuthTask = new AccountLoginTask();
            mAuthTask.execute(new LoginBody(username.toString(),
                    password.toString()));
        }
    }

    /**
     * Checks if username is valid (currently just if the username is in the valid credentials set)
     *
     * @param username the username to check
     * @return true if the username exists in the valid credentials
     */
    private boolean isUsernameValid(CharSequence username) {
        return username.length() >= MIN_USERNAME_LENGTH;
    }

    /**
     * Checks if the password meets password specs (currently just minimum length)
     *
     * @param password the password to check
     * @return true if the password meets specifications
     */
    private boolean isPasswordValid(CharSequence password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    private void indicateIncorrectPassword() {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mUsernameView.requestFocus();
        mAuthTask = null;
    }

    /**
     * Transitions from the login screen to the post-login screen
     *
     */
    private void goToPostLogin() {
        Intent goToPostLoginIntent = new Intent(this, PostLoginActivity.class);
        startActivity(goToPostLoginIntent);
    }

    /**
     * Transition back to the welcome screen
     */
    private void goBackToWelcome() {
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }

    /**
     * Suppressed because need access to UI elements and can't make this static
     */
    @SuppressLint("StaticFieldLeak")
    private class AccountLoginTask extends WebserviceTask<LoginBody, Void, LoginResponse> {


        @Override
        public Response<LoginResponse> doRequest(LoginBody body) throws IOException {
            Webservice webservice = Webservice.getInstance();
            AccountService accountService = webservice.getAccountService();
            Call<LoginResponse> loginResponseCall = accountService.login(body);
            return loginResponseCall.execute();
        }

        @Override
        public void onPostExecute(LoginResponse response) {
            if (response == null) {
                indicateIncorrectPassword();
                return;
            }
            Account account;
            String jwt = response.getJwt();
            String firstName = response.getFirstname();
            String lastName = response.getLastname();

            switch (response.getRole()) {
                case "admins":
                    account = new Admin(firstName, lastName, null, null);
                    break;
                case "users":
                    account = new User(firstName, lastName, null, null);
                    break;
                case "employees":
                    account = new LocationEmployee(firstName, lastName, null, null);
                    break;
                case "managers":
                    account = new Manager(firstName, lastName, null, null);
                    break;
                default:
                    account = new User(firstName, lastName, null, null);
                    break;
            }
            Webservice webservice = Webservice.getInstance();
            webservice.logIn(account, jwt);
            goToPostLogin();
        }
    }
}

