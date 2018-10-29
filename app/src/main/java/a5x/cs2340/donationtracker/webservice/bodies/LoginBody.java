package a5x.cs2340.donationtracker.webservice.bodies;

/**
 * Represents a webservice body for handling login
 */
public class LoginBody {
    private String username;
    private String password;

    /**
     * Creates a login body with given parameters
     * @param username the username to attempt to login with
     * @param password the password to attempt to login with
     */
    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
