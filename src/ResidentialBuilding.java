public class ResidentialBuilding extends Building {
    private String buildingNumber;

    public ResidentialBuilding(String buildingNumber, Address address) {
        super("", address); // Empty name if not provided
        this.buildingNumber = buildingNumber;
    }

    public ResidentialBuilding(String buildingNumber, String name, Address address) {
        super(name, address);
        this.buildingNumber = buildingNumber;
    }
}
