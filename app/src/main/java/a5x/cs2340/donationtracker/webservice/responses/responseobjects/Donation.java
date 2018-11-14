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
    private String comments = "No comments";
    private String tstamp;


    /**
     * Creates a new Donation with no comments
     * @param name The name of the donation
     * @param shortdescription A short description of the donation
     * @param description A description of the donation
     * @param value The estimated value of the donation (in dollars)
     * @param category The category the donation falls in
     */
    public Donation(CharSequence name, CharSequence shortdescription,
                    CharSequence description, CharSequence value
            , DonationCategory category) {
        this.name = name.toString();
        this.shortdescription = shortdescription.toString();
        this.description = description.toString();
        this.value = value.toString();
        this.category = category;
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
     * Get the name of the donation's category
     * @return the name of the donation's category, null if the category is null
     */
    public String getCategoryName() {
        return ((category == null) ? null : category.toString());
    }
    /**
     * Getter for comments
     * @return the donation comments
     */
    public String getComments() {
        return (comments == null) ? "<No Comments>" : comments;
    }

    /**
     * Setter for comments
     * @param comments the new comments
     */
    public void setComments(CharSequence comments) {
        this.comments = comments.toString();
    }

    /**
     * Getter for time stamp
     * @return the donation time stamp
     */
    public String getTstamp() {
        return (tstamp == null) ? "<No Timestamp>" : tstamp;
    }

}
