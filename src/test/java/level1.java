import java.util.List;

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
        input = new InputReader("level/level1_5.in");
        output = new OutputWriter("level/level1_5.out.txt");
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
        int rows = integers.get(0);
        int cols = integers.get(1);

        Table<Integer, Integer, Integer> world = TableUtils.create(rows, cols, 0);

        long min = Integer.MAX_VALUE;
        long max = Integer.MIN_VALUE;
        long total = 0;

        for (int y = 0; y < rows; y++) {
            final List<Integer> integers1 = input.readIntParts();
            for (int x = 0; x < cols; x++) {
                final Integer val = integers1.get(x);
                world.put(x, y, val);
                if (val < min) min = val;
                if (val > max) max = val;
                total += val;
            }
        }

        long avg = total / (rows * cols);

        output.writeLine(String.format("%d %d %d", min, max, avg));
    }
}
