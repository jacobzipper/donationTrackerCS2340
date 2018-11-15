package a5x.cs2340.donationTracker.webservice.responses;

import android.support.annotation.Nullable;

import a5x.cs2340.donationTracker.webservice.responses.responseobjects.Location;

/**
 * Webservice response for getting the list of locations
 */
public class GetLocationsResponse extends StandardResponse {
    private final Location[] locations;

    /**
     * Constructor for get locations response
     * Suppress unused because retrofit
     * @param error error code
     * @param msg message about error
     * @param locations list of locations
     */
    @SuppressWarnings("unused")
    public GetLocationsResponse(int error, @Nullable String msg, Location[] locations) {
        super(error, msg);
        this.locations = locations.clone();
    }


    /**
     * Gets the list of locations
     * @return the list of locations
     */
    public Location[] getLocations() {
        return locations.clone();
    }

}
