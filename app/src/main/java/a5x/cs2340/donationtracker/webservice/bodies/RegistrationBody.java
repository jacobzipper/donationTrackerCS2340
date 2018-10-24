package a5x.cs2340.donationtracker.webservice.bodies;

/**
 * Webservice body used for user registration
 */
public class RegistrationBody extends LoginBody {
    private String role;
    private String firstname;
    private String lastname;

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
     * Getter for role
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role
     * @param role the new role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Getter for first name
     * @return the first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter for first name
     * @param firstname the new first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for last name
     * @return the last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter for last name
     * @param lastname the new last name
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
