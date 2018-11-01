package a5x.cs2340.donationtracker.webservice.responses.responseobjects;


import a5x.cs2340.donationtracker.DonationCategory;

/**
 * Represents a Donation object
 */
public class Donation {
    private final String name;
    private final String shortdescription;
    private final String description;
    private final String value;
    private final DonationCategory category;
    private final String comments;
    private String tstamp;

    /**
     * Creates a new Donation with given parameters
     * @param name The name of the donation
     * @param shortdescription A short description of the donation
     * @param description A full description of the donation
     * @param value The estimated value of the donation (in dollars)
     * @param category The category the donation falls in
     * @param comments Non-empty comments about the donation
     */
    public Donation(String name, String shortdescription, String description, String value,
                    DonationCategory category, String comments) {
        this.name = name;
        this.shortdescription = shortdescription;
        this.description = description;
        this.value = value;
        this.category = category;
        this.comments = comments;
    }

    /**
     * Creates a new Donation with no comments
     * @param name The name of the donation
     * @param shortdescription A short description of the donation
     * @param description A description of the donation
     * @param value The estimated value of the donation (in dollars)
     * @param category The category the donation falls in
     */
    public Donation(String name, String shortdescription, String description, String value
                    , DonationCategory category) {
        this(name, shortdescription, description, value, category, "No comment");
    }

    /**
     * Getter for name
     * @return the donation name
     */
    public String getName() {
        return (name == null) ? "<No Name>" : name;
    }

    /**
     * Getter for short description
     * @return the short description
     */
    public String getShortdescription() {
        return (shortdescription == null) ? "<No Description>" : shortdescription;
    }

    /**
     * Getter for description
     * @return the donation description
     */
    public String getDescription() {
        return (description == null) ? "<No Description>" : description;
    }

    /**
     * Getter for value
     * @return the donation value
     */
    public String getValue() {
        return (value == null) ? "<No Value>" : value;
    }

    /**
     * Getter for category
     * @return the donation category
     */
    public DonationCategory getCategory() {
        return (category == null) ? DonationCategory.NOCAT : category;
    }

    /**
     * Getter for comments
     * @return the donation comments
     */
    public String getComments() {
        return (comments == null) ? "<No Comments>" : comments;
    }

    /**
     * Getter for time stamp
     * @return the donation time stamp
     */
    public String getTstamp() {
        return (tstamp == null) ? "<No Timestamp>" : tstamp;
    }

}
