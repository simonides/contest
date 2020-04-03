public class Coord {

    public int timeOffset;
    public double lat;
    public double lng;
    public double alt;

    public Coord() {

    }

    public Coord(int timeOffset, double lat, double lng, double alt) {
        this.timeOffset = timeOffset;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
}
