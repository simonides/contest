//
//import commons.InputReader;
//        import commons.OutputWriter;
//        import org.apache.logging.log4j.LogManager;
//        import org.apache.logging.log4j.Logger;
//        import org.junit.After;
//        import org.junit.Before;
//        import org.junit.Test;
//
//public class level3 {
//    private static final Logger logger = LogManager.getLogger(InputReader.class);
//
//    InputReader input;
//    OutputWriter output;
//
//    @Before
//    public void setUp() throws Exception {
//        input = new InputReader("level/level3_2.in");
//        output = new OutputWriter("level/level3.out");
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
//
//
//        for (Routing.Way way : city2.citymap) {
//            int lastx = -1;
//            int lasty = -1;
//
//            for (float i = 0; i <= 1; i += 0.001f) {
//                int dotx = way.getDotx(i);
//                int doty = way.getDoty(i);
//                if (dotx != lastx || doty != lasty){
//                    if(lastx != -1){
//                        output.write(" ");
//                    }
//                    output.write(Integer.toString(dotx) + " " + Integer.toString(doty));
//                    lastx = dotx;
//                    lasty = doty;
//                }
//
//            }
//            output.write("\n");
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