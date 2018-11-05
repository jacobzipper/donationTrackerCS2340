package a5x.cs2340.donationtracker.webservice;

import android.support.annotation.Nullable;

import java.util.Objects;

import a5x.cs2340.donationtracker.models.users.Account;
import a5x.cs2340.donationtracker.models.users.UserType;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Represents the webservice associated with the app-singleton class
 */
public final class Webservice {
    static final int ERROR_CODE_OK = 200;
    private static final String API_URL = "https://donationtrackerzipper.herokuapp.com";

    private static final Webservice instance = new Webservice();

    // Logged in account information
    @Nullable
    private Account accountLoggedIn;
    @Nullable
    private String jwtToken;

    // Services
    private final AccountService accountService;
    private final DonationService donationService;

    private Webservice() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        accountService = retrofit.create(AccountService.class);
        donationService = retrofit.create(DonationService.class);}
    static {

    }

    /**
     * Get the instance representing this singleton class
     * @return the Webservice instance
     */
    public static Webservice getInstance() {return instance;}
    /**
     * Log in with the given account and jwt
     * @param acc the account to log in
     * @param jwt the jwt currently logging in
     */
    public void logIn(Account acc, String jwt) {
        accountLoggedIn = acc;
        jwtToken = jwt;
    }

    /**
     * Log out the currently logged in account
     */
    public void logOut() {
        accountLoggedIn = null;
        jwtToken = null;
    }

    /**
     * Checks whether or not an account is currently logged in
     * @return true if an account is logged in
     */
    public boolean isLoggedIn() {
        return accountLoggedIn != null;
    }

    /**
     * Gets the UserType of the logged in account
     * @return the UserType corresponding to the logged in account, null if no user is logged in
     */
    public UserType getLoggedInUserType() {
        assert accountLoggedIn != null;
        return (isLoggedIn())
            ? accountLoggedIn.getUserType() : null;
    }

    /**
     * Gets the name of the account currently logged in
     * @return the name of the logged in account, null if no account is logged in
     */
    public String getAccountName() {
        return (isLoggedIn() ? Objects.requireNonNull(accountLoggedIn).getName() : null);
    }
    /**
     * Gets the current APIType of the logged in user
     * @return the current APIType of the logged in user, null if no user is logged in
     */
    public String getCurrentUserAPIType() {
        return (isLoggedIn() ? getLoggedInUserType().getAPIType() : null);
    }

    /**
     * Gets the current permission level of the logged in user
     * @return the current permissions level of the logged in user, -1 if no user is logged in
     */
    public  int getCurrentUserPermissions() {
        return (isLoggedIn() ? getLoggedInUserType().getPermissionsLevel() : -1);
    }
    /**
     * Get the current jwt
     * @return the jwt
     */
    @Nullable
    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Getter for account service
     * @return the account service
     */
    public AccountService getAccountService() {
        return accountService;
    }

    /**
     * Getter for donation service
     * @return the donation service
     */
    public DonationService getDonationService() {
        return donationService;
    }
}
