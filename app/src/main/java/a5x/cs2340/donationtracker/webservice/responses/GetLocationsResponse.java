package a5x.cs2340.donationtracker.webservice.responses;

import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;

/**
 * Webservice response for getting the list of locations
 */
public class GetLocationsResponse extends StandardResponse {
    private Location[] locations;

    /**
     * Gets the list of locations
     *
     * @return the list of locations
     */
    public Location[] getLocations() {
        return locations.clone();
    }

}
