package a5x.cs2340.donationtracker.activities.registration;

import android.annotation.SuppressLint;
import android.widget.Toast;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.AccountService;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.bodies.RegistrationBody;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the account.
 */
@SuppressLint("StaticFieldLeak")
public class AccountRegistrationTask extends WebserviceTask<RegistrationActivity,
        RegistrationBody, StandardResponse> {
    private final AccountService accountService;
    /**
     * Creates a new AccountRegistrationTask with the given context and body
     * @param activity The RegistrationActivity to use as the context
     * @param body The RegistrationBody to attempt to register
     */
    AccountRegistrationTask(RegistrationActivity activity, RegistrationBody body) {
        super(activity, body);
        Webservice webservice = Webservice.getInstance();
        accountService = webservice.getAccountService();
    }

    @Override
    public Response<StandardResponse> doRequest(RegistrationBody body) throws IOException {
        Call<StandardResponse> standardResponseCall = accountService.register(body);
        return standardResponseCall.execute();
    }

    @Override
    public void useResponse(StandardResponse response) {

    }

    @Override
    public void uiSuccess() {
        Toast successfulRegistrationToast = Toast.makeText(mContext,
                "Registration Successful", Toast.LENGTH_LONG);
        successfulRegistrationToast.show();
        mContext.goBackToWelcome();
    }

    @Override
    public void uiFailure() {
        Toast failedRegistrationToast = Toast.makeText(mContext,
                "Registration Failed (try new username?)",
                Toast.LENGTH_LONG);
        failedRegistrationToast.show();
    }
}