package ro.academyplus.avaj.simulator;

import java.util.function.Consumer;

public class WeatherTower extends Tower {
    private long cycle;

    public WeatherTower() {
        super();
    }

    WeatherTower(Consumer<String> logger) {
        super(logger);
    }

    public String getWeather(Coordinates coordinates) {
        return WeatherProvider.getInstance().getCurrentWeather(coordinates, cycle);
    }

    public void changeWeather() {
        conditionChanged();
        cycle++;
    }
}