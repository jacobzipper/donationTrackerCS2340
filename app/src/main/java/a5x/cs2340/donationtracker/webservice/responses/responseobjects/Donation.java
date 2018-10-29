package a5x.cs2340.donationtracker.webservice.responses.responseobjects;


import a5x.cs2340.donationtracker.DonationCategory;

/**
 * Represents a Donation object
 */
public class Donation {
    private String name;
    private String shortdescription;
    private String description;
    private String value;
    private DonationCategory category;
    private String comments;
    private String tstamp;

    /**
     * Creates a new Donation with given parameters
     * @param name The name of the donation
     * @param description A description of the donation
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
        return name == null ? "<No Name>" : name;
    }

    /**
     * Setter for name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for short description
     * @return the short description
     */
    public String getShortdescription() {
        return shortdescription == null ? "<No Description>" : shortdescription;
    }

    /**
     * Setter for short description
     * @param shortdescription the new short description
     */
    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    /**
     * Getter for description
     * @return the donation description
     */
    public String getDescription() {
        return description == null ? "<No Description>" : description;
    }

    /**
     * Setter for description
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for value
     * @return the donation value
     */
    public String getValue() {
        return value == null ? "<No Value>" : value;
    }

    /**
     * Setter for value
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for category
     * @return the donation category
     */
    public DonationCategory getCategory() {
        return category == null ? DonationCategory.NOCAT : category;
    }

    /**
     * Setter for category
     * @param category the new category
     */
    public void setCategory(DonationCategory category) {
        this.category = category;
    }

    /**
     * Getter for comments
     * @return the donation comments
     */
    public String getComments() {
        return comments == null ? "<No Comments>" : comments;
    }

    /**
     * Setter for comments
     * @param comments the new comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Getter for time stamp
     * @return the donation time stamp
     */
    public String getTstamp() {
        return tstamp == null ? "<No Timestamp>" : tstamp;
    }

    /**
     * Setter for time stamp
     * @param tstamp the new time stamp
     */
    public void setTstamp(String tstamp) {
        this.tstamp = tstamp;
    }
}
