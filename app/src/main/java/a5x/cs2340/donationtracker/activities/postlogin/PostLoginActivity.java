package a5x.cs2340.donationtracker.activities.postlogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.WelcomeActivity;
import a5x.cs2340.donationtracker.activities.admintools.AdminToolsActivity;
import a5x.cs2340.donationtracker.activities.login.LoginActivity;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Default activity users go to after they successfully log in
 */
public class PostLoginActivity extends AppCompatActivity {
    private GetLocationsTask mGetLocationsTask;
    private final Webservice webservice = Webservice.getInstance();
    @SuppressLint("StringFormatMatches") // This fixes an android studio bug
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!webservice.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        setContentView(R.layout.activity_post_login);
        TextView postLoginTextView = findViewById(R.id.postLoginTextView);
        postLoginTextView.setText(getString(R.string.post_login_welcome_string,
                webservice.getUserTypeLabel(),
                webservice.getAccountName()));
        Button logoutButton = findViewById(R.id.logoutButton);
        mGetLocationsTask = new GetLocationsTask();
        mGetLocationsTask.execute((Object) null);
        logoutButton.setOnClickListener(view -> {
            logoutBackToWelcome();
            if (mGetLocationsTask != null) {
                mGetLocationsTask.cancel(true);
            }
        });
        Button toAdminToolsButton = findViewById(R.id.toToolsButton);
        toAdminToolsButton.setOnClickListener(v -> toAdminTools());
        Button toMapButton = findViewById(R.id.toMapButton);
        toMapButton.setOnClickListener(v -> toMapScreen());
    }

    /**
     * Transitions back to the welcome screen
     */
    private void logoutBackToWelcome() {
        webservice.logOut();
        Intent backToWelcomeIntent = new Intent(this, WelcomeActivity.class);
        startActivity(backToWelcomeIntent);
    }

    /**
     * Transitions to the admin tools screen
     */
    private void toAdminTools() {
        Intent toAdminToolsIntent = new Intent(this, AdminToolsActivity.class);
        startActivity(toAdminToolsIntent);
    }
    /**
     * Transitions to map screen
     */
    private void toMapScreen() {
        Intent toMapIntent = new Intent(this, LocationsMapActivity.class);
        startActivity(toMapIntent);
    }

    public class GetLocationsTask extends WebserviceTask<Object,
            Void, GetLocationsResponse> {
        private Location[] locations;
        private List<String> locationNames;

        @Override
        public Response<GetLocationsResponse> doRequest(Object body) throws IOException {
            if (Webservice.getInstance().isLoggedIn()) {
                Call<GetLocationsResponse> getLocationsResponseCall = Webservice.getInstance()
                        .getAccountService().locations();
                return getLocationsResponseCall.execute();
            }
            return null;
        }

        @SuppressLint({"NewApi", "LocalSuppress"})
        @Override
        protected void onPostExecute(GetLocationsResponse response) {
            if (response == null) {
                return;
            }
            locations = response.getLocations();
            Stream<Location> locationStream = Arrays.stream(locations);
            Stream<String> namesStream = locationStream.map(Location::getName);
            locationNames = namesStream.collect(Collectors.toList());
            ((ListView) findViewById(R.id.locationlist)).
                    setAdapter(new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, android.R.id.text1, locationNames));
            ((ListView) findViewById(R.id.locationlist)).
                    setOnItemClickListener((parent, view, position, id) -> {
                        Location location = locations[position];
                        final Dialog dialog = new Dialog(getApplicationContext());
                        dialog.setContentView(R.layout.location_view);
                        dialog.setTitle("Location Details");

                        // set the custom dialog components - text, image and button
                        ((TextView) dialog.findViewById(R.id.name)).
                                setText(getString(R.string.location_name, location.getName()));
                        ((TextView) dialog.findViewById(R.id.latitude)).
                                setText(getString(R.string.location_latitude, location.getLatitude()));
                        ((TextView) dialog.findViewById(R.id.longitude)).
                                setText(getString(R.string.location_longitude,
                                        location.getLongitude()));
                        ((TextView) dialog.findViewById(R.id.type)).
                                setText(getString(R.string.location_type, location.getType()));
                        ((TextView) dialog.findViewById(R.id.phone)).
                                setText(getString(R.string.location_phone, location.getPhone()));
                        ((TextView) dialog.findViewById(R.id.address)).
                                setText(getString(R.string.location_address, location.getAddress()));

                        Button dialogCloseButton = dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogCloseButton.setOnClickListener(v -> dialog.dismiss());

                        dialog.show();
                    });
        }
    }
}
