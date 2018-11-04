package a5x.cs2340.donationtracker.activities.admintools;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import a5x.cs2340.donationtracker.webservice.DonationService;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.bodies.SearchDonationsMap;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Donation;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.GetDonationsResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Webservice task for getting the list of donations
 */
public class SearchDonationsTask extends WebserviceTask<ViewDonationsActivity,
        SearchDonationsMap, GetDonationsResponse> {
    private Donation[] donations;
    private List<String> donationSDescriptions;
    private final Webservice webservice;
    private final DonationService donationService;
    /**
     * Constructor for GetDonationsTask
     * @param context the Activity to use as context
     * @param body the body for the task (usually null)
     */
    SearchDonationsTask(ViewDonationsActivity context, SearchDonationsMap body) {
        super(context, body);
        webservice = Webservice.getInstance();
        donationService = webservice.getDonationService();
    }

    /**
     * NOTE: BODY CANNOT BE NULL
     * Do new SearchDonationsMap(null, null, null) if the user doesn't enter anything
     * Empty string should count as null
     */
    @Override
    public Response<GetDonationsResponse> doRequest(SearchDonationsMap body) throws IOException {
        Call<GetDonationsResponse> getDonationsResponseCall = donationService.searchDonations(
                webservice.getCurrentUserAPIType(),
                "Bearer " + webservice.getJwtToken(), body);
        return getDonationsResponseCall.execute();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void useResponse(GetDonationsResponse response) {
        Log.d("Searching", "Using response");
        donations = response.getDonations();
        donationSDescriptions = Arrays.stream(donations).
                map(Donation::getShortdescription).collect(Collectors.toList());
    }

    @Override
    public void uiSuccess() {
        Log.d("Searching", "Call Success");
        mContext.updateListView(donations, donationSDescriptions);
        mContext.switchToClearingSearch();
    }

    @Override
    public void uiFailure() {
        Log.d("Searching", "Call Failure");
    }
}