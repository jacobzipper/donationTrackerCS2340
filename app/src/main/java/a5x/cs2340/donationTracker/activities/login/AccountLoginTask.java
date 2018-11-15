package a5x.cs2340.donationTracker.activities.login;

import android.annotation.SuppressLint;

import java.io.IOException;

import a5x.cs2340.donationTracker.models.users.Account;
import a5x.cs2340.donationTracker.models.users.Admin;
import a5x.cs2340.donationTracker.models.users.LocationEmployee;
import a5x.cs2340.donationTracker.models.users.Manager;
import a5x.cs2340.donationTracker.models.users.User;
import a5x.cs2340.donationTracker.webservice.AccountService;
import a5x.cs2340.donationTracker.webservice.Webservice;
import a5x.cs2340.donationTracker.webservice.WebserviceTask;
import a5x.cs2340.donationTracker.webservice.bodies.LoginBody;
import a5x.cs2340.donationTracker.webservice.responses.LoginResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Represents an asynchronous login task used to authenticate
 * the account.
 */
@SuppressLint("StaticFieldLeak")
class AccountLoginTask extends WebserviceTask<LoginActivity, LoginBody, LoginResponse> {
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
    public Response<LoginResponse> doRequest(LoginBody body) throws IOException {
        Call<LoginResponse> loginResponseCall = accountService.login(body);
        return loginResponseCall.execute();
    }

    @Override
    public void useResponse(LoginResponse response) {
        jwt = response.getJwt();
        String username = mBody.getUsername();
        String password = mBody.getPassword();
        switch (response.getRole()) {
            case "admins":
                account = new Admin(response, username, password);
                break;
            case "users":
                account = new User(response, username, password);
                break;
            case "employees":
                account = new LocationEmployee(response, username, password);
                break;
            case "managers":
                account = new Manager(response, username, password);
                break;
            default:
                account = new User(response, username, password);
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

    /**
     * Getter for account
     * Suppress unused because retrofit
     * @return account corresponding to this login task
     */
    @SuppressWarnings("unused")
    public Account getAccount() {
        return account;
    }

    /**
     * Getter for JWT
     * Suppress unused because retrofit
     * @return JWT corresponding to this login task
     */
    @SuppressWarnings("unused")
    public String getJwt() {
        return jwt;
    }
}
