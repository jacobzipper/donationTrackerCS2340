package a5x.cs2340.donationtracker.webservice.responses;

/**
 * Represents the response after a login attempt
 */
public class LoginResponse extends StandardResponse {
    private final String role;
    private final String firstname;
    private final String lastname;
    private final String jwt;

    public LoginResponse(int error, String msg, String role, String firstname, String lastname, String jwt) {
        super(error, msg);
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jwt = jwt;
    }

    /**
     * Accessor for role
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Accessor for first name
     *
     * @return the first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Accessor for last name
     *
     * @return the last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Accessor for jwt
     *
     * @return the jwt
     */
    public String getJwt() {
        return jwt;
    }

}
