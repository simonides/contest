package commons;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Tower {

    private enum Status {
        Locked,
        Seeking;
    }

    private Entity targetAlien;

    private int x;
    private int y;
    private int spawnTick;
    private double damage;

    private int range;

    public Tower(final int x, final int y, final int spawnTick, final double damage, final int range) {
        this.x = x;
        this.y = y;
        this.spawnTick = spawnTick;
        this.damage = damage;
        this.range = range;
    }

    public void aim(List<Entity> entities, int tick) {
        if (targetAlien != null) {
            if (!isValidTarget(targetAlien, tick)) {
                targetAlien = null;
            }
        }

        if (targetAlien == null) {
            targetAlien = getNearestTarget(entities, tick);
        }
    }

    public void fire(int tick) {
        if (targetAlien != null) {
//            System.out.printf("Tower [%d/%d] shooting at %d\n", x, y, targetAlien.alienId);
            targetAlien.shoot(tick, damage);
        }
    }

    private Entity getNearestTarget(List<Entity> entities, int tick) {
        final Stream<Entity> sorted = entities.stream()
            .filter(ent -> isValidTarget(ent, tick))
            .sorted(Comparator.comparing(this::dist));

        return sorted.findFirst().orElse(null);
    }

    private boolean isValidTarget(Entity entity, int tick) {
        if (!inRange(entity)) {
            return false;
        }
        if (entity.health <= 0) {
            return false;
        }
        if (tick < entity.spawnTime || tick == 0) {
            return false;
        }
        return true;
    }

    private boolean inRange(Entity entity) {
        return dist(entity) <= range;

    }

    private double dist(Entity entity) {

        double xDist = Math.abs(entity.x - x);
        double yDist = Math.abs(entity.y - y);

        return Math.sqrt(xDist * xDist + yDist * yDist);
    }
}
