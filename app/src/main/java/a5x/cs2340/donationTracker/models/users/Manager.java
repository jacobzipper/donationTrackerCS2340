package a5x.cs2340.donationTracker.models.users;

import android.os.Parcel;
import android.os.Parcelable;

import a5x.cs2340.donationTracker.webservice.responses.LoginResponse;

/**
 * Represents an account corresponding to a Manager
 */
public class Manager extends Account {
    private static final UserType userType = UserType.MANAGER;

    /**
     * Regular constructor and all passed parameters
     *
     * @param login        the LoginResponse corresponding to this object
     * @param username     the Admin's username
     * @param passwordHash the Admin's hashed password
     */
    public Manager(LoginResponse login, String username, String passwordHash) {
        super(login, username, passwordHash);
    }

    /**
     * Parcel constructor for Manager
     *
     * @param in the Parcel to construct from
     */
    private Manager(Parcel in) {
        super(in);
    }


    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Manager((in));
        }

        @Override
        public Account[] newArray(int size) {
            return new Manager[size];
        }
    };

    @Override
    public UserType getUserType() {
        return userType;
    }
}
