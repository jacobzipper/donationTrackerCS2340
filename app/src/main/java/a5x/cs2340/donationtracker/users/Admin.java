package a5x.cs2340.donationtracker.users;

import android.os.Parcel;
import android.os.Parcelable;

public class Admin extends Account {
    private static UserType userType = UserType.ADMIN;
    /**
     * Regular constructor and all passed parameters
     *
     * @param firstName the Admin's first name
     * @param lastName the Admin's last name
     * @param username the Admin's username
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
        public Account createFromParcel(Parcel in) {
            return new Admin((in));
        }
        public Account[] newArray(int size) {
            return new Admin[size];
        }
    };
    public UserType getUserType() {
        return userType;
    }

}
