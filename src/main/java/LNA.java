public class LNA {

    public double lat;
    public double lng;
    public double alt;

    public ECEF toECEF() {
        final ECEF ecef = new ECEF();

        double a = 6371000; // radius
        double e = 0;

        double latG = lat / 180 * Math.PI;
        double lngG = lng / 180 * Math.PI;
        double h = alt;

        double e2 = Math.pow(e, 2);

        double slat = Math.sin(latG);
        double clat = Math.cos(latG);

        double N = a / Math.sqrt(1 - e2 * slat * slat);

        double x = (N + h) * clat * Math.cos(lngG);
        double y = (N + h) * clat * Math.sin(lngG);
        double z = (N * (1 - e2) + h) * slat;

        ecef.x = x;
        ecef.y = y;
        ecef.z = z;

        return ecef;
    }
}
