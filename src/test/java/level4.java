//import commons.InputReader;
//import commons.OutputWriter;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class level4 {
//    private static final Logger logger = LogManager.getLogger(InputReader.class);
//
//    InputReader input;
//    OutputWriter output;
//
//    @Before
//    public void setUp() throws Exception {
//        input = new InputReader("level/level4_3.in");
//        output = new OutputWriter("level/level4.out");
//    }
//
//    @After
//    public void tearDown() {
//        if (input != null) {
//            input.close();
//        }
//        if (output != null) {
//            output.close();
//        }
//    }
//
//    @Test
//    public void execute() {
//
//        City city = new City();
//        city.readFile(input);
//        List<City.Building> buildings = city.findBuildings();
//        List<City.Building> sortedHotspots= buildings.stream().filter(b -> b.isHotspot())
//                .sorted((a, b) -> {
//                    if (a.getCenterY() < b.getCenterY()) {
//                        return -1;
//                    } else if (a.getCenterY() > b.getCenterY()) {
//                        return 1;
//                    }
//
//                    if (a.getCenterX() < b.getCenterX()) {
//                        return -1;
//                    }
//                    return 1;
//                }).collect(Collectors.toList());
//        int idx = 0;
//
//        for (City.Building hotspot : sortedHotspots) {
//
//            if (idx != 0){
//                output.write(" ");
//            }
//
//            output.write(Integer.toString(idx) + " " + Integer.toString(hotspot.getCenterY())
//                    + " " + Integer.toString(hotspot.getCenterX()) );
//
//            ++idx ;
//        }
//
//
////        logger.info(buildingheights);
////
////        for (Integer height : buildingheights) {
////
////            output.write(height.toString());
////            output.write(" ");
////
////        }
////        output.write("\n");
//
////        output.write(line);
//
//    }
//}