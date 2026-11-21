package ro.academyplus.avaj.simulator;

public class JetPlane extends Aircraft {
    public JetPlane(long id, String name, Coordinates coordinates) {
        super(id, name, coordinates);
    }

    @Override
    public void updateConditions() {
        switch (weatherTower.getWeather(coordinates)) {
            case "SUN" -> respond("Clear skies and a smooth climb.", 0, 10, 2);
            case "RAIN" -> respond("Good thing the runway has drainage.", 0, 5, 0);
            case "FOG" -> respond("The view from every seat is now the same.", 0, 1, 0);
            case "SNOW" -> respond("Descending before the wings need mittens.", 0, 0, -7);
        }
    }
}