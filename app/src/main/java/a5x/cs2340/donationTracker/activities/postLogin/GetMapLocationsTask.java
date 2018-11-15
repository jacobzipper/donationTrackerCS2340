package a5x.cs2340.donationTracker.activities.postLogin;

import java.io.IOException;

import a5x.cs2340.donationTracker.webservice.AccountService;
import a5x.cs2340.donationTracker.webservice.Webservice;
import a5x.cs2340.donationTracker.webservice.WebserviceTask;
import a5x.cs2340.donationTracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.Location;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A task to get a list of locations to put into the Google Map activity
 */
public class GetMapLocationsTask extends WebserviceTask<LocationsMapActivity, Object,
        GetLocationsResponse> {
    private Location[] locations;
    private final Webservice webservice;
    private final AccountService accountService;

    GetMapLocationsTask(LocationsMapActivity context) {
        super(context, null);
        webservice = Webservice.getInstance();
        accountService = webservice.getAccountService();
    }
    @Override
    protected Response<GetLocationsResponse> doRequest(Object body) throws IOException {
        if (webservice.isLoggedIn()) {
            Call<GetLocationsResponse> getLocationsResponseCall = accountService.locations();
            return getLocationsResponseCall.execute();
        }
        return null;
    }

    @Override
    protected void useResponse(GetLocationsResponse response) {
        locations = response.getLocations();
    }

    @Override
    protected void uiSuccess() {
        mContext.populateList(locations);
    }

    @Override
    protected void uiFailure() {

    }
}
