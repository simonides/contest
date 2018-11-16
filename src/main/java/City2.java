import commons.InputReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;



public class City2 {

    public class Way{
        float x0, y0, x1,y1;
        float ratio;

//        float getLenght(){
//            float dx = x1 - x0;
//            float dy = y1 - y0;
//
//            double length = Math.sqrt((dx *dx + dy *dy));
//            return (float) length;
//        }
//
//        int getDotx(){
//
//            float lenght = getLenght();
//            double nx = (x1 -x0) / getLenght();
//            return (int) (x0 + nx);
//        }
//        int getDoty(){
//            float lenght = getLenght();
//            double nx = (y1 -y0) / getLenght();
//            return (int) (y0 + nx);
//        }

        int getDotx(){
            return (int) (x0 * (1-ratio) + x1 * ratio);
        }
        int getDoty(){
            return (int) (y0 * (1-ratio) + y1 * ratio);
        }
    }

//    1 int
//    4 int 1 float
    List<Way> citymap = new ArrayList<>();
    int count;

    private static final Logger logger = LogManager.getLogger(City.class);


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
            way.ratio = Float.parseFloat(row.get(4));

            citymap.add(way);
        }
    }


}
