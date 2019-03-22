package commons;

import java.util.List;

public class Simulation {

    public int tick;

    private final List<Position> towerPositions;
    private final int cost;
    private final double damage;
    private final OutputWriter output;
    private final int range;
    public List<Entity> entities;
    public List<Tower> towers;
    private final int loot;

    public static long money;

    public Simulation(final List<Position> towerPositions, final int cost, final double damage, final int range, final OutputWriter output,
        final List<Entity> entities,
        List<Tower> towers,
        final int i, final int loot)
    {
        this.towerPositions = towerPositions;
        this.cost = cost;
        this.damage = damage;
        this.output = output;
        this.entities = entities;
        this.towers = towers;
        this.loot = loot;
        this.tick = 0;
        this.range = range;
        money = i;

        towers.stream().forEach(t -> {
            System.out.printf("Placing tower at [%d/%d]\n", t.x, t.y);
            output.write(String.format("%d %d %d\n", t.x, t.y, 0));
            towerPositions.remove(0);
        });
    }

    public int simulate() throws LostException {

        while (true) {

            //System.out.println("\nTick " + tick);

            final long aliveBefore = entities.stream().filter(ent -> ent.isAlive(tick)).count();

            for (Entity entity : entities) {
                entity.evaluate(tick);
            }
            for (Tower tower : towers) {
                tower.aim(entities, tick);
            }
            for (Tower tower : towers) {
                tower.fire(tick);
            }

            final long aliveAfter = entities.stream().filter(ent -> ent.isAlive(tick)).count();
            final long killed = aliveBefore - aliveAfter;
            money += killed * loot;

            final int buy = (int)money / cost;
            money -= cost * buy;

            if (towerPositions.size() > 0) {
                final Position pos = towerPositions.get(0);
                towerPositions.remove(0);
                final Tower t = new Tower(pos.x, pos.y, tick, damage, range);
                System.out.printf("new tower: %d/%d %d\n", t.x, t.y, tick);
                output.write(String.format("%d %d %d\n", t.x, t.y, tick));
                towers.add(t);
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
