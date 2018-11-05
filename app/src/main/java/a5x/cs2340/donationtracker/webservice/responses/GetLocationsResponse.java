package a5x.cs2340.donationtracker.webservice.responses;

import java.util.List;

import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;

/**
 * Webeservice response for getting the list of locations
 */
public class GetLocationsResponse extends StandardResponse {
    private Location[] locations;

    /**
     * Constructor for GetLocationsResponse
     * @param error error code
     * @param msg error message
     * @param locations list of locations
     */
    public GetLocationsResponse(int error, String msg, List<Location> locations) {
        super(error, msg);
        this.locations = new Location[locations.size()];
        this.locations = locations.toArray(this.locations);
    }

    /**
     * Gets the list of locations
     * @return the list of locations
     */
    public Location[] getLocations() {
        return locations.clone();
    }

    /**
     * Sets the list of locations
     * @param locations the new list of locations
     */
    public void setLocations(Location[] locations) {
        this.locations = locations;
    }
}
