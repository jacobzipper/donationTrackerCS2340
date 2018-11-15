package a5x.cs2340.donationTracker.webservice;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Enum for the type of an Account
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public enum Errors {
    ERROR_OK(0),

    // Registration codes
    ERROR_NULL_FIELDS_REGISTRATION(1000),
    ERROR_INVALID_ROLE(1001),
    ERROR_USERNAME_TAKEN(1002);

    private final int errNum;

    // Needs to be hashMap to fit existing code
    @SuppressLint("UseSparseArrays")
    public static final HashMap<Integer, Errors> errorsMap = new HashMap<>();
    Errors(int errNum) {
        this.errNum = errNum;
    }

    static {
        Stream<Errors> stream = Arrays.stream(Errors.values());
        stream.forEach(errors -> errorsMap.put(errors.errNum, errors));
    }

}
