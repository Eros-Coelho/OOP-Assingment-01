public class Address {
    private Location location;
    private String eircode;
    private String street;
    private String city;

    public Address(Location location, String eircode, String street, String city) {
        this.location = location;
        this.eircode = eircode;
        this.street = street;
        this.city = city;
    }

    public String getAddress() {
        return eircode + ", " + street + ", " + city;
    }

    public Location getLocation() {
        return location;
    }
}
