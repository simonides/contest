import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commons.Command;
import commons.Entity;
import commons.InputReader;
import commons.LostException;
import commons.OutputWriter;
import commons.Simulation;
import commons.Tower;

public class level1 {
    private static final Logger logger = LogManager.getLogger(InputReader.class);
    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level4/level4_3.in");
        output = new OutputWriter("level/level4/level4_3.out");
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

        final int alienCount = input.readLineInt();

        List<Entity> entities = new ArrayList<>();
        List<Integer> spawnTimes = new ArrayList<>();
        for (int i = 0; i < alienCount; i++) {
            final int spawnTime = input.readLineInt();
            spawnTimes.add(spawnTime);
            final Entity entity = new Entity(mapSize.get(0), mapSize.get(1), coords.get(0), coords.get(1), i, speed, spawnTime, path, health);
            entities.add(entity);
        }

        final List<String> dmgRange = input.readStringParts();
        final double damage = Double.parseDouble(dmgRange.get(0));
        final int range = Integer.parseInt(dmgRange.get(1));

        final int towerCount = input.readLineInt();

        List<Tower> towers = new ArrayList<>();

        for (int i = 0; i < towerCount; i++) {
            final List<Integer> pos = input.readIntParts();
            final Tower tower = new Tower(pos.get(0), pos.get(1), 0, damage, range);
            towers.add(tower);
        }

        final Simulation sim = new Simulation(entities, towers);

        try {
            final int wonAt = sim.simulate();
            System.out.println("Won at tick " + wonAt);
            output.write(wonAt + "\n");
            output.writeLine("WIN");
        } catch (LostException e) {
            System.out.println("lost at tick " + sim.tick);
            output.write(sim.tick + "\n");
            output.writeLine("LOSS");
        }

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
}
