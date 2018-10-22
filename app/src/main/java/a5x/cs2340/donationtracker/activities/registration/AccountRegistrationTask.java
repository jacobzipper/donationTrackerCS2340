package a5x.cs2340.donationtracker.activities.registration;

import android.annotation.SuppressLint;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.bodies.RegistrationBody;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Response;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the account.
 */
@SuppressLint("StaticFieldLeak")
public class AccountRegistrationTask extends WebserviceTask<RegistrationActivity,
        RegistrationBody, StandardResponse> {
    /**
     * Creates a new AccountRegistrationTask with the given context and body
     * @param activity The RegistrationActivity to use as the context
     * @param body The RegistrationBody to attempt to register
     */
    public AccountRegistrationTask(RegistrationActivity activity, RegistrationBody body) {
        super(activity, body);
    }

    @Override
    public Response<StandardResponse> doRequest(RegistrationBody body) throws IOException {
        return Webservice.accountService.register(body).execute();
    }

    @Override
    public void useResponse(StandardResponse response) {

    }

    @Override
    public void uiSuccess() {

    }

    @Override
    public void uiFailure() {

    }
}