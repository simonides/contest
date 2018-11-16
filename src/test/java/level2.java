//import commons.InputReader;
//import commons.OutputWriter;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class level2 {
//    private static final Logger logger = LogManager.getLogger(InputReader.class);
//
//    InputReader input;
//    OutputWriter output;
//
//    @Before
//    public void setUp() throws Exception {
//        input = new InputReader("level/level2_3.in");
//        output = new OutputWriter("level/level2.out");
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
//        Routing city2 = new Routing();
//        city2.readFile(input);
//        for (Routing.Way way : city2.citymap) {
//            int dotx = way.getDotx();
//            int doty = way.getDoty();
//            output.write(Integer.toString(dotx) + " " + Integer.toString(doty)+ "\n");
//        }
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