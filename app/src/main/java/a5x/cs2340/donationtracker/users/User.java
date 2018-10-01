package a5x.cs2340.donationtracker.users;

import android.os.Parcel;
import android.os.Parcelable;

public class User extends Account {
    private static UserType userType = UserType.REGULAR_USER;

    /**
     * Regular constructor and all passed parameters
     *
     * @param firstName    the User's first name
     * @param lastName     the User's last name
     * @param username     the User's username
     * @param passwordHash the User's hashed password
     */
    public User(String firstName, String lastName, String username, String passwordHash) {
        super(firstName, lastName, username, passwordHash);
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
        public Account createFromParcel(Parcel in) {
            return new User((in));
        }

        public Account[] newArray(int size) {
            return new User[size];
        }
    };

    public UserType getUserType() {
        return userType;
    }
}
