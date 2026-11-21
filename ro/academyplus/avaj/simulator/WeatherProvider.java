package ro.academyplus.avaj.simulator;

public class WeatherProvider {
    private static final WeatherProvider INSTANCE = new WeatherProvider();
    private final String[] weather = {"RAIN", "FOG", "SUN", "SNOW"};

    private WeatherProvider() {
    }

    public static WeatherProvider getInstance() {
        return INSTANCE;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        return getCurrentWeather(coordinates, 0);
    }

    String getCurrentWeather(Coordinates coordinates, long cycle) {
        long index = (long) coordinates.getLongitude() + coordinates.getLatitude()
                + coordinates.getHeight() + cycle;
        return weather[Math.floorMod(index, weather.length)];
    }
}