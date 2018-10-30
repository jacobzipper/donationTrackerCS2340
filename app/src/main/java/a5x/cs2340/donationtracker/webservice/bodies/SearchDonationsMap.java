package a5x.cs2340.donationtracker.webservice.bodies;

import java.util.HashMap;

public class SearchDonationsMap extends HashMap<String, String> {
    public SearchDonationsMap(String name, String category, String location) {
        super();
        if (name != null) {
            this.put("name", name);
        }
        if (category != null) {
            this.put("category", category);
        }
        if (location != null) {
            this.put("location", location);
        }
    }
}
