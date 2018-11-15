package a5x.cs2340.donationTracker.models.users;

import android.os.Parcel;
import android.os.Parcelable;

import a5x.cs2340.donationTracker.webservice.responses.LoginResponse;

/**
 * Represents an Account corresponding to Location Employees
 */
public class LocationEmployee extends Account {
    private static final UserType userType = UserType.LOCATION_EMPLOYEE;

    /**
     * Regular constructor and all passed parameters
     *
     * @param login        the LoginResponse corresponding to this object
     * @param username     the Admin's username
     * @param passwordHash the Admin's hashed password
     */
    public LocationEmployee(LoginResponse login, String username, String passwordHash) {
        super(login, username, passwordHash);
    }

    /**
     * Parcel constructor for Admin
     *
     * @param in the Parcel to construct from
     */
    private LocationEmployee(Parcel in) {
        super(in);
    }


    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new LocationEmployee((in));
        }

        @Override
        public Account[] newArray(int size) {
            return new LocationEmployee[size];
        }
    };

    @Override
    public UserType getUserType() {
        return userType;
    }
}
