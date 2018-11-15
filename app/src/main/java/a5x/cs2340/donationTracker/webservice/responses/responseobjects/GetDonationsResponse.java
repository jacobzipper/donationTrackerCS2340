package a5x.cs2340.donationTracker.webservice.responses.responseobjects;

import android.support.annotation.Nullable;

import a5x.cs2340.donationTracker.webservice.responses.StandardResponse;

/**
 * Webservice response for getting the list of donations
 */
public class GetDonationsResponse extends StandardResponse {
    private final Donation[] donations;

    /**
     * Constructor for get donations response
     * Suppress unused because retrofit
     * @param error error code
     * @param msg message describing error
     * @param donations list of donations
     */
    @SuppressWarnings("unused")
    public GetDonationsResponse(int error, @Nullable String msg, Donation[] donations) {
        super(error, msg);
        this.donations = donations.clone();
    }

    /**
     * Gets the list of donations
     * @return the list of donations
     */
    public Donation[] getDonations() {
        return donations.clone();
    }


}
