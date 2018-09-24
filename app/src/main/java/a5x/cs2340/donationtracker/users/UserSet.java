package a5x.cs2340.donationtracker.users;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class UserSet extends HashSet<User> {
    /**
     * Checks if a user with the passed username is in the UserSet
     *
     * @param username the username to check
     * @return true if a user with a matching username is in the UserSet
     */
    public boolean containsUsername(String username) {
        for (User user : this) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a user with the same username as the passed in username
     *
     * @param username the username of the user to find
     * @return a user with username equal to what was passed in
     */
    public User getUser(String username) {
        for (User user : this) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        throw new NoSuchElementException("User was not in the UserSet");
    }
}
