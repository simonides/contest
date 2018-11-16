import commons.InputReader;
import jdk.internal.util.xml.impl.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class City {

    public class Building{
        int x, y, hx, hy = 0;

        int getWidth(){
            return  (hx - x) +1;
        }

        int getHeight(){
            return (hy -y ) +1;
        }
        public boolean isHotspot(){
            return (getHeight() >= 4 && getWidth() >=4);
        }

        int getCenterX(){
            float res =  x + ((hx - x) /2.f);
            return (int) res;
//            return x + ((int)getWidth() / 2);
        }

        int getCenterY(){
            float res = y + (( hy -y ) / 2.f);
            return (int) res;
//            return y + ((int) getHeight()/2);
        }
    }

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

    public List<Building> findBuildings(){

        List<Building> building_heights = new ArrayList<>();

        for (int y = 0; y < citymap.size(); y++) {
            List<Integer> row = citymap.get(y);
            for (int x = 0; x < row.size(); x++) {
                int field = row.get(x);
                int left = -1;
                if (x != 0) {
                    left = row.get(x - 1);
                }

                int top = -1;
                if (y!=0){
                    top = citymap.get(y-1).get(x);
                }

                if (field != 0 && field != top && field != left){
                    Building building = new Building();
                    building.x = x;
                    building.hx = x;

                    building.y = y;
                    building.hy = y;

                    for (int nx = x; nx < row.size(); nx++) {
                        Integer neighbour = row.get(nx);
                        if (neighbour == field){
                            building.hx = nx;
                        }
                        else{
                            break;
                        }
                    }
                    for (int ny = y; ny < citymap.size(); ny++) {
                        Integer neighbour = citymap.get(ny).get(x);
                        if (neighbour == field){
                            building.hy = ny;
                        }
                        else{
                            break;
                        }
                    }

                    building_heights.add(building);
                }
            }
        }

    return  building_heights;

    }
}
