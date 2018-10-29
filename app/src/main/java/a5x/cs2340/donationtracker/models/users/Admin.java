package a5x.cs2340.donationtracker.models.users;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents an Administrator for the app
 */
public class Admin extends Account {
    private static final UserType userType = UserType.ADMIN;

    /**
     * Regular constructor and all passed parameters
     *
     * @param firstName    the Admin's first name
     * @param lastName     the Admin's last name
     * @param username     the Admin's username
     * @param passwordHash the Admin's hashed password
     */
    public Admin(String firstName, String lastName, String username, String passwordHash) {
        super(firstName, lastName, username, passwordHash);
    }

    /**
     * Parcel constructor for Admin
     *
     * @param in the Parcel to construct from
     */
    private Admin(Parcel in) {
        super(in);
    }


    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Admin((in));
        }

        @Override
        public Account[] newArray(int size) {
            return new Admin[size];
        }
    };

    @Override
    public UserType getUserType() {
        return userType;
    }

}
