package a5x.cs2340.donationtracker.webservice.responses.responseobjects;


import a5x.cs2340.donationtracker.DonationCategory;

public class Donation {
    private String name;
    private String description;
    private String value;
    private DonationCategory category;
    private String comments;
    private String timeStamp;

    public Donation(String name, String description, String value,
                    DonationCategory category, String comments) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.category = category;
        this.comments = comments;
    }
    public Donation(String name, String description, String value
                    , DonationCategory category) {
        this(name, description, value, category, "No comment");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DonationCategory getCategory() {
        return category;
    }
    public void setCategory(DonationCategory category) {
        this.category = category;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
