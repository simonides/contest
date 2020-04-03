
public class FlightPoint implements Comparable<FlightPoint> {

    public int timestamp;
    public float lat;
    public float lng;
    public float alt;
    public String start;
    public String dest;
    public int takeoff;

    @Override
    public int compareTo(FlightPoint o) {
        if (this.start.compareTo(o.start) == 0) {
            return this.dest.compareTo(o.dest);
        }
        return this.start.compareTo(o.start);
    }
}
