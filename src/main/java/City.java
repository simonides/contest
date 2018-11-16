import commons.InputReader;
import jdk.internal.util.xml.impl.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.FactoryConfigurationError;
import java.util.*;
import java.util.stream.Collectors;

public class City {

    public class Hotspot{
        int  x, y;
        private int hx, hy;
        private int mx, my;

        int buildingHeight = 0;
        List<Hotspot> attatchedHotspots = new ArrayList<>();


        int getCenterX(){
            for (Hotspot ahs : attatchedHotspots) {
                if (ahs.hx > hx){
                    hx = ahs.hx;
                }
            }
            for (Hotspot ahs : attatchedHotspots) {
                if (ahs.mx < mx){
                    mx = ahs.mx;
                }
            }
            return (int)   ( mx + (( hx - mx ) / 2.f));
        }

        int getCenterY(){
            for (Hotspot ahs : attatchedHotspots) {
                if (ahs.my < my){
                    my = ahs.my;
                }
            }
            for (Hotspot ahs : attatchedHotspots) {
                if (ahs.hy > hy){
                    hy = ahs.hy;
                }
            }
            return (int)   ( my + (( hy - my ) / 2.f));
        }

        public void attach(Hotspot other){
            attatchedHotspots.add(other);
        }

        public boolean overlaps(Hotspot other){


            if(other.attatchedHotspots.size() > 0) {
                throw new RuntimeException("ohje");
            }

            if(buildingHeight != other.buildingHeight) {
                return false;
            }


            for (Hotspot h : attatchedHotspots){
                if(h.overlaps(other)) {
                    return true;
                }
            }

            int dx = Math.abs(other.x - x);
            int dy = Math.abs(other.y - y);

//            if(dx < hotspotsize - 1 && dy < hotspotsize - 1){
//                return true;
//            }


            if(dx <= hotspotsize && dy <= hotspotsize){
                if(dx == hotspotsize && dy == hotspotsize){
                    return false;
                }
                return true;
            }
//            if(dx < hotspotsize && dy < hotspotsize ){
//                return true;
//            }
//            if(dx < hotspotsize - 1 && dy < hotspotsize){
//                return true;
//            }

            return false;
        }
    }

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
    int width, height, hotspotsize;

    private static final Logger logger = LogManager.getLogger(City.class);


    public void readFile(InputReader ir){


        List<Integer> size = ir.readIntParts();
        height= size.get(0);
        width = size.get(1);
        hotspotsize = size.get(2);

        for (int y  = 0; y < height; y++) {
            List<Integer> row = ir.readIntParts();
            citymap.add(row);
//            logger.info(row);
        }


    }

    private List<Hotspot> filterHotspots(List<Hotspot> hotspots){
        List<Hotspot> filteredHotspots = new ArrayList<>();

        for (Hotspot hotspot : hotspots){

            boolean attached = false;
            for(Hotspot filteredHotspot : filteredHotspots){

                if(filteredHotspot.overlaps(hotspot)){
                    filteredHotspot.attach(hotspot);
                    attached = true;
                    break;
                }

            }
            if (!attached){
                logger.info("***********************************************");
                filteredHotspots.add(hotspot);
            }
        }

        return filteredHotspots;
    }

    public List<Hotspot> findHotspots()
    {
        ArrayList<Hotspot> hotspots = new ArrayList<>();
        for (int y = 0; y <= citymap.size() - hotspotsize; y++) {
            List<Integer> row = citymap.get(y);

            for (int x = 0; x <= row.size()- hotspotsize; x++) {

                int field = row.get(x);
                boolean ok = field != 0;

                for (int yy = 0; yy < hotspotsize; yy++) {
                    for (int xx = 0; xx < hotspotsize; xx++) {
                        int otherField = citymap.get(y+yy).get(x+xx);
                        if (otherField != field){
                            ok  = false;
                            break;
                        }
                    }
                }
                if (ok){
                    Hotspot hotspot = new Hotspot();
                    hotspot.x = x;
                    hotspot.mx = x;
                    hotspot.hx = x + hotspotsize -1;
                    hotspot.y = y;
                    hotspot.my = y;
                    hotspot.hy = y + hotspotsize -1;
                    hotspot.buildingHeight = field;
                    hotspots.add(hotspot);
                }
            }
        }
        return filterHotspots(hotspots);
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
