import commons.InputReader;
import commons.OutputWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class level5 {
    private static final Logger logger = LogManager.getLogger(InputReader.class);

    InputReader input;
    OutputWriter output;

    @Before
    public void setUp() throws Exception {
        input = new InputReader("level/level5_2.in");
        output = new OutputWriter("level/level5.out");
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

        City city = new City();
        city.readFile(input);
        List<City.Hotspot> hotspots = city.findHotspots();
        List<City.Hotspot> filteredHotspots = new ArrayList<>();

        for (City.Hotspot hs : hotspots) {
            Integer field
                    = city.citymap.get(hs.getCenterY()).get(hs.getCenterX());
            if (field == hs.buildingHeight){
                filteredHotspots.add(hs);
            }
        }
//        System.out.println("hallo");


        List<City.Hotspot> sortedHotspots= filteredHotspots.stream()
                .sorted((a, b) -> {
                    if (a.getCenterY() == b.getCenterY()) {
                        return   a.getCenterX() - b.getCenterX();
                    }
                    return   (a.getCenterY() - b.getCenterY()) * 10000;

//                    if (a.getCenterY() < b.getCenterY()) {
//                        return -1;
//                    } else if (a.getCenterY() > b.getCenterY()) {
//                        return 1;
//                    }
//
//                    if (a.getCenterX() < b.getCenterX()) {
//                        return -1;
//                    }
//                    if (a.getCenterX() > b.getCenterX()) {
//                        return 1;
//                    }
//                    return 0;
                }).collect(Collectors.toList());
        int idx = 0;

        for (City.Hotspot hotspot : sortedHotspots) {

            if (idx != 0){
                output.write(" ");
            }

            output.write(Integer.toString(idx) + " " + Integer.toString(hotspot.getCenterY())
                    + " " + Integer.toString(hotspot.getCenterX()) );

            ++idx ;
        }
    }
}