package a5x.cs2340.donationtracker.webservice.responses.responseobjects;

import java.util.List;

import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;

public class GetDonationsResponse extends StandardResponse {
    private List<Donation> donations;
    public GetDonationsResponse(int error, String msg, List<Donation> donations) {
        super(error, msg);
        this.donations = donations;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}
