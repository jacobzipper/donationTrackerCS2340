package a5x.cs2340.donationtracker.webservice;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Enum for the type of an Account
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public enum Errors {
    ERROR_OK(0, "Everything went okay!"),

    // Registration codes
    ERROR_NULL_FIELDS_REGISTRATION(1000, "Username, password, or role is null"),
    ERROR_INVALID_ROLE(1001, "Invalid role provided"),
    ERROR_USERNAME_TAKEN(1002, "Username taken");

    private final int errNum;
    private final String desc;
    public static HashMap<Integer, Errors> errorsMap = new HashMap<>();
    Errors(int errNum, String desc) {
        this.errNum = errNum;
        this.desc = desc;
    }

    static {
        Arrays.stream(Errors.values()).forEach(errors -> errorsMap.put(errors.errNum, errors));
    }

}
