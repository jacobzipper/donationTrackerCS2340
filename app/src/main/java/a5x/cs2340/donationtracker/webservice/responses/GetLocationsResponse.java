package a5x.cs2340.donationtracker.webservice.responses;

import java.util.List;

import a5x.cs2340.donationtracker.webservice.responses.responseobjects.Location;

public class GetLocationsResponse extends StandardResponse {
    private List<Location> locations;

    public GetLocationsResponse(int error, String msg, List<Location> locations) {
        super(error, msg);
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
