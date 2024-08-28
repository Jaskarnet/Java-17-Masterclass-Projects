package net.jaskar;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class AdventureGame {
    // A map to store all locations in the game. The key is the location's name.
    private Map<String, Location> locations = new HashMap<>();
    Location currentLocation;  // Tracks the player's current location.
    Scanner scanner = new Scanner(System.in);  // Scanner to get user input.

    // Game locations and their possible directions are stored in this string.
    private static final String GAME_LOCATIONS = """
            road,at the end of the road, W: hill, E:well house,S:valley,N:forest
            hill,on top of hill with a view in all directions,N:forest, E:road
            well house,inside a well house for a small spring,W:road,N:lake,S:stream
            valley,in a forest valley beside a tumbling stream,N:road,W:hill,E:stream
            forest,at the edge of a thick dark forest,S:road,E:lake
            lake,by an alpine lake surrounded by wildflowers,W:forest,S:well house
            stream,near a stream with a rocky bed,W:valley, N:well house
            """;

    // Enum to represent the four compass directions: East, North, South, West.
    private enum Compass {
        E, N, S, W;

        // Array to convert enum values to string descriptions.
        private static final String[] directions = {"East", "North", "South", "West"};

        // Method to get the string description of a compass direction.
        public String getString() {
            return directions[this.ordinal()];
        }
    }

    // Class to represent a location in the game.
    private class Location {
        private String description;  // Description of the location.
        // A map that holds possible directions and the names of locations in those directions.
        private Map<Compass, String> nextPlaces = new HashMap<>();

        public Location(String description) {
            this.description = description;  // Set the description when creating a location.
        }

        // Method to get the set of valid compass directions from this location.
        public Set<Compass> getValidDirections() {
            return nextPlaces.keySet();
        }
    }

    // Constructor to set up the game by loading locations and setting the starting location.
    public AdventureGame() {
        loadLocations();  // Load all locations from the GAME_LOCATIONS string.
        currentLocation = locations.get("road");  // Start the player at the "road" location.
    }

    // Method to start the game loop where the player can make choices.
    public void startGame() {
        while (true) {
            printOptions();  // Display the player's options based on current location.
            String choice = Character.toString(scanner.nextLine().toUpperCase().trim().charAt(0));
            if (choice.toUpperCase().charAt(0) == 'Q') break;  // Quit if the player types 'Q'.
            try {
                // Attempt to move to the next location based on the player's choice.
                String nextLocationName = currentLocation.nextPlaces.get(Compass.valueOf(choice));
                currentLocation = locations.get(nextLocationName);
            } catch (IllegalArgumentException e) {
                // Handle invalid direction choice.
                System.out.printf("Direction (%s) not available from current location!\n", choice);
                continue;  // Skip to the next loop iteration if direction is invalid.
            }
        }
    }

    // Method to display available directions from the current location.
    private void printOptions() {
        StringBuilder nextPlacesOptions = new StringBuilder();
        // Loop through available directions and append them to the options string.
        currentLocation.nextPlaces.forEach((k, v) -> nextPlacesOptions.append("-> A %s to the %s (%s)\n".formatted(v, k.getString(), k)));
        // Display the current location description and available directions.
        System.out.printf("""
        
        *** You're standing %s ***
        From here you can see:
        %sSelect Your Compass Direction (Q to quit) >>""", currentLocation.description, nextPlacesOptions);
    }

    // Method to load locations and their connections from the GAME_LOCATIONS string.
    private void loadLocations() {
        for (String line : GAME_LOCATIONS.split("\n")) {
            String[] fields = line.split(",");
            String locationName = fields[0].trim();  // The name of the location.
            String locationDescription = fields[1].trim();  // The description of the location.
            Location location = new Location(locationDescription);  // Create a new Location object.
            // Loop through direction and location pairs to populate nextPlaces map.
            for (int i = 2; i < fields.length; i++) {
                Compass direction = Compass.valueOf(Character.toString(fields[i].trim().toUpperCase().charAt(0)));
                String nextPlace = fields[i].split(":")[1].trim();  // The name of the connected location.
                location.nextPlaces.put(direction, nextPlace);  // Add the connection to the map.
            }
            locations.put(locationName, location);  // Store the location in the map.
        }
    }
}
