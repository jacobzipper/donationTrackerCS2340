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
