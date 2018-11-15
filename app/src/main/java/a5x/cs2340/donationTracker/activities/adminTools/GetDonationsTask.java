package a5x.cs2340.donationTracker.activities.adminTools;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a5x.cs2340.donationTracker.webservice.DonationService;
import a5x.cs2340.donationTracker.webservice.Webservice;
import a5x.cs2340.donationTracker.webservice.WebserviceTask;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.Donation;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.GetDonationsResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Webservice task for getting the list of donations
 */
public class GetDonationsTask extends WebserviceTask<ViewDonationsActivity,
        Object, GetDonationsResponse> {
    private Donation[] donations;
    private List<String> donationSDescriptions;
    private final Webservice webservice;
    private final DonationService donationService;

    /**
     * Constructor for GetDonationsTask
     * @param context the Activity to use as context
     *
     */
    GetDonationsTask(ViewDonationsActivity context) {
        super(context, null);
        webservice = Webservice.getInstance();
        donationService = webservice.getDonationService();
    }
    @Override
    public Response<GetDonationsResponse> doRequest(Object body) throws IOException {
        Call<GetDonationsResponse> getDonationsResponseCall = donationService.getDonations(
                webservice.getCurrentUserAPIType(),
                "Bearer " + webservice.getJwtToken());
        return getDonationsResponseCall.execute();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void useResponse(GetDonationsResponse response) {
        donations = response.getDonations();
        Stream<Donation> donationStream = Arrays.stream(donations);
        Stream<String> shortDescriptionStream = donationStream.map(Donation::getShortDescription);
        donationSDescriptions = shortDescriptionStream.collect(Collectors.toList());
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
