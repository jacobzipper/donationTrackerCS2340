package a5x.cs2340.donationtracker.models.users;

import android.os.Parcel;
import android.os.Parcelable;

import a5x.cs2340.donationtracker.webservice.responses.LoginResponse;

/**
 * Represents a regular user with no special privileges
 */
public class User extends Account {
    private static final UserType userType = UserType.REGULAR_USER;

    /**
     * Regular constructor and all passed parameters
     *
     * @param login        the LoginResponse corresponding to this object
     * @param username     the Admin's username
     * @param passwordHash the Admin's hashed password
     */
    public User(LoginResponse login, String username, String passwordHash) {
        super(login, username, passwordHash);
    }

    /**
     * Parcel constructor for User
     *
     * @param in the Parcel to construct from
     */
    private User(Parcel in) {
        super(in);
    }


    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new User((in));
        }

        @Override
        public Account[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public UserType getUserType() {
        return userType;
    }
}
