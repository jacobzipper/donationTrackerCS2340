package a5x.cs2340.donationtracker.activities.registration;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.bodies.RegistrationBody;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Response;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the account.
 */
@SuppressLint("StaticFieldLeak")
public class AccountRegistrationTask extends AsyncTask<Void, Void, Boolean> {

    private final String mPassword;
    private final String mUsername;
    private final String mFirstname;
    private final String mLastname;
    private final String mRole;

    public AccountRegistrationTask(String username, String password, String firstname, String lastname, String role) {
        mPassword = password;
        mUsername = username;
        mFirstname = firstname;
        mLastname = lastname;
        mRole = role;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Response<StandardResponse> registrationAttempt;
        try {
            registrationAttempt = Webservice.accountService.register(new RegistrationBody(mUsername, mPassword, mRole, mFirstname, mLastname)).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // TODO Handle errors
        if (registrationAttempt.code() != 200) {
            // Do something
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {

    }

    @Override
    protected void onCancelled() {

    }
}