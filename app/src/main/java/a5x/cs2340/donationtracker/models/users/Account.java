package a5x.cs2340.donationtracker.models.users;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Account implements Parcelable {
    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;

    /**
     * Regular constructor and all passed parameters
     *
     * @param firstName    the Account's first name
     * @param lastName     the Account's last name
     * @param username     the Account's username
     * @param passwordHash the Account's hashed password
     */
    Account(String firstName, String lastName, String username, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * Accessor for first name
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Accessor for last name
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Accessor for username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Checks if the hashed password passed in matches the stored hash
     *
     * @param passwordHash the pasesd in hash to check
     * @return true if the password hashes match
     */
    public boolean checkPassword(String passwordHash) {
        return this.passwordHash.equals(passwordHash);
    }

    public abstract UserType getUserType();

    /**
     * Returns a concatenation of first name and last name
     *
     * @return firstName and lastName concatenated with a space in between
     */
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
        return other instanceof Account && username.equals(((Account) other).username);
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

    /**
     * Constructor from Parcel
     *
     * @param parcel the parcel to build the Account from
     */
    Account(Parcel parcel) {
        firstName = parcel.readString();
        lastName = parcel.readString();
        username = parcel.readString();
        passwordHash = parcel.readString();
    }

}
