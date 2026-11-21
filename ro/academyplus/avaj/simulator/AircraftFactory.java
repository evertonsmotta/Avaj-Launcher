package ro.academyplus.avaj.simulator;

public class AircraftFactory {
    private static final AircraftFactory INSTANCE = new AircraftFactory();
    private long lastId;

    private AircraftFactory() {
    }

    public static AircraftFactory getInstance() {
        return INSTANCE;
    }

    public synchronized Flyable newAircraft(String type, String name, Coordinates coordinates) {
        return switch (type) {
            case "Balloon" -> new Balloon(++lastId, name, coordinates);
            case "Helicopter" -> new Helicopter(++lastId, name, coordinates);
            case "JetPlane" -> new JetPlane(++lastId, name, coordinates);
            default -> throw new IllegalArgumentException("Unknown aircraft type: " + type);
        };
    }
}