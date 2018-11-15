package a5x.cs2340.donationTracker.webservice.responses;

/**
 * Represents the response after a login attempt
 */
public class LoginResponse extends StandardResponse {
    private final String role;
    private final String firstname;
    private final String lastname;
    private final String jwt;

    /**
     * Constructor for login response
     *
     * Suppress too many parameters because it is how it is
     *
     * Suppress unused because used by retrofit
     *
     * @param error the error
     * @param msg the message
     * @param role role for user
     * @param firstname first name
     * @param lastname last name
     * @param jwt user's jwt
     */
    @SuppressWarnings({"ConstructorWithTooManyParameters", "unused"})
    public LoginResponse(int error, String msg, String role, String firstname,
                         String lastname, String jwt) {
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
     * Accessor for first name
     * @return the first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Accessor for last name
     * @return the last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Accessor for jwt
     * @return the jwt
     */
    public String getJwt() {
        return jwt;
    }

}
