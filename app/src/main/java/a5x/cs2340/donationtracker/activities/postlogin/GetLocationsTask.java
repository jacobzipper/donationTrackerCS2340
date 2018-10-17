package a5x.cs2340.donationtracker.activities.postlogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
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
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class GetLocationsTask extends AsyncTask<Void, Void, Boolean> {
    private final PostLoginActivity mContext;
    private List<Location> locations;
    private List<String> locationNames;

    public GetLocationsTask(PostLoginActivity context) {
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Boolean doInBackground(Void... params) {
        Response<GetLocationsResponse> locationsAttempt;
        try {
            locationsAttempt = Webservice.accountService.locations().execute();
        } catch (IOException e) {
            // TODO: Graceful error handling
            e.printStackTrace();
            return false;
        }

        // TODO: Error messages for each error code from backend
        GetLocationsResponse getLocationsResponse = locationsAttempt.body();
        if (locationsAttempt.code() == 200 && getLocationsResponse != null && getLocationsResponse.getError() == 0) {
            locations = getLocationsResponse.getLocations();
            locationNames = locations.stream().map(location -> location.getName()).collect(Collectors.toList());
            return true;
        }
        return false;

    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            ((ListView) mContext.findViewById(R.id.locationlist)).setAdapter(new ArrayAdapter<String>(mContext,
                    android.R.layout.simple_list_item_1, android.R.id.text1, locationNames));
            ((ListView) mContext.findViewById(R.id.locationlist)).setOnItemClickListener((parent, view, position, id) -> {
                Location location = locations.get(position);
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.location_view);
                dialog.setTitle("Location Details");

                // set the custom dialog components - text, image and button
                ((TextView) dialog.findViewById(R.id.name)).setText(location.getName());
                ((TextView) dialog.findViewById(R.id.latitude)).setText(location.getLatitude());
                ((TextView) dialog.findViewById(R.id.longitude)).setText(location.getLongitude());
                ((TextView) dialog.findViewById(R.id.type)).setText(location.getType());
                ((TextView) dialog.findViewById(R.id.phone)).setText(location.getPhone());
                ((TextView) dialog.findViewById(R.id.address)).setText(location.getAddress());

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(v -> dialog.dismiss());

                dialog.show();
            });
        } else {
            // Failed
        }
    }

    @Override
    protected void onCancelled() {

    }
}
