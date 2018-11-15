package a5x.cs2340.donationTracker.webservice.bodies;

/**
 * Webservice body used for user registration
 */
public class RegistrationBody extends LoginBody {
    private final String role;
    private final String firstName;
    private final String lastName;
    /**
     * Standard constructor from all parameters
     * @param username username to register
     * @param password password to register
     * @param role the role of the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     */
    public RegistrationBody(String username, String password, String role,
                            String firstName, String lastName) {
        super(username, password);
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
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
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for account last name corresponding to registration
     * Suppress unused because retrofit
     * @return last name of this account
     */
    @SuppressWarnings("unused")
    public String getLastName() {
        return lastName;
    }
}
