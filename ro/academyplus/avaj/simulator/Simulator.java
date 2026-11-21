package ro.academyplus.avaj.simulator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Simulator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ro.academyplus.avaj.simulator.Simulator <scenario-file>");
            System.exit(1);
        }

        try {
            Scenario scenario = Scenario.read(Path.of(args[0]));
            simulate(scenario);
        } catch (IOException | UncheckedIOException | InvalidPathException | InvalidScenarioException error) {
            System.out.println("Error: " + error.getMessage());
            System.exit(1);
        }
    }

    private static void simulate(Scenario scenario) throws IOException {
        try (BufferedWriter output = Files.newBufferedWriter(Path.of("simulation.txt"))) {
            WeatherTower tower = new WeatherTower(message -> writeLine(output, message));
            for (Flyable aircraft : scenario.aircraft()) {
                aircraft.registerTower(tower);
            }
            for (int cycle = 0; cycle < scenario.cycles() && tower.hasObservers(); cycle++) {
                tower.changeWeather();
            }
        }
    }

    private static void writeLine(BufferedWriter output, String message) {
        try {
            output.write(message);
            output.newLine();
        } catch (IOException error) {
            throw new UncheckedIOException(error);
        }
    }
}