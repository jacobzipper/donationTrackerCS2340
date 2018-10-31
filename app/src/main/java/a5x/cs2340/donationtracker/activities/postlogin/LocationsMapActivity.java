package a5x.cs2340.donationtracker.activities.postlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import a5x.cs2340.donationtracker.R;
import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;

public class LocationsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GetMapLocationsTask mTask;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_map);
        Button backToLocationListButton = findViewById(R.id.backFromMapButton);
        backToLocationListButton.setOnClickListener(v -> backToLocationList());
        mapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = savedInstanceState == null ? null :
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
        Log.d("MapReady", "Successful Map Ready");
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mTask = new GetMapLocationsTask(this, null);
        mTask.execute((Void) null);

    }
    void populateList(List<Location> locations) {
        createMarkers(locations);
    }
    private void createMarkers(List<Location> locations) {
        Log.d("MapReady", "Successful locationTask");
        LatLng newLocation = new LatLng(0, 0);
        for (Location location : locations) {
            Log.d("MapReady", "Adding Location");
            newLocation = new LatLng(Double.parseDouble(location.getLatitude()),
                    Double.parseDouble(location.getLongitude()));
            Log.d("MapReady", "Adding Marker");
            mMap.addMarker(new MarkerOptions().position(newLocation).
                    title(location.getName()).snippet(location.getPhone()));
            Log.d("MapReady", "Marker should be added");
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
    }
    private void backToLocationList() {
        Intent backToLocationListIntent = new Intent(this, PostLoginActivity.class);
        startActivity(backToLocationListIntent);
    }
}
