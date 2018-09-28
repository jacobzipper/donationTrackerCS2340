package a5x.cs2340.donationtracker.users;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class UserSet extends HashSet<Account> {
    /**
     * Checks if a account with the passed username is in the UserSet
     *
     * @param username the username to check
     * @return true if a account with a matching username is in the UserSet
     */
    public boolean containsUsername(String username) {
        for (Account account : this) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a account with the same username as the passed in username
     *
     * @param username the username of the account to find
     * @return a account with username equal to what was passed in
     */
    public Account getUser(String username) {
        for (Account account : this) {
            if (username.equals(account.getUsername())) {
                return account;
            }
        }
        throw new NoSuchElementException("Account was not in the UserSet");
    }
}
