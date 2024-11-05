public class CommercialBuilding extends Building {
    private String openingHours;
    private String website;

    public CommercialBuilding(String name, Address address, String openingHours, String website) {
        super(name, address);
        this.openingHours = openingHours;
        this.website = website;
    }

    public String getInfo() {
        return "Commercial Building: " + getName() + ", Address: " + getAddress().getAddress()
                + (openingHours != null ? ", Opening Hours: " + openingHours : "")
                + (website != null ? ", Website: " + website : "");
    }
}