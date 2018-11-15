package a5x.cs2340.donationTracker.webservice.bodies;

/**
 * Webservice body used for user registration
 */
public class RegistrationBody extends LoginBody {
    private final String role;
    private final String firstname;
    private final String lastname;
    /**
     * Standard constructor from all parameters
     * @param username username to register
     * @param password password to register
     * @param role the role of the user
     * @param firstname first name of the user
     * @param lastname last name of the user
     */
    public RegistrationBody(String username, String password, String role,
                            String firstname, String lastname) {
        super(username, password);
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * Getter for account role corresponding to this registration
     * @return role of the account
     */
    public String getRole() {
        return role;
    }

    /**
     * Getter for account first name corresponding to registration
     * Suppress unused because retrofit
     * @return first name of this account
     */
    @SuppressWarnings("unused")
    public String getFirstname() {
        return firstname;
    }

    /**
     * Getter for account last name corresponding to registration
     * Suppress unused because retrofit
     * @return last name of this account
     */
    @SuppressWarnings("unused")
    public String getLastname() {
        return lastname;
    }
}
