package a5x.cs2340.donationtracker.webservice.bodies;

import java.util.HashMap;

/**
 * Webservice body that represents a single search query to perform a searching task with
 */
public class SearchDonationsMap extends HashMap<String, String> {
    /**
     * Creates a new SearchDonationsMap
     * @param name The name to search for (null or empty string if searching for anything)
     * @param category The name of the category to filter by (null for any category)
     * @param location The location to search (null for any location)
     */
    public SearchDonationsMap(String name, String category, String location) {
        super();
        fillMap(name, category, location);
    }
    private void fillMap(String name, String category, String location) {
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
