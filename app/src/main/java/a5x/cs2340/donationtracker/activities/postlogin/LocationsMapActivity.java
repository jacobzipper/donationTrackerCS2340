package a5x.cs2340.donationtracker.activities.postlogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.webservice.AccountService;
import a5x.cs2340.donationtracker.webservice.Webservice;
import a5x.cs2340.donationtracker.webservice.WebserviceTask;
import a5x.cs2340.donationtracker.webservice.responses.GetLocationsResponse;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Activity that shows a map of all locations using Google maps with a pin at every location
 */
public class LocationsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final float DEFAULT_ZOOM_LEVEL = 10.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_map);
        Button backToLocationListButton = findViewById(R.id.backFromMapButton);
        backToLocationListButton.setOnClickListener(v -> backToLocationList());
        mapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = (savedInstanceState == null) ? null :
                savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings mapUISettings = mMap.getUiSettings();
        mapUISettings.setZoomControlsEnabled(true);
        GetMapLocationsTask mTask = new GetMapLocationsTask();
        mTask.execute((Object) null);

    }
    private void populateList(Location[] locations) {
        createMarkers(locations);
    }
    private void createMarkers(Location[] locations) {
        LatLng newLocation = new LatLng(0, 0);
        for (Location location : locations) {
            newLocation = new LatLng(Double.parseDouble(location.getLatitude()),
                    Double.parseDouble(location.getLongitude()));
            MarkerOptions newMarker = new MarkerOptions();
            newMarker.position(newLocation);
            newMarker.title(location.getName());
            newMarker.snippet(location.getPhone());
            mMap.addMarker(newMarker);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, DEFAULT_ZOOM_LEVEL));
    }
    private void backToLocationList() {
        Intent backToLocationListIntent = new Intent(this, PostLoginActivity.class);
        startActivity(backToLocationListIntent);
    }

    /**
     * Suppressed because need access to UI elements and can't make this static
     */
    @SuppressLint("StaticFieldLeak")
    private class GetMapLocationsTask extends WebserviceTask<Object, Void,
            GetLocationsResponse> {
        private Location[] locations;

        @Override
        protected Response<GetLocationsResponse> doRequest(Object body) throws IOException {
            Webservice webservice = Webservice.getInstance();
            if (webservice.isLoggedIn()) {
                AccountService accountService = webservice.getAccountService();
                Call<GetLocationsResponse> getLocationsResponseCall = accountService.locations();
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
            populateList(locations);
        }
    }
}
