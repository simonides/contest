import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commons.InputReader;
import commons.OutputWriter;

public class level1 {
    private static final Logger logger = LogManager.getLogger(InputReader.class);

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level1/lvl1-4.inp");
        output = new OutputWriter("level/level1/level1.out.txt");
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
        
    }
}
