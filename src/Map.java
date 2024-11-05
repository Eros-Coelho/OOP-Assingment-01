/*
Student Name: Eros Lima Coelho
Student Number: 3151957
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {

    public static void main(String[] args) {
        // Create an array list to store residential and commercial buildings
        List<ResidentialBuilding> residentialBuildings = new ArrayList<>();
        List<CommercialBuilding> commercialBuildings = new ArrayList<>();

        // Add residential buildings
        residentialBuildings.add(new ResidentialBuilding("1", new Address(new Location(51.8969, -8.4676), "T12E299", "Eglinton St", "Cork")));
        residentialBuildings.add(new ResidentialBuilding("12", new Address(new Location(51.8895, -8.4259), "T12X8RH", "Mahon", "Cork")));
        residentialBuildings.add(new ResidentialBuilding("3", new Address(new Location(51.8924, -8.4957), "T12XY01", "Western Rd", "Cork")));

        // Add commercial buildings
        commercialBuildings.add(new CommercialBuilding("Griffith College Cork", new Address(new Location(51.9042, -8.4608), "T23DF3A", "Wellington Rd", "Cork"), "9:00 AM - 5:30 PM", "www.griffith.ie"));
        commercialBuildings.add(new CommercialBuilding("The Kingsley Hotel", new Address(new Location(51.89352, -8.50834), "T12P680", "Carrigrohane Rd", "Cork"), "24/7", "www.thekingsley.ie"));
        commercialBuildings.add(new CommercialBuilding("Penneys", new Address(new Location(51.8985, -8.4716), "T12D868", "St Patrick's St", "Cork"), "09:00 AM - 7:00 PM", "www.primark.com"));

        Scanner scanner = new Scanner(System.in);
//        boolean set to true so the loop continues until the user quits
        boolean running = true;

        while (running) {
            System.out.println("\u001B[34mMenu:\n1. Find a building by its Eircode \n2. Get a building's Eircode by its coordinates \n3. Get distance between 2 buildings \n4. Quit\u001B[0m");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Pleas enter the building's Eircode: ");
                    String eircode = scanner.nextLine();
                    findBuildingByEircode(eircode, residentialBuildings, commercialBuildings);
                    break;
                case 2:
                    System.out.print("Please enter the building's Latitude: ");
                    double latitude = scanner.nextDouble();
                    System.out.print("Please enter the building's Longitude: ");
                    double longitude = scanner.nextDouble();
                    getBuildingByCoordinates(new Location(latitude, longitude), residentialBuildings, commercialBuildings);
                    break;
                case 3:
                    System.out.print("Please enter first building's Eircode: ");
                    String eircode1 = scanner.nextLine();
                    System.out.print("Please enter second building's Eircode: ");
                    String eircode2 = scanner.nextLine();
                    getDistanceBetweenBuildings(eircode1, eircode2, residentialBuildings, commercialBuildings);
                    break;
                case 4:
                    running = false;
                    System.out.println("Goodbye! Thank you for using our services!");
                    break;
                default:
                    System.out.println("\u001B[31mError! Please choose an option between 1 and 4!\u001B[0m");
            }
        }
        scanner.close();
    }
    public static void findBuildingByEircode(String eircode, List<ResidentialBuilding> residentialBuildings, List<CommercialBuilding> commercialBuildings) {
        boolean found = false;

        // Search through residential buildings
        for (ResidentialBuilding rb : residentialBuildings) {
            if (rb.getAddress().getAddress().contains(eircode)) {
                System.out.println("Found Residential Building: " + rb.getName() + ", Address: " + rb.getAddress().getAddress());
                found = true;
                break;
            }
        }
        // Search through commercial buildings if not found
        if (!found) {
            for (CommercialBuilding cb : commercialBuildings) {
                if (cb.getAddress().getAddress().contains(eircode)) {
                    System.out.println("Found Commercial Building: " + cb.getInfo());
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("Building with Eircode " + eircode + " not found.");
        }
    }

    public static void getBuildingByCoordinates(Location location, List<ResidentialBuilding> residentialBuildings, List<CommercialBuilding> commercialBuildings) {
        boolean found = false;

        // Search through residential buildings
        for (ResidentialBuilding rb : residentialBuildings) {
            if (areCoordinatesClose(rb.getAddress().getLocation(), location)) {
                System.out.println("Found Residential Building: " + rb.getName() + ", Eircode: " + rb.getAddress().getAddress());
                found = true;
                break;
            }
        }

        // Search through commercial buildings if not found
        if (!found) {
            for (CommercialBuilding cb : commercialBuildings) {
                if (areCoordinatesClose(cb.getAddress().getLocation(), location)) {
                    System.out.println("Found Commercial Building: " + cb.getName() + ", Eircode: " + cb.getAddress().getAddress());
                    found = true;
                    break;
                }
            }
        }
//        If nothing is found, return a statement saying so
        if (!found) {
            System.out.println("No building found near these coordinates.");
        }
    }

    public static void getDistanceBetweenBuildings(String eircode1, String eircode2, List<ResidentialBuilding> residentialBuildings, List<CommercialBuilding> commercialBuildings) {
        Location location1 = findLocationByEircode(eircode1, residentialBuildings, commercialBuildings);
        Location location2 = findLocationByEircode(eircode2, residentialBuildings, commercialBuildings);

        if (location1 != null && location2 != null) {
            double distance = distance(location1, location2);
            System.out.println("Distance between buildings is: " + distance + " km");
        } else {
            System.out.println("One or both Eircodes not found.");
        }
    }

    public static Location findLocationByEircode(String eircode, List<ResidentialBuilding> residentialBuildings, List<CommercialBuilding> commercialBuildings) {
        for (ResidentialBuilding rb : residentialBuildings) {
            if (rb.getAddress().getAddress().contains(eircode)) {
                return rb.getAddress().getLocation();
            }
        }
        for (CommercialBuilding cb : commercialBuildings) {
            if (cb.getAddress().getAddress().contains(eircode)) {
                return cb.getAddress().getLocation();
            }
        }
        // Eircode not found
        return null;
    }

    public static boolean areCoordinatesClose(Location loc1, Location loc2) {
        // Defines how close the coordinates need to be to match
        final double threshold = 0.001;
        return Math.abs(loc1.getLatitude() - loc2.getLatitude()) < threshold
                && Math.abs(loc1.getLongitude() - loc2.getLongitude()) < threshold;
    }

    public static double distance(Location first, Location second) {
        // Radius of the Earth in km
        final double earthRadius = 6371;

//        Using the standard Haversine formula to calculate distance between buildings
        double latDistance = Math.toRadians(second.getLatitude() - first.getLatitude());
        double lonDistance = Math.toRadians(second.getLongitude() - first.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(first.getLatitude())) * Math.cos(Math.toRadians(second.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        returns the distance in km
        return earthRadius * c;
    }
}