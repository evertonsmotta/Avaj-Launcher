package ro.academyplus.avaj.simulator;

public class Helicopter extends Aircraft {
    public Helicopter(long id, String name, Coordinates coordinates) {
        super(id, name, coordinates);
    }

    @Override
    public void updateConditions() {
        switch (weatherTower.getWeather(coordinates)) {
            case "SUN" -> respond("Taking the scenic route.", 10, 0, 2);
            case "RAIN" -> respond("The rotor is not an umbrella.", 5, 0, 0);
            case "FOG" -> respond("The scenic route has lost its scenery.", 1, 0, 0);
            case "SNOW" -> respond("My rotor did not pack a winter coat.", 0, 0, -12);
        }
    }
}