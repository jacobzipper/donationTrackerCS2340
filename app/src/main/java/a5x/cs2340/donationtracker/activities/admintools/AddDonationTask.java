package a5x.cs2340.donationtracker.activities.admintools;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.DonationService;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.StandardResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Webservice task for adding new donations
 */
public class AddDonationTask extends WebserviceTask<AddDonationActivity, Object, StandardResponse> {
    private final Webservice webservice;
    private final DonationService donationService;
    /**
     * Creates a new AddDonationTask with the given context and body
     * @param context the Activity to use as the context for this task
     * @param body the body to use for the task (should be the Donation object)
     */
    AddDonationTask(AddDonationActivity context, Object body) {
        super(context, body);
        webservice = Webservice.getInstance();
        donationService = webservice.getDonationService();
    }

    @Override
    public Response<StandardResponse> doRequest(Object body) throws IOException {
        Call<StandardResponse> standardResponseCall = donationService.
                addDonation(webservice.getCurrentUserAPIType(),
                (Donation) body, "Bearer " + webservice.getJwtToken());
        return standardResponseCall.execute();
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
