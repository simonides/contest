import commons.InputReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;



public class City2 {

    public class Way{
        float x0, y0, x1,y1;

        int getDotx(float ratio){
            return (int) (x0 * (1-ratio) + x1 * ratio);
        }
        int getDoty(float ratio){
            return (int) (y0 * (1-ratio) + y1 * ratio);
        }
    }

//    1 int
//    4 int
    List<Way> citymap = new ArrayList<>();
    int count;

    private static final Logger logger = LogManager.getLogger(City.class);

    public void getWays(){


    }

    public void readFile(InputReader ir){


        List<Integer> countList = ir.readIntParts();
        this.count = countList.get(0);

        for (int i = 0; i < count; i++) {
            List<String> row = ir.readStringParts();
            Way way = new Way();
            way.x0 = Integer.parseInt(row.get(0) )+ .5f;
            way.x1 = Integer.parseInt(row.get(2))+ .5f;
            way.y0 = Integer.parseInt(row.get(1))+ .5f;
            way.y1 = Integer.parseInt(row.get(3))+ .5f;
            citymap.add(way);
        }
    }


}
