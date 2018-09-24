package a5x.cs2340.donationtracker.users;

import android.os.Parcel;
import android.os.Parcelable;

public class RegularUser extends User {
    /**
     * Regular constructor and all passed parameters
     *
     * @param firstName the RegularUser's first name
     * @param lastName the RegularUser's last name
     * @param username the RegularUser's username
     * @param passwordHash the RegularUser's hashed password
     */
    public RegularUser(String firstName, String lastName, String username, String passwordHash) {
        super(firstName, lastName, username, passwordHash);
    }

    /**
     * Parcel constructor for RegularUser
     *
     * @param in the Parcel to construct from
     */
    private RegularUser(Parcel in) {
        super(in);
    }


    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new RegularUser((in));
        }
        public User[] newArray(int size) {
            return new RegularUser[size];
        }
    };
}
