package a5x.cs2340.donationTracker.webservice.responses.responseobjects;


import a5x.cs2340.donationTracker.DonationCategory;

/**
 * Represents a Donation object
 */
public class Donation {
    private final String name;
    private final String shortDescription;
    private final String description;
    private final String value;
    private final DonationCategory category;
    private String comments = "No comments";

    // Suppress spelling because same spelling in api
    // Suppress unused because retrofit
    @SuppressWarnings({"SpellCheckingInspection", "unused"})
    private String tstamp;


    /**
     * Creates a new Donation with no comments
     * @param name The name of the donation
     * @param shortDescription A short description of the donation
     * @param description A description of the donation
     * @param value The estimated value of the donation (in dollars)
     * @param category The category the donation falls in
     */
    public Donation(CharSequence name, CharSequence shortDescription,
                    CharSequence description, CharSequence value
                    , DonationCategory category) {
        this.name = name.toString();
        this.shortDescription = shortDescription.toString();
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
    public String getShortDescription() {
        return (shortDescription == null) ? "<No Description>" : shortDescription;
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
     *
     * Suppress spelling because same spelling in api
     *
     * @return the donation time stamp
     */
    @SuppressWarnings("SpellCheckingInspection")
    public String getTstamp() {
        return (tstamp == null) ? "<No Timestamp>" : tstamp;
    }

}
