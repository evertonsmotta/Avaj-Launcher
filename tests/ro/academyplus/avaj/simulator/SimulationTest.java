package ro.academyplus.avaj.simulator;

import java.util.ArrayList;
import java.util.List;

public class SimulationTest {
    public static void main(String[] args) {
        checkMovement("JetPlane", "SUN", 0, 10, 2);
        checkMovement("JetPlane", "RAIN", 0, 5, 0);
        checkMovement("JetPlane", "FOG", 0, 1, 0);
        checkMovement("JetPlane", "SNOW", 0, 0, -7);
        checkMovement("Helicopter", "SUN", 10, 0, 2);
        checkMovement("Helicopter", "RAIN", 5, 0, 0);
        checkMovement("Helicopter", "FOG", 1, 0, 0);
        checkMovement("Helicopter", "SNOW", 0, 0, -12);
        checkMovement("Balloon", "SUN", 2, 0, 4);
        checkMovement("Balloon", "RAIN", 0, 0, -5);
        checkMovement("Balloon", "FOG", 0, 0, -3);
        checkMovement("Balloon", "SNOW", 0, 0, -15);
        checkLandingAndCeiling();
        checkWeather();
        System.out.println("Simulation model checks passed.");
    }

    private static void checkMovement(String type, String weather, int longitude, int latitude, int height) {
        List<String> log = new ArrayList<>();
        WeatherTower tower = fixedWeather(weather, log);
        Aircraft aircraft = (Aircraft) AircraftFactory.getInstance()
                .newAircraft(type, "Test", new Coordinates(10, 20, 50));
        aircraft.registerTower(tower);
        tower.changeWeather();
        Coordinates updated = aircraft.coordinates;
        require(updated.getLongitude() == 10 + longitude && updated.getLatitude() == 20 + latitude
                && updated.getHeight() == 50 + height, type + " movement in " + weather);
        require(log.size() == 2 && log.get(1).startsWith(aircraft + ": "), type + " weather log");
    }

    private static void checkLandingAndCeiling() {
        List<String> log = new ArrayList<>();
        WeatherTower rain = fixedWeather("RAIN", log);
        AircraftFactory factory = AircraftFactory.getInstance();
        Aircraft balloon = (Aircraft) factory.newAircraft("Balloon", "Same", new Coordinates(1, 1, 5));
        Aircraft jet = (Aircraft) factory.newAircraft("JetPlane", "Same", new Coordinates(1, 1, 100));
        require(jet.id == balloon.id + 1, "unique sequential IDs");
        balloon.registerTower(rain);
        jet.registerTower(rain);
        rain.changeWeather();
        require(balloon.coordinates.getHeight() == 0 && jet.coordinates.getLatitude() == 6, "landing round");
        require(log.size() == 6 && log.get(3).equals(balloon + " landing.")
                && log.get(4).equals("Tower says: " + balloon + " unregistered from weather tower."), "landing log");
        rain.changeWeather();
        require(log.size() == 7 && jet.coordinates.getLatitude() == 11, "landed aircraft is no longer notified");

        WeatherTower sun = fixedWeather("SUN", log);
        Aircraft helicopter = (Aircraft) factory.newAircraft("Helicopter", "High", new Coordinates(1, 1, 99));
        helicopter.registerTower(sun);
        sun.changeWeather();
        require(helicopter.coordinates.getHeight() == 100, "in-flight height cap");
        require(new Coordinates(1, 1, 768).getHeight() == 100, "initial height cap");
    }

    private static void checkWeather() {
        WeatherProvider provider = WeatherProvider.getInstance();
        Coordinates origin = new Coordinates(1, 1, 1);
        String base = provider.getCurrentWeather(origin);
        require(!base.equals(provider.getCurrentWeather(new Coordinates(2, 1, 1)))
                && !base.equals(provider.getCurrentWeather(new Coordinates(1, 2, 1)))
                && !base.equals(provider.getCurrentWeather(new Coordinates(1, 1, 2))), "all axes affect weather");
        WeatherTower tower = new WeatherTower();
        require(base.equals(tower.getWeather(origin)), "initial weather");
        tower.changeWeather();
        require(!base.equals(tower.getWeather(origin)), "weather changes between cycles");
    }

    private static WeatherTower fixedWeather(String weather, List<String> log) {
        return new WeatherTower(log::add) {
            @Override
            public String getWeather(Coordinates coordinates) {
                return weather;
            }
        };
    }

    private static void require(boolean condition, String check) {
        if (!condition) {
            throw new AssertionError(check);
        }
    }
}