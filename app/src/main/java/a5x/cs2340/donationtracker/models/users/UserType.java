package a5x.cs2340.donationtracker.models.users;

/**
 * Enum for the type of an Account
 */
public enum UserType {
    REGULAR_USER("User", "users"),
    ADMIN("Admin", "admins"),
    LOCATION_EMPLOYEE("Location Employee", "employees"),
    MANAGER("Manager", "managers");
    private final String label;
    private final String apiType;
    UserType(String label, String apiType) {
        this.label = label;
        this.apiType = apiType;
    }

    /**
     * Getter for the label string
     * @return the label string corresponding to the UserType
     */
    public String getLabel() {
        return label;
    }

    /**
     * Getter for the API type of the user
     * @return the API type corresponding to the UserType
     */
    public String getAPIType() {
        return apiType;
    }

    @Override
    public String toString() {
        return label;
    }
}
