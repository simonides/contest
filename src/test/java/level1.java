import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import commons.Command;
import commons.Entity;
import commons.InputReader;
import commons.LostException;
import commons.OutputWriter;
import commons.Position;
import commons.Simulation;
import commons.Tower;

public class level1 {
    private static final Logger logger = LogManager.getLogger(InputReader.class);
    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level6/level6_1.in");
        output = new OutputWriter("level/level6/level6_1.out");
    }

    @After
    public void tearDown() {
        if (input != null) {
            input.close();
        }
        if (output != null) {
            output.close();
        }
    }

    @Test
    public void execute() {

        final List<Integer> mapSize = input.readIntParts();
        final List<Integer> coords = input.readIntParts();
        final List<Command> path = input.readCommandsLine();
        final List<String> healthSpeedStr = input.readStringParts();
        final double health = Double.parseDouble(healthSpeedStr.get(0));
        final double speed = Double.parseDouble(healthSpeedStr.get(1));
        final int loot = Integer.parseInt(healthSpeedStr.get(2));

        final int alienCount = input.readLineInt();

        List<Entity> entities = new ArrayList<>();
        List<Integer> spawnTimes = new ArrayList<>();
        for (int i = 0; i < alienCount; i++) {
            final int spawnTime = input.readLineInt();
            spawnTimes.add(spawnTime);
            final Entity entity = new Entity(mapSize.get(0), mapSize.get(1), coords.get(0), coords.get(1), i, speed, spawnTime, path, health, loot);
            entities.add(entity);
        }

        final List<String> dmgRange = input.readStringParts();
        final double damage = Double.parseDouble(dmgRange.get(0));
        final int range = Integer.parseInt(dmgRange.get(1));
        final int cost = Integer.parseInt(dmgRange.get(2));
        final int gold = input.readLineInt();

        //        final int towerCount = input.readLineInt();
        //
        //        List<Tower> towers = new ArrayList<>();
        //
        //        for (int i = 0; i < towerCount; i++) {
        //            final List<Integer> pos = input.readIntParts();
        //            final Tower tower = new Tower(pos.get(0), pos.get(1), 0, damage, range, cost);
        //            towers.add(tower);
        //        }

        final int numTowersPossible = Math.min(500, gold / cost);

        final Entity entity = entities.get(0);
        final List<Position> positions = entity.executeCommands(path);

        final List<Position> bestPositions = findBestPositions(positions, range, mapSize.get(0), mapSize.get(1));
        final List<Position> towerPositions = bestPositions.subList(0, numTowersPossible);

        final List<Tower> towers = towerPositions.stream()
            .map(pos -> new Tower(pos.x, pos.y, 0, damage, range))
            .collect(Collectors.toList());

        final Simulation sim = new Simulation(bestPositions, cost, damage, range, output, entities, towers, loot, gold - numTowersPossible * cost);

        try {
            sim.simulate();
        } catch (LostException e) {
            e.printStackTrace();
        }

        double totalDamage = 0;
        int overkilled = 0;
        for (Entity entity1 : sim.entities) {
            double entityDamage = health - Math.max(entity1.health, 0);
            totalDamage += entityDamage;

            if (entity1.health < -2 * damage) {
                overkilled++;
            }
        }

        System.out.println("Total damage caused: " + totalDamage);
        System.out.println("Overkilled: " + overkilled);

        for (Tower tower : towers) {
            //output.write(String.format("%d %d\n", tower.x, tower.y));
        }

        //        try {
        //            final int wonAt = sim.simulate();
        //            System.out.println("Won at tick " + wonAt);
        //            output.write(wonAt + "\n");
        //            output.writeLine("WIN");
        //        } catch (LostException e) {
        //            System.out.println("lost at tick " + sim.tick);
        //            output.write(sim.tick + "\n");
        //            output.writeLine("LOSS");
        //        }

        //        List<Query> queries = new ArrayList<>();
        //        final int numberOfQueries = input.readLineInt();
        //        for (int i = 0; i < numberOfQueries; i++) {
        //            final List<Integer> ints = input.readIntParts();
        //
        //            final Query query = new Query(ints.get(0), ints.get(1));
        //            queries.add(query);
        //        }

        // execute stuff

        //        for (Query query : queries) {
        //            final Entity entity = entities.get(query.alienId);
        //            entity.evaluate(query.tick);
        //
        //            final String str = String.format("%d %d %d %d\n", query.tick, query.alienId, entity.x, entity.y);
        //            System.out.printf(str);
        //            output.write(str);
        //        }
    }

    private List<Position> findBestPositions(final List<Position> path, final int range, final Integer xSize, final Integer ySize) {

        Map<Position, Integer> candidates = new HashMap<>();

        for (Integer x = 0; x < xSize; x++) {
            for (Integer y = 0; y < ySize; y++) {

                if ((x + y) % 2 == 0) continue;

                final Position checkPosition = new Position(x, y);

                if (path.contains(checkPosition)) {
                    continue;
                }

                int fieldsInRange = 0;

                for (Position pathPosition : path) {
                    final double dist = Tower.dist(checkPosition, pathPosition);
                    if (dist < range) fieldsInRange++;
                }

                if (fieldsInRange > 0) {
                    candidates.put(checkPosition, fieldsInRange);
                }
            }
        }

        final List<Position> positions = candidates.entrySet()
            .stream()
            .sorted(Comparator.comparing(Entry::getValue))
            .peek(pos -> System.out.printf("Position %d/%d has %d in range\n", pos.getKey().x, pos.getKey().y, pos.getValue()))
            .map(Entry::getKey)
            .collect(Collectors.toList());

        final List<Position> reversed = Lists.reverse(positions);

        return reversed;
    }
}
