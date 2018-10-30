package a5x.cs2340.donationtracker.models.users;

/**
 * Enum for the type of an Account
 */
public enum UserType {
    REGULAR_USER("User", "users", 1),
    LOCATION_EMPLOYEE("Location Employee", "employees", 2),
    MANAGER("Manager", "managers", 3),
    ADMIN("Admin", "admins", 4);
    private final String label;
    private final String apiType;
    private final int permissionsLevel;
    UserType(String label, String apiType, int permissionsLevel) {
        this.label = label;
        this.apiType = apiType;
        this.permissionsLevel = permissionsLevel;
    }

    /**
     * Getter for the label string
     * @return the label string corresponding to the UserType
     */
    public String getLabel() {
        return label;
    }

    /**
     * Getter for the permissions level
     * @return the permissions level corresponding to the UserType
     */
    public int getPermissionsLevel() {
        return permissionsLevel;
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
