import commons.InputReader;
import jdk.internal.util.xml.impl.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class City {
    List<List<Integer>> citymap = new ArrayList<>();
    int width, height;

    private static final Logger logger = LogManager.getLogger(City.class);


    public void readFile(InputReader ir){


        List<Integer> size = ir.readIntParts();
        height= size.get(0);
        width = size.get(1);
        for (int y  = 0; y < height; y++) {
            List<Integer> row = ir.readIntParts();
            citymap.add(row);
//            logger.info(row);
        }


    }

    public List<Integer> findBuildings(){

        List<Integer> building_heights = new ArrayList<>();
        for (int y = 0; y < citymap.size(); y++) {
            List<Integer> row = citymap.get(y);
            for (int x = 0; x < row.size(); x++) {
                int field = row.get(x);
                int left = -1;
                if (x != 0) {
                    left = row.get(x - 1);
                }

                int top = -1
                        ;
                if (y!=0){
                    top = citymap.get(y-1).get(x);
                }

                if (field != 0 && field != top && field != left){
                    building_heights.add(field);

                }

            }
        }
        Set<Integer> buildings = new HashSet<>(building_heights);

        return buildings.stream().sorted().collect(Collectors.toList());

    }
}
