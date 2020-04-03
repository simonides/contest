import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commons.InputReader;
import commons.OutputWriter;
import commons.Timerange;

public class level3 {
    private final Logger logger = LogManager.getLogger(this.getClass());

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception { // optimize
        input = new InputReader("level/level5/level5_2.in");
        output = new OutputWriter("level/level5/level5_2.out");
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
        final double transferRange = Double.parseDouble(input.readLine());
        final int inputs = input.readLineInt();

        List<Flight> flights = new ArrayList<>();

        for (int i = 0; i < inputs; i++) {
            final int flightId = input.readLineInt();

            final InputReader flightFile = new InputReader("level/level5/usedFlights/" + flightId + ".csv");

            final Flight flight = new Flight();
            flight.origin = flightFile.readLine();
            flight.dest = flightFile.readLine();
            flight.takeoff = flightFile.readLineInt();
            flight.id = flightId;
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

            flights.add(flight);
        }

        //        4424 2878 981 223645-223651

        //        Flight f4424 = flights.get(2);
        //        Flight f2878 = flights.get(1);
        //
        //        Flight fDelayed = f2878.delay(981);
        //
        //        f4424.matches(fDelayed, transferRange);

        final CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();

        flights.parallelStream().forEach(f -> {

            String threadStr = "";

            for (int j = 0; j < flights.size(); j++) {
                final Flight flight1 = f;
                final Flight flight2 = flights.get(j);

                if (flight1.dest.equals(flight2.dest)) continue;

                System.out.println("simulating " + flight1.id + " " + flight2.id);

                for (int delay = 0; delay <= 3600; delay++) {

                    // System.out.println("simulating " + i + " " + j + " delay: " + delay);

                    final Flight fFixed = flight2.delay(delay);

                    final List<Timerange> matches = flight1.matches(fFixed, transferRange);

                    if (matches.isEmpty()) continue;

                    threadStr += flight1.id + " " + fFixed.id + " " + delay + " ";
                    for (Timerange match : matches) {
                        if (match.start == match.end) {
                            threadStr += match.start + " ";
                        } else {
                            threadStr += match.start + "-" + match.end + " ";
                        }
                    }
                    threadStr += "\n";
                }
            }

            strings.add(threadStr);
        });

        strings.stream().sorted().forEach(s -> output.write(s));

        //        for (int i = 0; i < flights.size(); i++) {
        //            for (int j = 0; j < flights.size(); j++) {
        //                final Flight flight1 = flights.get(i);
        //                final Flight flight2 = flights.get(j);
        //
        //                if (flight1.dest.equals(flight2.dest)) continue;
        //
        //                System.out.println("simulating " + i + " " + j);
        //
        //                for (int delay = 0; delay <= 3600; delay++) {
        //
        //                    // System.out.println("simulating " + i + " " + j + " delay: " + delay);
        //
        //                    final Flight fFixed = flight2.delay(delay);
        //
        //                    final List<Timerange> matches = flight1.matches(fFixed, transferRange);
        //
        //                    if (matches.isEmpty()) continue;
        //
        //                    output.write(flight1.id + " " + fFixed.id + " " + delay + " ");
        //                    for (Timerange match : matches) {
        //                        if (match.start == match.end) {
        //                            output.write(match.start + " ");
        //                        } else {
        //                            output.write(match.start + "-" + match.end + " ");
        //                        }
        //                    }
        //                    output.write("\n");
        //                }
        //            }
        //        }
    }
}
