package a5x.cs2340.donationtracker.webservice.responses.responseobjects;

/**
 * Represents a Location object for the app
 */
public class Location {
    private String name;
    private String type;
    private String latitude;
    private String longitude;
    private String address;
    private String phone;
    private String id;

    public Location(String name, String type, String latitude, String longitude, String address, String phone, String id) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.phone = phone;
        this.id = id;
    }

    /**
     * Getter for type
     * @return the type of the location
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for name
     * @return the name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for latitude
     * @return the latitude of the location
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Getter for longitude
     * @return the longitude of the location
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Getter for address
     * @return the address of the location
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for phone number
     * @return the phone number of the location
     */
    public String getPhone() {
        return phone;
    }

}
