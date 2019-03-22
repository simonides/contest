package commons;

import java.util.List;

public class Simulation {

    public int tick;

    public List<Entity> entities;

    public Simulation(final List<Entity> entities) {
        this.entities = entities;
    }

    public void nextTick() {

    }
}
