package a5x.cs2340.donationtracker.webservice;

import a5x.cs2340.donationtracker.Constants;
import a5x.cs2340.donationtracker.models.users.Account;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Webservice {
    private static Retrofit retrofit;

    // Logged in account information
    private static Account accountLoggedIn;
    private static String jwtToken;

    // Services
    public static AccountService accountService;
    public static DonationService donationService;


    static {
        retrofit = new Retrofit.Builder().baseUrl(Constants.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        accountService = retrofit.create(AccountService.class);
        donationService = retrofit.create(DonationService.class);
    }

    public static void logIn(Account acc, String jwt) {
        accountLoggedIn = acc;
        jwtToken = jwt;
    }

    public static void logOut() {
        accountLoggedIn = null;
        jwtToken = null;
    }

    public static boolean isLoggedIn() {
        return accountLoggedIn != null;
    }

    public static Account getAccountLoggedIn() {
        return accountLoggedIn;
    }

    public static String getJwtToken() {
        return jwtToken;
    }
}
