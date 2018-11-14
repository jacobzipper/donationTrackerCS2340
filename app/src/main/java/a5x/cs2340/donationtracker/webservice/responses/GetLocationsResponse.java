package a5x.cs2340.donationtracker.webservice.responses;

import android.support.annotation.Nullable;

import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;

/**
 * Webservice response for getting the list of locations
 */
public class GetLocationsResponse extends StandardResponse {
    private final Location[] locations;

    protected GetLocationsResponse(int error, @Nullable String msg, Location[] locations) {
        super(error, msg);
        this.locations = locations.clone();
    }

    /**
     * Gets the list of locations
     *
     * @return the list of locations
     */
    public Location[] getLocations() {
        return locations.clone();
    }

}
