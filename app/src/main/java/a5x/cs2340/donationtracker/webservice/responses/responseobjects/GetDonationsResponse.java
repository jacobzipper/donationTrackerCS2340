package a5x.cs2340.donationtracker.webservice.responses.responseobjects;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;

/**
 * Webservice response for getting the list of donations
 */
public class GetDonationsResponse extends StandardResponse {
    private Donation[] donations;

    /**
     * Gets the list of donations
     *
     * @return the list of donations
     */
    public Donation[] getDonations() {
        return donations.clone();
    }


}
