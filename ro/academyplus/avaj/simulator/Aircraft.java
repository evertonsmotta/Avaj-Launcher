package ro.academyplus.avaj.simulator;

public abstract class Aircraft extends Flyable {
    protected final long id;
    protected final String name;
    protected Coordinates coordinates;

    protected Aircraft(long id, String name, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }

    @Override
    public void registerTower(WeatherTower tower) {
        super.registerTower(tower);
        if (coordinates.getHeight() == 0) {
            land();
        }
    }

    protected void respond(String message, int longitude, int latitude, int height) {
        weatherTower.log(this + ": " + message);
        coordinates = new Coordinates(
                (int) Math.min(Integer.MAX_VALUE, (long) coordinates.getLongitude() + longitude),
                (int) Math.min(Integer.MAX_VALUE, (long) coordinates.getLatitude() + latitude),
                coordinates.getHeight() + height);
        if (coordinates.getHeight() == 0) {
            land();
        }
    }

    private void land() {
        weatherTower.log(this + " landing.");
        weatherTower.unregister(this);
        weatherTower = null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + name + "(" + id + ")";
    }
}