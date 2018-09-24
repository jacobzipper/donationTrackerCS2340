package a5x.cs2340.donationtracker.users;

import android.os.Parcel;
import android.os.Parcelable;

public class RegularUser extends User {
    public RegularUser(String firstName, String lastName, String username, String passwordHash) {
        super(firstName, lastName, username, passwordHash);
    }
    public RegularUser(Parcel in) {
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
