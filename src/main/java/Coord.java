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

    public double distance(Coord other) {
        final LNA lna1 = new LNA();
        lna1.alt = this.alt;
        lna1.lng = this.lng;
        lna1.lat = this.lat;
        final ECEF ecef1 = lna1.toECEF();

        final LNA lna2 = new LNA();
        lna2.alt = other.alt;
        lna2.lng = other.lng;
        lna2.lat = other.lat;
        final ECEF ecef2 = lna2.toECEF();

        double distance = Math.sqrt(Math.pow(ecef2.x - ecef1.x, 2) + Math.pow(ecef2.y - ecef1.y, 2) + Math.pow(ecef2.z - ecef1.z, 2));
        return distance;
        // d = sqrt((x2 - x1)² + (y2 - y1)² + (z2 - z1)²)
    }
}
