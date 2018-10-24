package a5x.cs2340.donationtracker.webservice.responses;

/**
 * Represents the response after a login attempt
 */
public class LoginResponse extends StandardResponse {
    private String role;
    private String firstname;
    private String lastname;
    private String jwt;

    /**
     * Creates a new LoginResponse with given parameters
     * @param error the error code
     * @param msg the error message
     * @param role the role of the user
     * @param firstname the user's firstname
     * @param lastname the user's lastname
     * @param jwt the jwt of the user
     */
    public LoginResponse(int error, String msg, String role,
                         String firstname, String lastname, String jwt) {
        super(error, msg);
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jwt = jwt;
    }

    /**
     * Accessor for role
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Mutator for role
     * @param role the new role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Accessor for first name
     * @return the first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Mutator for first name
     * @param firstName the new first name
     */
    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    /**
     * Accessor for last name
     * @return the last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Mutator for last name
     * @param lastname the new last name
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Accessor for jwt
     * @return the jwt
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * Mutator for jwt
     * @param jwt the jwt
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
