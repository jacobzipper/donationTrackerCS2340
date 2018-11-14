package a5x.cs2340.donationtracker;

import org.junit.Test;

import a5x.cs2340.donationtracker.webservice.bodies.SearchDonationsMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class SearchDonationsMapTest {
    private SearchDonationsMap searchDonationsMap;
    @Test
    public void allNonNullTest() {
        searchDonationsMap = new SearchDonationsMap("Name", "Category", "Location");
        assertNotNull("Name was null", searchDonationsMap.get("name"));
        assertNotNull("Category was null", searchDonationsMap.get("category"));
        assertNotNull("Location was null", searchDonationsMap.get("location"));
        assertEquals("Name was not expected value", "Name", searchDonationsMap.get("name"));
        assertEquals("Category was not expected value", "Category", searchDonationsMap.get("category"));
        assertEquals("Location was not expected value", "Location", searchDonationsMap.get("location"));
        assertEquals("More than 3 elements added to map", 3, searchDonationsMap.size());
    }

    @Test
    public void allNullTest() {
        searchDonationsMap = new SearchDonationsMap(null, null, null);
        assertFalse("Contained name", searchDonationsMap.containsKey("name"));
        assertFalse("Contained category", searchDonationsMap.containsKey("category"));
        assertFalse("Contained location", searchDonationsMap.containsKey("location"));
        assertEquals("Contained any other values", 0, searchDonationsMap.size());
    }
    @Test
    public void allEmptyButNotNull() {
        searchDonationsMap = new SearchDonationsMap("", "", "");
        assertNotNull("Name was null", searchDonationsMap.get("name"));
        assertNotNull("Category was null", searchDonationsMap.get("category"));
        assertNotNull("Location was null", searchDonationsMap.get("location"));
        assertEquals("Name was not expected value", "", searchDonationsMap.get("name"));
        assertEquals("Category was not expected value", "", searchDonationsMap.get("category"));
        assertEquals("Location was not expected value", "", searchDonationsMap.get("location"));
        assertEquals("More than 3 elements added to map", 3, searchDonationsMap.size());
    }
}
