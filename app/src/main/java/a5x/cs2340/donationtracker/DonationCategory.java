package a5x.cs2340.donationtracker;

public enum DonationCategory {
    CLOTHING("Clothing"),
    HAT("Hat"),
    KITCHEN("Kitchen"),
    ELECTRONICS("Electronics"),
    HOUSEHOLD("Household"),
    OTHER("Other");
    private String name;
    DonationCategory(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
