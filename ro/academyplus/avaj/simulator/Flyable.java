package ro.academyplus.avaj.simulator;

public abstract class Flyable {
    protected WeatherTower weatherTower;

    public abstract void updateConditions();

    public void registerTower(WeatherTower tower) {
        if (weatherTower != null && weatherTower != tower) {
            weatherTower.unregister(this);
        }
        weatherTower = tower;
        tower.register(this);
    }
}