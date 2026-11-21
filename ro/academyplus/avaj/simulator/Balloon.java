package ro.academyplus.avaj.simulator;

public class Balloon extends Aircraft {
    public Balloon(long id, String name, Coordinates coordinates) {
        super(id, name, coordinates);
    }

    @Override
    public void updateConditions() {
        switch (weatherTower.getWeather(coordinates)) {
            case "SUN" -> respond("Perfect light for an aerial photo.", 2, 0, 4);
            case "RAIN" -> respond("The basket is not a bathtub.", 0, 0, -5);
            case "FOG" -> respond("Did someone erase the horizon?", 0, 0, -3);
            case "SNOW" -> respond("The balloon is turning into a snow globe.", 0, 0, -15);
        }
    }
}