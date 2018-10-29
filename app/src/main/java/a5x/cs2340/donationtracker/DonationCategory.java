package a5x.cs2340.donationtracker;

import com.google.gson.annotations.SerializedName;

/**
 * Enum representing the category the donation falls in
 */
public enum DonationCategory {
    @SerializedName("Clothing")
    CLOTHING("Clothing"),
    @SerializedName("Hat")
    HAT("Hat"),
    @SerializedName("Kitchen")
    KITCHEN("Kitchen"),
    @SerializedName("Electronics")
    ELECTRONICS("Electronics"),
    @SerializedName("Household")
    HOUSEHOLD("Household"),
    @SerializedName("Other")
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
