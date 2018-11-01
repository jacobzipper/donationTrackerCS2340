package a5x.cs2340.donationtracker.webservice;

import android.support.annotation.Nullable;

import a5x.cs2340.donationtracker.Constants;
import a5x.cs2340.donationtracker.models.users.Account;
import a5x.cs2340.donationtracker.models.users.UserType;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Represents the webservice associated with the app
 */
public class Webservice {
    private static final Retrofit retrofit;

    // Logged in account information
    @Nullable
    private static Account accountLoggedIn;
    @Nullable
    private static String jwtToken;

    // Services
    public static final AccountService accountService;
    public static final DonationService donationService;


    static {
        retrofit = new Retrofit.Builder().baseUrl(Constants.API_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        accountService = retrofit.create(AccountService.class);
        donationService = retrofit.create(DonationService.class);
    }

    /**
     * Log in with the given account and jwt
     * @param acc the account to log in
     * @param jwt the jwt currently logging in
     */
    public static void logIn(Account acc, String jwt) {
        accountLoggedIn = acc;
        jwtToken = jwt;
    }

    /**
     * Log out the currently logged in account
     */
    public static void logOut() {
        accountLoggedIn = null;
        jwtToken = null;
    }

    /**
     * Checks whether or not an account is currently logged in
     * @return true if an account is logged in
     */
    public static boolean isLoggedIn() {
        return accountLoggedIn != null;
    }

    /**
     * Gets the Account that is currently logged in
     * @return the Account currently logged in
     */
    public static Account getAccountLoggedIn() {
        return accountLoggedIn;
    }

    /**
     * Gets the UserType of the logged in account
     * @return the UserType corresponding to the logged in account, null if no user is logged in
     */
    public static UserType getLoggedInUserType() {
        assert accountLoggedIn != null;
        return (isLoggedIn())
            ? accountLoggedIn.getUserType() : null;
    }

    /**
     * Gets the current APIType of the logged in user
     * @return the current APIType of the logged in user, null if no user is logged in
     */
    public static String getCurrentUserAPIType() {
        return (isLoggedIn() ? getLoggedInUserType().getAPIType() : null);
    }

    /**
     * Gets the current permission level of the logged in user
     * @return the current permissions level of the logged in user, -1 if no user is logged in
     */
    public static int getCurrentUserPermissions() {
        return (isLoggedIn() ? getLoggedInUserType().getPermissionsLevel() : -1);
    }
    /**
     * Get the current jwt
     * @return the jwt
     */
    public static String getJwtToken() {
        return jwtToken;
    }
}
