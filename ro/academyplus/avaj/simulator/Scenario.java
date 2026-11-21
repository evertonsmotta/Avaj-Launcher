package ro.academyplus.avaj.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

record Scenario(int cycles, List<Flyable> aircraft) {
    static Scenario read(Path path) throws IOException, InvalidScenarioException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String header = reader.readLine();
            int cycles = parseInt(header == null ? "" : header.strip(), 1, "cycle count");
            if (cycles <= 0) {
                throw new InvalidScenarioException(1, "cycle count must be positive.");
            }

            List<Flyable> aircraft = new ArrayList<>();
            int lineNumber = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                aircraft.add(parseAircraft(line, ++lineNumber));
            }
            return new Scenario(cycles, List.copyOf(aircraft));
        }
    }

    private static Flyable parseAircraft(String line, int lineNumber) throws InvalidScenarioException {
        String[] fields = line.strip().split("\\s+");
        if (fields.length != 5) {
            throw new InvalidScenarioException(lineNumber, "expected TYPE NAME LONGITUDE LATITUDE HEIGHT.");
        }

        int longitude = parseInt(fields[2], lineNumber, "longitude");
        int latitude = parseInt(fields[3], lineNumber, "latitude");
        int height = parseInt(fields[4], lineNumber, "height");
        if (longitude <= 0 || latitude <= 0 || height < 0) {
            throw new InvalidScenarioException(lineNumber,
                    "longitude and latitude must be positive and height cannot be negative.");
        }

        Coordinates coordinates = new Coordinates(longitude, latitude, height);
        try {
            return AircraftFactory.getInstance().newAircraft(fields[0], fields[1], coordinates);
        } catch (IllegalArgumentException error) {
            throw new InvalidScenarioException(lineNumber, error.getMessage());
        }
    }

    private static int parseInt(String value, int lineNumber, String field) throws InvalidScenarioException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException error) {
            throw new InvalidScenarioException(lineNumber, field + " must be a 32-bit integer.");
        }
    }
}