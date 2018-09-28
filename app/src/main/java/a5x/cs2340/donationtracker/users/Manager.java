package a5x.cs2340.donationtracker.users;

import android.os.Parcel;
import android.os.Parcelable;

public class Manager extends Account {
    private static UserType userType = UserType.MANAGER;
    /**
     * Regular constructor and all passed parameters
     *
     * @param firstName the Manager's first name
     * @param lastName the Manager's last name
     * @param username the Manager's username
     * @param passwordHash the Manager's hashed password
     */
    public Manager(String firstName, String lastName, String username, String passwordHash) {
        super(firstName, lastName, username, passwordHash);
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
        public Account createFromParcel(Parcel in) {
            return new Manager((in));
        }
        public Account[] newArray(int size) {
            return new Manager[size];
        }
    };
    public UserType getUserType() {
        return userType;
    }
}
