package a5x.cs2340.donationtracker.activities.postlogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class GetLocationsTask extends WebserviceTask<PostLoginActivity,
        Object, GetLocationsResponse> {
    private List<Location> locations;
    private List<String> locationNames;

    public GetLocationsTask(PostLoginActivity context, Object body) {
        super(context, body);
    }

    @Override
    public Response<GetLocationsResponse> doRequest(Object body) throws IOException {
        return Webservice.accountService.locations().execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void useResponse(GetLocationsResponse response) {
        locations = response.getLocations();
        locationNames = locations.stream().map(Location::getName).collect(Collectors.toList());
    }

    @Override
    public void uiSuccess() {
        ((ListView) mContext.findViewById(R.id.locationlist)).
                setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, locationNames));
        ((ListView) mContext.findViewById(R.id.locationlist)).
                setOnItemClickListener((parent, view, position, id) -> {
            Location location = locations.get(position);
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
