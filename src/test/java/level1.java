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
//public class level1 {
//    private static final Logger logger = LogManager.getLogger(InputReader.class);
//
//    InputReader input;
//    OutputWriter output;
//
//    @Before
//    public void setUp() throws Exception {
//        input = new InputReader("level/level1_3.in");
//        output = new OutputWriter("level/level1.out.txt");
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
//        List<Buil> buildingheights =   city.findBuildings();
//
//        logger.info(buildingheights);
//
//        for (Integer height : buildingheights) {
//
//            output.write(height.toString());
//            output.write(" ");
//
//        }
//        output.write("\n");
//
////        output.write(line);
//
//    }
//}