import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Table;

import commons.Country;
import commons.InputReader;
import commons.LandCell;
import commons.OutputWriter;
import commons.Ray;
import commons.TableUtils;

public class level4 {
    private final Logger logger = LogManager.getLogger(this.getClass());

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level4_5.in");
        output = new OutputWriter("level/level4_5.out.txt");
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

    public int rows;
    public int cols;
    public int steps;

    @Test
    public void execute() {
        final List<Integer> integers = input.readIntParts();
        Map<Long, Long> borderCount = new HashMap<>();
        Map<Integer, Country> countries = new HashMap<>();

        rows = integers.get(0);
        cols = integers.get(1);

        final int steps = input.readLineInt();

        List<Ray> rays = new ArrayList<>();

        for (int i = 0; i < steps; i++) {
            final List<Integer> ints = input.readIntParts();

            final Ray ray = new Ray(ints.get(0), ints.get(1), ints.get(2), ints.get(3));

            rays.add(ray);
        }

        for (Ray ray : rays) {
            ray.findIntersections(rows, cols);

            final Table<Integer, Integer, Integer> table = TableUtils.create(rows, cols, -1);
            int i = 0;
            for (LandCell cell : ray.cells) {
                table.put((int)cell.y, (int)cell.x, i);
                i++;
            }

            output.writeLine(ray.writeString());

          //  System.out.println(TableUtils.formatTable(table));
        }
    }
}
