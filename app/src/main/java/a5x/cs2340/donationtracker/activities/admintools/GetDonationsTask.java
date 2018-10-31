package a5x.cs2340.donationtracker.activities.admintools;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.GetDonationsResponse;
import retrofit2.Response;

/**
 * Webservice task for getting the list of donations
 */
public class GetDonationsTask extends WebserviceTask<ViewDonationsActivity,
        Object, GetDonationsResponse> {
    private List<Donation> donations;
    private List<String> donationSDescriptions;

    /**
     * Constructor for GetDonationsTask
     * @param context the Activity to use as context
     * @param body the body for the task (usually null)
     */
    public GetDonationsTask(ViewDonationsActivity context, Object body) {
        super(context, body);
    }
    @Override
    public Response<GetDonationsResponse> doRequest(Object body) throws IOException {
        return Webservice.donationService.getDonations(
                Webservice.getLoggedInUserType().getAPIType(),
                "Bearer " + Webservice.getJwtToken()).execute();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void useResponse(GetDonationsResponse response) {
        donations = response.getDonations();
        donationSDescriptions = donations.stream().map(Donation::getShortdescription).collect(Collectors.toList());
    }

    @Override
    public void uiSuccess() {
        mContext.updateListView(donations, donationSDescriptions);
        mContext.switchToMakingSearch();
    }

    @Override
    public void uiFailure() {

    }
}
