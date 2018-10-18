package a5x.cs2340.donationtracker.activities.login;

import android.annotation.SuppressLint;

import java.io.IOException;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.models.users.Account;
import a5x.cs2340.donationtracker.models.users.Admin;
import a5x.cs2340.donationtracker.models.users.LocationEmployee;
import a5x.cs2340.donationtracker.models.users.Manager;
import a5x.cs2340.donationtracker.models.users.User;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.bodies.LoginBody;
import a5x.cs2340.donationtracker.webservice.responses.LoginResponse;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class AccountLoginTask extends WebserviceTask<LoginActivity, LoginBody, LoginResponse> {
    private Account account;
    private String jwt;

    public AccountLoginTask(LoginActivity activity, LoginBody body) {
        super(activity, body);
    }

    @Override
    protected void onCancelled() {
        mContext.showProgress(false);
    }

    @Override
    public Response<LoginResponse> doRequest(LoginBody body) throws IOException {
        return Webservice.accountService.login(body).execute();
    }

    @Override
    public void useResponse(LoginResponse response) {
        jwt = response.getJwt();
        switch (response.getRole()) {
            case "admins":
                account = new Admin(response.getFirstname(), response.getLastname(), mBody.getUsername(), mBody.getPassword());
                break;
            case "users":
                account = new User(response.getFirstname(), response.getLastname(), mBody.getUsername(), mBody.getPassword());
                break;
            case "employees":
                account = new LocationEmployee(response.getFirstname(), response.getLastname(), mBody.getUsername(), mBody.getPassword());
                break;
            case "managers":
                account = new Manager(response.getFirstname(), response.getLastname(), mBody.getUsername(), mBody.getPassword());
                break;
            default:
                account = new User(response.getFirstname(), response.getLastname(), mBody.getUsername(), mBody.getPassword());
                break;
        }
        Webservice.logIn(account, jwt);
    }

    @Override
    public void uiSuccess() {
        mContext.showProgress(false);
        mContext.goToPostLogin(account, jwt);
    }

    @Override
    public void uiFailure() {
        mContext.showProgress(false);
        mContext.indicateIncorrectPassword();
        mContext.findViewById(R.id.username).requestFocus();
    }
}
