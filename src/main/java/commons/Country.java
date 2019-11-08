package commons;

import java.util.ArrayList;
import java.util.List;

public class Country {

    public Country(int id) {
        this.countryId = id;
    }

    public Double xAvg;
    public Double yAvg;

    public Integer xAvgI;
    public Integer yAvgI;

    public Integer countryId;
    public List<LandCell> cells = new ArrayList<>();

    public void calcCenter() {

        long xtotal = 0;
        long ytotal = 0;

        for (LandCell cell : cells) {
            xtotal += cell.x;
            ytotal += cell.y;
        }

        xAvg = ((double)xtotal / cells.size());
        yAvg = ((double)ytotal / cells.size());

        xAvgI = (int)(double)xAvg;
        yAvgI = (int)(double)yAvg;

        System.out.printf("country %d: %f %f\n", countryId, xAvg, yAvg);

    }

    public LandCell findCapital() {

        double minDist = Double.MAX_VALUE;
        LandCell best = null;

        for (LandCell cell : cells) {
            if (cell.isBorder) continue;
            final double distance = distance(cell);

            if (distance == minDist) {
                if (cell.y < best.y) {
                    best = cell;
                } else if (cell.y == best.y && cell.x < best.x) {
                    best = cell;
                }
            }

            if (distance < minDist) {
                best = cell;
                minDist = distance;
            }
        }

        return best;
    }

    private double distance(final LandCell cell) {
        final double a = Math.pow(Math.abs(xAvgI - cell.x), 2);
        final double b = Math.pow(Math.abs(yAvgI - cell.y), 2);

        return Math.sqrt(a + b);
    }
}
