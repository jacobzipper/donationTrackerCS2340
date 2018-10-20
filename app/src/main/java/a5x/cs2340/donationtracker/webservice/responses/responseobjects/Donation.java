package a5x.cs2340.donationtracker.webservice.responses.responseobjects;


import a5x.cs2340.donationtracker.DonationCategory;

public class Donation {
    private String name;
    private String description;
    private double value;
    private DonationCategory category;
    private String comments;

    public Donation(String name, String description, double value,
                    DonationCategory category, String comments) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.category = category;
        this.comments = comments;
    }
    public Donation(String name, String description, double value
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
}
