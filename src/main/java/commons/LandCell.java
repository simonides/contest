package commons;

import java.util.Objects;

public class LandCell {

    public LandCell(long alt, long c, long x, long y) {
        altitude = alt;
        this.country = c;
        this.x = x;
        this.y = y;
    }

    public long altitude;
    public long country;
    public boolean isBorder;
    public long x;
    public long y;

    public void setBorder(boolean b) {
        this.isBorder = b;
    }

    public String toString() {
        if (isBorder) {
            return String.format("%d X", country);
        }
        return String.format("%d  ", country);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LandCell landCell = (LandCell)o;
        return x == landCell.x &&
            y == landCell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
