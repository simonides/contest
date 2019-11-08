import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Table;
import com.google.common.io.CountingInputStream;

import commons.Country;
import commons.InputReader;
import commons.LandCell;
import commons.OutputWriter;
import commons.TableUtils;

public class level3 {
    private final Logger logger = LogManager.getLogger(this.getClass());

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level3_5.in");
        output = new OutputWriter("level/level3_5.out.txt");
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

    @Test
    public void execute() {
        final List<Integer> integers = input.readIntParts();
        Map<Long, Long> borderCount = new HashMap<>();
        Map<Integer, Country> countries = new HashMap<>();

        rows = integers.get(0);
        cols = integers.get(1);
        int textCols = cols * 2;

        Table<Integer, Integer, LandCell> world = TableUtils.create(cols, rows, new LandCell(0, -1, 0, 0));

        long min = Integer.MAX_VALUE;
        long max = Integer.MIN_VALUE;
        long total = 0;

        for (int y = 0; y < rows; y++) {
            final List<Integer> integers1 = input.readIntParts();
            assertThat(integers1.size(), is(textCols));

            for (int x = 0; x < cols; x++) {
                final Integer alt = integers1.get(x * 2);
                final Integer countryID = integers1.get(x * 2 + 1);
                final LandCell cell = new LandCell(alt, countryID, x, y);
                world.put(y, x, cell);

                final Country country = countries.getOrDefault(countryID, new Country(countryID));
                country.cells.add(cell);
                countries.put(countryID, country);

                if (alt < min) min = alt;
                if (alt > max) max = alt;
                total += alt;
            }
        }

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                final LandCell landCell = world.get(y, x);
                if (isBorderCell(x, y, world)) {
                    landCell.setBorder(true);
                    Long count = borderCount.getOrDefault(landCell.country, 0L);
                    count++;
                    borderCount.put(landCell.country, count);
                }
            }
        }

        final String s = TableUtils.formatTable(world);

        for (Entry<Integer, Country> longCountryEntry : countries.entrySet()) {
            final Country country = longCountryEntry.getValue();
            country.calcCenter();
            final LandCell capital = country.findCapital();
            output.writeLine(String.format("%d %d", capital.x, capital.y));
        }

        System.out.println(s);
    }

    public boolean isBorderCell(int x, int y, Table<Integer, Integer, LandCell> t) {

        if (x == 0 || x == cols - 1) return true;
        if (y == 0 || y == rows - 1) return true;

        final LandCell bot = t.get(y + 1, x);
        final LandCell top = t.get(y - 1, x);
        final LandCell left = t.get(y, x - 1);
        final LandCell right = t.get(y, x + 1);
        final LandCell self = t.get(y, x);

        if (self.country != right.country || self.country != left.country || self.country != top.country || self.country != bot.country) return true;

        return false;
    }
}
