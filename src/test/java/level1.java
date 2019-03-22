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

public class level1 {
    private static final Logger logger = LogManager.getLogger(InputReader.class);

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level1/level1_5.in");
        output = new OutputWriter("level/level1/level1_5.out");
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

        final List<Integer> integers = input.readIntParts();

        final Entity entity = new Entity(integers.get(0), integers.get(1));

        final List<Command> commands = input.readCommandsLine();

        entity.executeCommands(commands);

        System.out.println(entity.x + " " + entity.y);

        output.printfLine(entity.x + " " + entity.y);
    }
}
