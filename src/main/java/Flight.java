import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import commons.Timerange;
import sun.security.krb5.internal.LastReq;

public class Flight {

    public String origin;
    public String dest;
    public int id;
    public int takeoff;
    public List<Coord> coords;

    public Coord getPosition(int timestamp) {

        if (timestamp < takeoff) throw new RuntimeException("Timestamp before takeoff");

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
        final double robinF = (double)(timestamp - (takeoff + before.timeOffset)) / (double)(after.timeOffset - before.timeOffset);

        int beforeplustakeoff = before.timeOffset + takeoff;
        int afterplustakeoff = after.timeOffset + takeoff;

        double f = robinF;
        //double f = timestamp / (double)((after.timeOffset - before.timeOffset) + takeoff);

        if (f < -0.001 || f > 1.001) {
            System.out.println(f);
            throw new RuntimeException("oida: " + f);
        }

        double altI = (before.alt * (1.0 - f)) + (after.alt * f);
        double latI = (before.lat * (1.0 - f)) + (after.lat * f);
        double lngI = (before.lng * (1.0 - f)) + (after.lng * f);

        final Coord coord = new Coord();
        coord.alt = altI;
        coord.lat = latI;
        coord.lng = lngI;
        return coord;
    }

    public List<Timerange> matches(Flight other, double range) {

        int start1 = this.takeoff;
        int start2 = other.takeoff;
        int end1 = this.coords.get(this.coords.size() - 1).timeOffset + this.takeoff;
        int end2 = other.coords.get(other.coords.size() - 1).timeOffset + other.takeoff;

        final List<Timerange> ranges = new ArrayList<>();

        int firstMatch = 0;
        int lastMatch = 0;

        int timestep = 1;

        for (int time = Math.max(start1, start2); time < Math.min(end1, end2); time += timestep) {

            final Coord pos1 = this.getPosition(time);
            final Coord pos2 = other.getPosition(time);

            //4424 2878 981 223645-223651
            if (this.id == 4424 && other.id == 2878 && time == 223645) {
                //       System.out.println("bla");
            }
            //            } else {
            //                continue;
            //            }

            final double d = pos1.distance(pos2);

//            if (d > range * 5) {
//                timestep = (int)(d / (range * 2));
//                timestep = Math.min(timestep, 10);
//                timestep = Math.max(1, timestep);
//            } else {
//                timestep = 1;
//            }

            if (pos1.alt < 6000 || pos2.alt < 6000 || d <= 1000 || d >= range) {
                if (lastMatch != 0) {
                    final Timerange r = new Timerange();
                    r.start = firstMatch;
                    r.end = lastMatch;

                    ranges.add(r);
                    firstMatch = 0;
                    lastMatch = 0;
                }
                continue;
            }

            if (firstMatch == 0) firstMatch = time;
            lastMatch = time;
        }

        if (lastMatch != 0) throw new RuntimeException("Wtf where is the plane");

        return ranges;
    }

    public Flight delay(int delay) {
        final Flight fFixed = new Flight();
        fFixed.id = this.id;
        fFixed.takeoff = this.takeoff + delay;
        fFixed.coords = this.coords;
        fFixed.origin = this.origin;
        fFixed.dest = this.dest;

        return fFixed;
    }
}
