package ro.academyplus.avaj.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tower {
    private final List<Flyable> observers = new ArrayList<>();
    private final Consumer<String> logger;

    public Tower() {
        this(message -> { });
    }

    Tower(Consumer<String> logger) {
        this.logger = logger;
    }

    public void register(Flyable flyable) {
        if (!observers.contains(flyable)) {
            observers.add(flyable);
            log("Tower says: " + flyable + " registered to weather tower.");
        }
    }

    public void unregister(Flyable flyable) {
        if (observers.remove(flyable)) {
            log("Tower says: " + flyable + " unregistered from weather tower.");
        }
    }

    protected void conditionChanged() {
        for (Flyable observer : List.copyOf(observers)) {
            observer.updateConditions();
        }
    }

    boolean hasObservers() {
        return !observers.isEmpty();
    }

    void log(String message) {
        logger.accept(message);
    }
}