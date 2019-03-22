package commons;

import java.util.Objects;

public class Position {

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position)o;
        return x == position.x &&
            y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
