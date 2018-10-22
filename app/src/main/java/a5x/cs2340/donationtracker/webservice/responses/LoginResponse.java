package a5x.cs2340.donationtracker.webservice.responses;

public class LoginResponse extends StandardResponse {
    private String role;
    private String firstname;
    private String lastname;
    private String jwt;

    public LoginResponse(int error, String msg, String role,
                         String firstname, String lastname, String jwt) {
        super(error, msg);
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jwt = jwt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
