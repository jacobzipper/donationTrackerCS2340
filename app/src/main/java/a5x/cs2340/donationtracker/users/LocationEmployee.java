package a5x.cs2340.donationtracker.users;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationEmployee extends User {
    private static UserType userType = UserType.LOCATION_EMPLOYEE;
    /**
     * Regular constructor and all passed parameters
     *
     * @param firstName the LocationEmployee's first name
     * @param lastName the LocationEmployee's last name
     * @param username the LocationEmployee's username
     * @param passwordHash the LocationEmployee's hashed password
     */
    public LocationEmployee(String firstName, String lastName, String username, String passwordHash) {
        super(firstName, lastName, username, passwordHash);
    }

    /**
     * Parcel constructor for Admin
     *
     * @param in the Parcel to construct from
     */
    private LocationEmployee(Parcel in) {
        super(in);
    }


    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new LocationEmployee((in));
        }
        public User[] newArray(int size) {
            return new LocationEmployee[size];
        }
    };
    public UserType getUserType() {
        return userType;
    }
}
