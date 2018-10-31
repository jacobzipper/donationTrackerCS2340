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
     * @return the UserType corresponding to the logged in account
     */
    public static UserType getLoggedInUserType() {
        return (isLoggedIn())
            ? accountLoggedIn.getUserType() : null;
    }

    /**
     * Get the current jwt
     * @return the jwt
     */
    public static String getJwtToken() {
        return jwtToken;
    }
}
