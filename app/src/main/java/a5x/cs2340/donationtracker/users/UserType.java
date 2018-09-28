package a5x.cs2340.donationtracker.users;

public enum UserType {
    REGULAR_USER("User"),
    ADMIN("Admin"),
    LOCATION_EMPLOYEE("Location Employee"),
    MANAGER("Manager");
    private String label;
    UserType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    @Override
    public String toString() {
        return label;
    }
}
