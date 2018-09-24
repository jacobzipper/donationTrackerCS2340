package a5x.cs2340.donationtracker.users;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class UserSet extends HashSet<User> {
    public boolean containsUsername(String username) {
        for (User user : this) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public User getUser(String username) {
        for (User user : this) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        throw new NoSuchElementException("User was not in the UserSet");
    }
}
