import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugins.LateRegistration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import commons.InputReader;
import commons.OutputWriter;
import commons.TableUtils;

public class level0 {
    private final Logger logger = LogManager.getLogger(this.getClass());

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception { // optimize
        input = new InputReader("level/level2/level2_5.in");
        output = new OutputWriter("level/level2/level5.out.txt.a");
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

        Integer minTimestamp = Integer.MAX_VALUE;
        Integer maxTimestamp = Integer.MIN_VALUE;
        Float minLat = Float.MAX_VALUE;
        Float maxLat = Float.MIN_VALUE;

        Float minLong = Float.MAX_VALUE;
        Float maxLong = Float.MIN_VALUE;

        Float minAlt = Float.MAX_VALUE;
        Float maxAlt = Float.MIN_VALUE;

        final ArrayList<FlightPoint> points = new ArrayList<>();

        Map<String, List<FlightPoint>> fps = new HashMap<>();

        for (int i = 0; i < inputs; i++) {
            final String line = input.readLine();

            final String[] parts = line.split(",");

            int timestamp = Integer.parseInt(parts[0]);
            float lat = Float.parseFloat(parts[1]);
            float lng = Float.parseFloat(parts[2]);
            float alt = Float.parseFloat(parts[3]);

            String start = parts[4];
            String dest = parts[5];
            int takeoff = Integer.parseInt(parts[6]);

            final FlightPoint fp = new FlightPoint();
            fp.alt = alt;
            fp.lng = lng;
            fp.lat = lat;
            fp.timestamp = timestamp;
            fp.takeoff = takeoff;
            fp.start = start;
            fp.dest = dest;

            if (timestamp > maxTimestamp) maxTimestamp = timestamp;
            if (timestamp < minTimestamp) minTimestamp = timestamp;
            if (lat > maxLat) maxLat = lat;
            if (lat < minLat) minLat = lat;
            if (lng > maxLong) maxLong = lng;
            if (lng < minLong) minLong = lng;
            if (alt > maxAlt) maxAlt = alt;
            if (alt < minAlt) minAlt = alt;

            points.add(fp);

            String key = start + " " + dest;
            final List<FlightPoint> flightPoints = fps.get(key);

            if (flightPoints == null) {
                final ArrayList<FlightPoint> fpss = new ArrayList<>();
                fpss.add(fp);
                fps.put(key, fpss);
            } else {
                boolean found = false;
                for (FlightPoint flightPoint : flightPoints) {
                    if (flightPoint.takeoff == takeoff) {
                        found = true;
                    }
                }
                if (!found) {
                    flightPoints.add(fp);
                }
            }
        }

        final List<String> sorted = fps.keySet().stream().sorted().collect(Collectors.toList());

        sorted.stream().forEach(a -> {
            output.write(a + " " + fps.get(a).size() + "\n");
        });

        //
        //        output.writeLine(minTimestamp.toString(), maxTimestamp.toString());
        //        output.writeLine(minLat.toString(), maxLat.toString());
        //        output.writeLine(minLong.toString(), maxLong.toString());
        //        output.writeLine(maxAlt.toString());

    }

}
