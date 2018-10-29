package a5x.cs2340.donationtracker;

/**
 * Enum representing the category the donation falls in
 */
public enum DonationCategory {
    CLOTHING("Clothing"),
    HAT("Hat"),
    KITCHEN("Kitchen"),
    ELECTRONICS("Electronics"),
    HOUSEHOLD("Household"),
    OTHER("Other"),
    NOCAT("<No Category>");
    private final String name;
    DonationCategory(String name) {
        this.name = name;
    }

    /**
     * Gets the string name of the category
     * @return the name of the category in string form
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() { return name; }
}
