import java.util.List;

public class Flight {

    public String origin;
    public String dest;
    public int id;
    public int takeoff;
    public List<Coord> coords;

    public Coord getPosition(int timestamp) {

        Coord before = coords.get(0);
        Coord after = coords.get(1);

        for (Coord coord : coords) {
            if (coord.timeOffset + takeoff > timestamp) {
                after = coord;
                break;
            }
            before = coord;
        }

        int duration = after.timeOffset - before.timeOffset;
        final double jakobF = (double)(timestamp - takeoff) / duration + takeoff;
        final double robinF = (double)(timestamp - (takeoff + before.timeOffset)) / (double)(after.timeOffset - before.timeOffset);

        int beforeplustakeoff = before.timeOffset + takeoff;
        int afterplustakeoff = after.timeOffset + takeoff;

        double f = robinF;
        //double f = timestamp / (double)((after.timeOffset - before.timeOffset) + takeoff);

        if (f < 0 || f > 1) throw new RuntimeException("oida: " + f);

        double altI = (before.alt * (1.0 - f)) + (after.alt * f);
        double latI = (before.lat * (1.0 - f)) + (after.lat * f);
        double lngI = (before.lng * (1.0 - f)) + (after.lng * f);

        final Coord coord = new Coord();
        coord.alt = altI;
        coord.lat = latI;
        coord.lng = lngI;
        return coord;
    }
}
