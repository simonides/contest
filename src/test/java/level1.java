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
import commons.OutputWriter;
import commons.Query;

public class level1 {
    private static final Logger logger = LogManager.getLogger(InputReader.class);

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level3/level3_5.in");
        output = new OutputWriter("level/level3/level3_5_x.out");
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
        final double speed = Double.parseDouble(input.readStringParts().get(0));
        final int alienCount = input.readLineInt();

        List<Entity> entities = new ArrayList<>();
        List<Integer> spawnTimes = new ArrayList<>();
        for (int i = 0; i < alienCount; i++) {
            final int spawnTime = input.readLineInt();
            spawnTimes.add(spawnTime);
            final Entity entity = new Entity(coords.get(0), coords.get(1), i, speed, spawnTime, path);
            entities.add(entity);
        }

        List<Query> queries = new ArrayList<>();
        final int numberOfQueries = input.readLineInt();
        for (int i = 0; i < numberOfQueries; i++) {
            final List<Integer> ints = input.readIntParts();

            final Query query = new Query(ints.get(0), ints.get(1));
            queries.add(query);
        }

        // execute stuff

        for (Query query : queries) {
            final Entity entity = entities.get(query.alienId);
            entity.evaluate(query.tick);

            final String str = String.format("%d %d %d %d\n", query.tick, query.alienId, entity.x, entity.y);
            System.out.printf(str);
            output.write(str);
        }
    }
}
