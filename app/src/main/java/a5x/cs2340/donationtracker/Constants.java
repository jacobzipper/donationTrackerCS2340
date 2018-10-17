package a5x.cs2340.donationtracker;

import java.math.BigDecimal;

public class Constants {
    public static final int MIN_PASSWORD_LENGTH = 4;
    public static final int MIN_USERNAME_LENGTH = 1;
    public static final int AUTHENTICATION_UPPER_BOUND = 100000;
    public static final BigDecimal VERY_WEAK_GUESSES = new BigDecimal("1000000");
    public static final BigDecimal WEAK_GUESSES = new BigDecimal("100000000");
    public static final BigDecimal AVERAGE_GUESSES = new BigDecimal("1000000000");
    public static final BigDecimal STRONG_GUESSES = new BigDecimal("10000000000");
    public static final int REGISTRATION_PASSWORD_CHECK_DELAY = 750;

    public static final String API_URL = "https://donationtrackerzipper.herokuapp.com";

    //public static final BigDecimal VERY_STRONG_GUESSES = new BigDecimal("100000000000");
}
