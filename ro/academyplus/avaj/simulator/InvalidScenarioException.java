package ro.academyplus.avaj.simulator;

final class InvalidScenarioException extends Exception {
    private static final long serialVersionUID = 1L;

    InvalidScenarioException(int line, String message) {
        super("Line " + line + ": " + message);
    }
}