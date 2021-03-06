package a5x.cs2340.donationTracker.activities.postLogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a5x.cs2340.donationTracker.R;
import a5x.cs2340.donationTracker.webservice.AccountService;
import a5x.cs2340.donationTracker.webservice.Webservice;
import a5x.cs2340.donationTracker.webservice.WebserviceTask;
import a5x.cs2340.donationTracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationTracker.webservice.responses.responseobjects.Location;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Webservice task for getting the list of all locations
 */
@SuppressLint("StaticFieldLeak")
public class GetLocationsTask extends WebserviceTask<PostLoginActivity,
        Object, GetLocationsResponse> {
    private Location[] locations;
    private List<String> locationNames;
    private final Webservice webservice;
    private final AccountService accountService;

    /**
     * Constructor for creating a GetLocationsTask
     * @param context the activity context
     *
     */
    GetLocationsTask(PostLoginActivity context) {
        super(context, null);
        webservice = Webservice.getInstance();
        accountService = webservice.getAccountService();
    }

    @Override
    public Response<GetLocationsResponse> doRequest(Object body) throws IOException {
        if (webservice.isLoggedIn()) {
            Call<GetLocationsResponse> getLocationsResponseCall = accountService.locations();
            return getLocationsResponseCall.execute();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void useResponse(GetLocationsResponse response) {
        locations = response.getLocations();
        Stream<Location> locationStream = Arrays.stream(locations);
        Stream<String> namesStream = locationStream.map(Location::getName);
        locationNames = namesStream.collect(Collectors.toList());
    }

    @Override
    public void uiSuccess() {
        ((ListView) mContext.findViewById(R.id.locationList)).
                setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, locationNames));
        ((ListView) mContext.findViewById(R.id.locationList)).
                setOnItemClickListener((parent, view, position, id) -> {
            Location location = locations[position];
            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.location_view);
            dialog.setTitle("Location Details");

            // set the custom dialog components - text, image and button
            ((TextView) dialog.findViewById(R.id.name)).
                    setText(mContext.getString(R.string.location_name, location.getName()));
            ((TextView) dialog.findViewById(R.id.latitude)).
                    setText(mContext.getString(R.string.location_latitude, location.getLatitude()));
            ((TextView) dialog.findViewById(R.id.longitude)).
                    setText(mContext.getString(R.string.location_longitude,
                            location.getLongitude()));
            ((TextView) dialog.findViewById(R.id.type)).
                    setText(mContext.getString(R.string.location_type, location.getType()));
            ((TextView) dialog.findViewById(R.id.phone)).
                    setText(mContext.getString(R.string.location_phone, location.getPhone()));
            ((TextView) dialog.findViewById(R.id.address)).
                    setText(mContext.getString(R.string.location_address, location.getAddress()));

            Button dialogCloseButton = dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogCloseButton.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        });
    }

    @Override
    public void uiFailure() {

    }

}
