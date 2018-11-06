package a5x.cs2340.donationtracker.activities.login;

import android.annotation.SuppressLint;

import java.io.IOException;

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
 * Represents an asynchronous login task used to authenticate
 * the account.
 */
@SuppressLint("StaticFieldLeak")
public class AccountLoginTask extends WebserviceTask<LoginActivity, LoginBody, LoginResponse> {
    private Account account;
    private String jwt;
    private final AccountService accountService;
    private final Webservice webservice;

    /**
     * Creates a new AccountLoginTask with the given activity as a context and the LoginBody to use
     * @param activity The LoginActivity to use as the context for this task
     * @param body The LoginBody to use to attempt to login
     */
    AccountLoginTask(LoginActivity activity, LoginBody body) {
        super(activity, body);
        webservice = Webservice.getInstance();
        accountService = webservice.getAccountService();
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }

    @Override
    public Response<LoginResponse> doRequest(LoginBody body) throws IOException {
        Call<LoginResponse> loginResponseCall = accountService.login(body);
        return loginResponseCall.execute();
    }

    @Override
    public void useResponse(LoginResponse response) {
        jwt = response.getJwt();
        String firstName = response.getFirstname();
        String lastName = response.getLastname();
        String username = mBody.getUsername();
        String password = mBody.getPassword();
        switch (response.getRole()) {
            case "admins":
                account = new Admin(firstName, lastName, username, password);
                break;
            case "users":
                account = new User(firstName, lastName, username, password);
                break;
            case "employees":
                account = new LocationEmployee(firstName, lastName, username, password);
                break;
            case "managers":
                account = new Manager(firstName, lastName, username, password);
                break;
            default:
                account = new User(firstName, lastName, username, password);
                break;
        }
        webservice.logIn(account, jwt);
    }

    @Override
    public void uiSuccess() {
        mContext.showProgress(false);
        mContext.goToPostLogin();
    }

    @Override
    public void uiFailure() {
        mContext.showProgress(false);
        mContext.indicateIncorrectPassword();
    }
}
