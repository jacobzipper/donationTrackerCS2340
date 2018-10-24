package a5x.cs2340.donationtracker.webservice.bodies;

public class RegistrationBody extends LoginBody {
    private String role;
    private String firstname;
    private String lastname;

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

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
