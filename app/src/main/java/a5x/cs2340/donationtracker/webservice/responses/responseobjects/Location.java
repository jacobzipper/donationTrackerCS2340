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

    /**
     * Creates a new Location with th  given parameters
     * @param name The name of the location
     * @param type the type of the location
     * @param latitude the location's latitude
     * @param longitude the location's longitude
     * @param address the address of the location
     * @param phone the location's phone number
     * @param id the location id
     */
    public Location(String name, String type, String latitude,
                    String longitude, String address, String phone, String id) {
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
     * Setter for type
     * @param type the new type of the location
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for name
     * @return the name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name the new name of the location
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for latitude
     * @return the latitude of the location
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Setter for latitude
     * @param latitude the new latitude of the location
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter for longitude
     * @return the longitude of the location
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Setter for longitude
     * @param longitude the new longitude of the location
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter for address
     * @return the address of the location
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address
     * @param address the new address of the location
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for phone number
     * @return the phone number of the location
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone number
     * @param phone the new phone number of the location
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for id
     * @return the id of the location
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id the new id of the location
     */
    public void setId(String id) {
        this.id = id;
    }
}
