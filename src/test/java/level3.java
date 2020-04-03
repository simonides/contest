import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commons.InputReader;
import commons.OutputWriter;

public class level3 {
    private final Logger logger = LogManager.getLogger(this.getClass());

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception { // optimize
        input = new InputReader("level/level3/level3_5.in");
        output = new OutputWriter("level/level3/level3_5.out");
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
        final int inputs = input.readLineInt();

        for (int i = 0; i < inputs; i++) {
            final String line = input.readLine();
            final String[] parts = line.split(" ");

            double lat = Double.parseDouble(parts[0]);
            double lng = Double.parseDouble(parts[1]);
            double alt = Double.parseDouble(parts[2]);

            final LNA lna = new LNA();
            lna.lat = lat;
            lna.lng = lng;
            lna.alt = alt;

            final ECEF ecef = lna.toECEF();

            output.write(ecef.x + " " + ecef.y + " " + ecef.z + "\n");
        }
    }
}
