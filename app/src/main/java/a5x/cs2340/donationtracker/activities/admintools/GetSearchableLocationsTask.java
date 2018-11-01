package a5x.cs2340.donationtracker.activities.admintools;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Response;

/**
 * Task to get a list of location names specifically for searching.
 * Sets a showable and searchable list location names to the context
 * The showable list has "Any" in index 0, and the searchable list has null in index 0
 * All other indicies are identical, and contain the names of all locations
 */
public class GetSearchableLocationsTask extends WebserviceTask<ViewDonationsActivity,
        Object, GetLocationsResponse> {
    private List<String> locationsShowableList;
    private List<String> locationsSearchableList;

    GetSearchableLocationsTask(ViewDonationsActivity context, Object body) {super(context, body);}
    @Override
    protected Response<GetLocationsResponse> doRequest(Object body) throws IOException {
        return Webservice.accountService.locations().execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void useResponse(GetLocationsResponse response) {
        locationsSearchableList = response.getLocations().stream().
                map(Location::getName).collect(Collectors.toList());
        locationsSearchableList.add(0, null);
        locationsShowableList = response.getLocations().stream().
                map(Location::getName).collect(Collectors.toList());
        locationsShowableList.add(0, "Any");
    }

    @Override
    protected void uiSuccess() {
        mContext.setLocationsLists(locationsSearchableList, locationsShowableList);
    }

    @Override
    protected void uiFailure() {

    }
}
