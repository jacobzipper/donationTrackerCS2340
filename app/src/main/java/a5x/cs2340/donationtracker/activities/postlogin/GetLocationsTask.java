package a5x.cs2340.donationtracker.activities.postlogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.models.users.LocationEmployee;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class GetLocationsTask extends WebserviceTask<PostLoginActivity, Object, GetLocationsResponse> {
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
        locationNames = locations.stream().map(location -> location.getName()).collect(Collectors.toList());
    }

    @Override
    public void uiSuccess() {
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

            Button dialogCloseButton = dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogCloseButton.setOnClickListener(v -> dialog.dismiss());
            Button addDonationButton = dialog.findViewById(R.id.dialogAddDonationButton);
            Button viewDonationsButton = dialog.findViewById(R.id.dialogViewDonationsButton);
            if (Webservice.getAccountLoggedIn() instanceof LocationEmployee) {
                viewDonationsButton.setVisibility(View.VISIBLE);
                addDonationButton.setVisibility(View.VISIBLE);
            } else {
                addDonationButton.setVisibility(View.GONE);
                viewDonationsButton.setVisibility(View.GONE);
            }
            dialog.show();
        });
    }

    @Override
    public void uiFailure() {

    }
    private void toAddDonation() {
        Intent toAddDonationIntent = new Intent(mContext, AddDonationActivity.class);
        mContext.startActivity(toAddDonationIntent);
    }
}
