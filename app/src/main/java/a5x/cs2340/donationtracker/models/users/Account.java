package a5x.cs2340.donationtracker.models.users;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Abstract definition of an Account, implemented differently to represent the different types of
 * accounts with different permissions
 */
public abstract class Account implements Parcelable {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String passwordHash;

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
     * Returns the type of the user, different per concrete implementation
     *
     * @return the user type of the account
     */
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
        return other == this || (other instanceof Account) && username.equals(((Account) other).username);
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
