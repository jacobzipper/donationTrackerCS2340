package a5x.cs2340.donationtracker.activities.postlogin;

import java.io.IOException;
import java.util.List;

import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Response;

public class GetMapLocationsTask extends WebserviceTask<LocationsMapActivity, Object,
        GetLocationsResponse> {
    private List<Location> locations;

    public GetMapLocationsTask(LocationsMapActivity context, Object body) {
        super(context, body);
    }
    @Override
    protected Response<GetLocationsResponse> doRequest(Object body) throws IOException {
        return Webservice.accountService.locations().execute();
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
