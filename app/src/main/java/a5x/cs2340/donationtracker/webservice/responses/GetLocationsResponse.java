package a5x.cs2340.donationtracker.webservice.responses;

import java.util.List;

import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;

/**
 * Webeservice response for getting the list of locations
 */
public class GetLocationsResponse extends StandardResponse {
    private List<Location> locations;

    /**
     * Constructor for GetLocationsResponse
     * @param error error code
     * @param msg error message
     * @param locations list of locations
     */
    public GetLocationsResponse(int error, String msg, List<Location> locations) {
        super(error, msg);
        this.locations = locations;
    }

    /**
     * Gets the list of locations
     * @return the list of locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Sets the list of locations
     * @param locations the new list of locations
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
