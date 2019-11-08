import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import commons.InputReader;
import commons.OutputWriter;
import commons.TableUtils;

public class level1 {
    private final Logger logger = LogManager.getLogger(this.getClass());

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
        final Table<Integer, Integer, Integer> t = TableUtils.create(5, 4, 42);
        System.out.println(TableUtils.formatTable(t));
    }
}
