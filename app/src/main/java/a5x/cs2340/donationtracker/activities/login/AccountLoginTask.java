package a5x.cs2340.donationtracker.activities.login;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.users.Account;
import a5x.cs2340.donationtracker.users.Admin;
import a5x.cs2340.donationtracker.users.LocationEmployee;
import a5x.cs2340.donationtracker.users.Manager;
import a5x.cs2340.donationtracker.users.User;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.bodies.LoginBody;
import a5x.cs2340.donationtracker.webservice.responses.LoginResponse;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class AccountLoginTask extends AsyncTask<Void, Void, Boolean> {
    private final LoginActivity mContext;
    private final String mPassword;
    private final String mUsername;
    private Account account;
    private String jwt;

    public AccountLoginTask(LoginActivity context, String username, String password) {
        mContext = context;
        mPassword = password;
        mUsername = username;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Response<LoginResponse> loginAttempt;
        try {
            loginAttempt = Webservice.accountService.login(new LoginBody(mUsername, mPassword)).execute();
        } catch (IOException e) {
            // TODO: Graceful error handling
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
        mContext.showProgress(false);
        if (success) {
            mContext.goToPostLogin(account, jwt);
        } else {
            mContext.indicateIncorrectPassword();
            mContext.findViewById(R.id.username).requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }
}
