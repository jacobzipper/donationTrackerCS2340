package a5x.cs2340.donationtracker.webservice.responses.responseobjects;

import java.util.List;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;

/**
 * Webservice response for getting the list of donations
 */
public class GetDonationsResponse extends StandardResponse {
    private List<Donation> donations;

    /**
     * Creates this response with given parameters
     * @param error the error code
     * @param msg the error message
     * @param donations the donations list returned
     */
    public GetDonationsResponse(int error, String msg, List<Donation> donations) {
        super(error, msg);
        this.donations = donations;
    }

    /**
     * Gets the list of donations
     * @return the list of donations
     */
    public List<Donation> getDonations() {
        return donations;
    }

    /**
     * Sets the list of donations
     * @param donations the new list of donations
     */
    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}
