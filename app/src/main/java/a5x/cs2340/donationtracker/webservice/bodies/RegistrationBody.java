package a5x.cs2340.donationtracker.webservice.bodies;

/**
 * Webservice body used for user registration
 *
 * Suppress field local and unused issue because retrofit uses these via reflect
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
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

    public String getRole() {
        return role;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
