package a5x.cs2340.donationtracker.activities.postlogin;

import java.io.IOException;

import a5x.cs2340.donationtracker.webservice.AccountService;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A task to get a list of locations to put into the Google Map activity
 */
public class GetMapLocationsTask extends WebserviceTask<LocationsMapActivity, Object,
        GetLocationsResponse> {
    private Location[] locations;
    private final AccountService accountService;

    GetMapLocationsTask(LocationsMapActivity context) {
        super(context, null);
        Webservice webservice = Webservice.getInstance();
        accountService = webservice.getAccountService();
    }
    @Override
    protected Response<GetLocationsResponse> doRequest(Object body) throws IOException {
        Call<GetLocationsResponse> getLocationsResponseCall = accountService.locations();
        return getLocationsResponseCall.execute();
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
