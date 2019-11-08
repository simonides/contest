package commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ray {

    public int cellIndex = 1;

    public int x;
    public int y;
    public int vx;
    public int vy;

    public double dy;
    public double dx;

    public int invertinator = 1;

    public boolean isHorizontalDominant;

    public Ray(int x, int y, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

        this.dx = (double)vx / Math.abs(vy);
        this.dy = (double)vy / Math.abs(vx);

        this.isHorizontalDominant = Math.abs(vx) >= Math.abs(vy);
    }

    public List<LandCell> cells = new ArrayList<>();

    public void findIntersectionsX(final int rows, final int cols) {
        System.out.println("xdom");
        double currentX;
        if (vx >= 0) {
            currentX = x + 0.5;
        } else {
            currentX = x - 0.5;
        }
        double currentY = y + dy / 2;

        cells.add(new LandCell(-1, -1, x, y));

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            final long roundedY = Math.round(currentY);

            if (vx >= 0) {
                if (vy >= 0) {
                    if (roundedY == currentY + 0.5) {
                        cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY - 1));
                        cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY - 1));
                    }
                    cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY));
                    cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY));
                } else {
                    cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY));
                    cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY));
                    if (roundedY == currentY - 0.5) {
                        cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY - 1));
                        cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY - 1));
                    }

                }
            } else {
                if (vy >= 0) {
                    if (roundedY == currentY + 0.5) {
                        cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY - 1));
                        cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY - 1));
                    }
                    cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY));
                    cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY));
                } else {
                    cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY));
                    cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY));
                    if (roundedY == currentY - 0.5) {
                        cells.add(new LandCell(-1, -1, (int)Math.ceil(currentX), (int)roundedY - 1));
                        cells.add(new LandCell(-1, -1, (int)Math.floor(currentX), (int)roundedY - 1));
                    }
                }
            }

            if (vx < 0) {
                currentX--;
            } else {
                currentX++;
            }
            currentY = y + dy / 2 + dy * i;

            if ((currentX > cols + 0.5) || (currentX < -0.5)) break;
            if ((currentY > rows + 0.5) || (currentY < -0.5)) break;
        }
    }

    public void findIntersectionsY(final int rows, final int cols) {

        System.out.println("ydom");

        double currentY;
        if (vy >= 0) {
            currentY = y + 0.5;
            invertinator = 1;
        } else {
            currentY = y - 0.5;
        }
        double currentX = x + dx / 2;

        cells.add(new LandCell(-1, -1, x, y));

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            final long roundedX = Math.round(currentX);

            if (vy >= 0) {
                if (vx >= 0) {
                    if (roundedX == currentX + 0.5) {
                        addUnique((int)roundedX - 1, (int)Math.floor(currentY));
                        addUnique((int)roundedX - 1, (int)Math.ceil(currentY));
                    }
                    addUnique((int)roundedX, (int)Math.floor(currentY));
                    addUnique((int)roundedX, (int)Math.ceil(currentY));
                } else {
                    addUnique((int)roundedX, (int)Math.floor(currentY));
                    addUnique((int)roundedX, (int)Math.ceil(currentY));
                    if (roundedX == currentX - 0.5) {
                        addUnique((int)roundedX - 1, (int)Math.floor(currentY));
                        addUnique((int)roundedX - 1, (int)Math.ceil(currentY));
                    }
                }
            } else {
                if (vx >= 0) {
                    if (roundedX == currentX + 0.5) {
                        addUnique((int)roundedX - 1, (int)Math.ceil(currentY));
                        addUnique((int)roundedX - 1, (int)Math.floor(currentY));
                    }
                    addUnique((int)roundedX, (int)Math.ceil(currentY));
                    addUnique((int)roundedX, (int)Math.floor(currentY));
                } else {
                    addUnique((int)roundedX, (int)Math.ceil(currentY));
                    addUnique((int)roundedX, (int)Math.floor(currentY));
                    if (roundedX == currentX - 0.5) {
                        addUnique((int)roundedX - 1, (int)Math.ceil(currentY));
                        addUnique((int)roundedX - 1, (int)Math.floor(currentY));
                    }
                }
            }

            if (vy < 0) {
                currentY--;
            } else {
                currentY++;
            }
            currentX = x + i * dx + dx / 2;
            if ((currentX > cols + 3) || (currentX < -3.5)) break;
            if ((currentY > rows + 3) || (currentY < -3.5)) break;
        }
    }

    public void addUnique(int x, int y) {

        //        final Optional<LandCell> any = cells.stream()
        //            .filter(cell -> cell.x == x && cell.y == y)
        //            .findAny();
        //        if (any.isEmpty()) {

        if (x == 3 && y == 4) {
            System.out.println("lol");
        }

        cells.add(new LandCell(-1, -1, x, y));
        //        }
    }

    public void findIntersections(final int rows, final int cols) {

        if (isHorizontalDominant) {
            findIntersectionsX(rows, cols);
        } else {
            findIntersectionsY(rows, cols);
        }

        cells = this.cells.stream()
            .filter(cell -> cell.x >= 0)
            .filter(cell -> cell.x < cols)
            .filter(cell -> cell.y >= 0)
            .filter(cell -> cell.y < rows)
            .distinct()
            .collect(Collectors.toList());
    }

    public String writeString() {
        String s = "";
        for (LandCell cell : cells) {
            s += String.format("%d %d ", cell.x, cell.y);
        }
        return s.trim();
    }
}
