package a5x.cs2340.donationtracker.webservice.responses.responseobjects;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;

/**
 * Webservice response for getting the list of donations
 */
public class GetDonationsResponse extends StandardResponse {
    private final Donation[] donations;

    public GetDonationsResponse(int error, String msg, Donation[] donations) {
        super(error, msg);
        this.donations = donations.clone();
    }

    /**
     * Gets the list of donations
     *
     * @return the list of donations
     */
    public Donation[] getDonations() {
        return donations.clone();
    }


}
