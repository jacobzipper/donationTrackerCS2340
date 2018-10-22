package a5x.cs2340.donationtracker.activities.postlogin;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import retrofit2.Response;

public class AddDonationTask extends WebserviceTask<AddDonationActivity, Object, StandardResponse> {
    public AddDonationTask(AddDonationActivity context, Object body) {
        super(context, body);
    }

    @Override
    public Response<StandardResponse> doRequest(Object body) throws IOException {
        return Webservice.donationService.addDonation((Donation) body,
                "Bearer " + Webservice.getJwtToken()).execute();
    }

    @Override
    public void useResponse(StandardResponse response) {

    }

    @Override
    public void uiSuccess() {

    }

    @Override
    public void uiFailure() {

    }

}
