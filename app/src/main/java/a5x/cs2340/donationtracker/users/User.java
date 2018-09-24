package a5x.cs2340.donationtracker.users;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class User implements Parcelable{
    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    public User(String firstName, String lastName, String username, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getUsername() {
        return username;
    }
    public boolean checkPassword(String passwordHash) {
        return this.passwordHash.equals(passwordHash);
    }
    public String getName() {
        return firstName + " " + lastName;
    }
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        return other instanceof User && username.equals(((User) other).username);
    }
    @Override
    public int hashCode() {
        return username.hashCode();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(username);
        out.writeString(passwordHash);
    }
    public User(Parcel parcel) {
        firstName = parcel.readString();
        lastName = parcel.readString();
        username = parcel.readString();
        passwordHash = parcel.readString();
    }

}
