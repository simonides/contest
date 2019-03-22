package commons;

import java.util.List;

public class Simulation {

    public int tick;

    public List<Entity> entities;
    public List<Tower> towers;

    public Simulation(final List<Entity> entities, List<Tower> towers) {
        this.entities = entities;
        this.towers = towers;
        this.tick = 0;

        towers.stream().forEach(t -> System.out.printf("Placing tower at [%d/%d]\n", t.x, t.y));
    }

    public int simulate() throws LostException {

        while (true) {

            //System.out.println("\nTick " + tick);

            for (Entity entity : entities) {
                entity.evaluate(tick);
            }
            for (Tower tower : towers) {
                tower.aim(entities, tick);
            }
            for (Tower tower : towers) {
                tower.fire(tick);
            }

            //            entities.stream()
            //                .filter(ent -> ent.isSpawned(tick))
            //                .forEach(ent -> System.out.printf("Entity %d [%d/%d] health: %f\n", ent.alienId, ent.x, ent.y, ent.health));

            final int tickFu = tick;
            final boolean stillAlive = entities.stream().anyMatch(ent -> ent.isAlive(tickFu));
            if (!stillAlive) {
                return tick;
            }

            tick++;
        }

    }
}
