import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;
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
        input = new InputReader("level/level4/level4_5.in");
        output = new OutputWriter("level/level4/level4_5.out.actual");
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
    public void execute() throws FileNotFoundException {
        final int inputs = input.readLineInt();

        for (int i = 0; i < inputs; i++) {
            final String line = input.readLine();
            final String[] parts = line.split(" ");

            int flightId = Integer.parseInt(parts[0]);
            int queryTimestamp = Integer.parseInt(parts[1]);

            final InputReader flightFile = new InputReader("level/level4/usedFlights/" + flightId + ".csv");

            final Flight flight = new Flight();
            flight.origin = flightFile.readLine();
            flight.dest = flightFile.readLine();
            flight.takeoff = flightFile.readLineInt();
            int count = flightFile.readLineInt();
            flight.coords = new ArrayList<>();

            for (int j = 0; j < count; j++) {
                final String[] split = flightFile.readLine().split(",");

                int offset = Integer.parseInt(split[0]);
                double lat = Double.parseDouble(split[1]);
                double lng = Double.parseDouble(split[2]);
                double alt = Double.parseDouble(split[3]);

                final Coord fp = new Coord(offset, lat, lng, alt);
                flight.coords.add(fp);
            }

            final Coord position = flight.getPosition(queryTimestamp);

            output.write(position.lat + " " + position.lng + " " + position.alt + "\n");
        }
    }
}
