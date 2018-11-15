package a5x.cs2340.donationTracker.activities.adminTools;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a5x.cs2340.donationTracker.webservice.AccountService;
import a5x.cs2340.donationTracker.webservice.Webservice;
import a5x.cs2340.donationTracker.webservice.WebserviceTask;
import a5x.cs2340.donationTracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.Location;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Task to get a list of location names specifically for searching.
 * Sets a showAble and searchable list location names to the context
 * The showAble list has "Any" in index 0, and the searchable list has null in index 0
 * All other indices are identical, and contain the names of all locations
 */
public class GetSearchableLocationsTask extends WebserviceTask<ViewDonationsActivity,
        Object, GetLocationsResponse> {
    private List<String> locationsShowAbleList;
    private List<String> locationsSearchableList;
    private final AccountService accountService;
    private final Webservice webservice;
    GetSearchableLocationsTask(ViewDonationsActivity context) {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void useResponse(GetLocationsResponse response) {
        Stream<Location> searchableListStream = Arrays.stream(response.getLocations());
        Stream<String> searchableListNameStream = searchableListStream.map(Location::getName);
        locationsSearchableList = searchableListNameStream.collect(Collectors.toList());
        locationsSearchableList.add(0, null);
        Stream<Location> showAbleListStream = Arrays.stream(response.getLocations());
        Stream<String> showAbleListNameStream = showAbleListStream.map(Location::getName);
        locationsShowAbleList = showAbleListNameStream.collect(Collectors.toList());
        locationsShowAbleList.add(0, "Any");
    }

    @Override
    protected void uiSuccess() {
        mContext.setLocationsLists(locationsSearchableList, locationsShowAbleList);
    }

    @Override
    protected void uiFailure() {

    }
}
