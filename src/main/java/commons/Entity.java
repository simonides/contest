package commons;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    public Entity(int xSize, int ySize, int x, int y, int alienId, double speed, long spawnTime, List<Command> path, double health, final int loot) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.direction = 90;
        this.alienId = alienId;
        this.spawnTime = spawnTime;
        this.path = path;
        this.speed = speed;
        this.health = health;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public int xSize;
    public int ySize;

    public int startX;
    public int startY;

    public int x;
    public int y;
    public int direction;
    public int alienId;
    public double health;

    public double speed;
    public long spawnTime; // in ticks

    public List<Command> path;

    //    public double applyCommand(Command command, double speed, double ticks) {
    //
    //    }

    public boolean isSpawned(int tick) {
        return tick >= spawnTime;
    }

    public void shoot(long tick, double damage) {
        if (tick < spawnTime) {
            throw new IllegalStateException("this shit shouldnt happen");
        }
        health -= damage;
    }

    public void evaluate(long tickTarget) throws LostException {
        x = startX;
        y = startY;
        direction = 90;

        double totalDistance = speed * (tickTarget - spawnTime);
        double walkedDistance = 0;

        for (Command command : path) {
            if (walkedDistance + 1 > totalDistance) return;
            if (command.forward == 0) {
                direction = (direction + command.rotate * 90) % 360;
                continue;
            } else {
                if (direction == 0) {
                    y--;
                } else if (direction == 90) {
                    x++;
                } else if (direction == 180) {
                    y++;
                } else if (direction == 270) {
                    x--;
                }
                walkedDistance++;
            }
        }

        if (x < 0 || x > xSize || y < 0 || y > ySize) {
            throw new IllegalAccessError("Map size exceeded");
        }

        if (isAlive(tickTarget)) {
            System.out.println("Lost to entity " + alienId);
            throw new LostException();
        }
    }

    public boolean isAlive(long tick) {
        if (tick < spawnTime) return true;
        return health > 0;
    }

    //    public void evaluatePositionAtTick(int tickTarget) {
    //
    //        x = startX;
    //        y = startY;
    //        direction = 90;
    //
    //        int currentTick = spawnTime;
    //
    //        for (Command command : path) {
    //            direction = (direction + command.rotate * 90) % 360;
    //
    //            for (int i = 0; i < command.forward; i++) {
    //
    //                if (currentTick + 1f / speed > tickTarget) return;
    //                //                if (currentTick >= tickTarget) return;
    //
    //                if (direction == 0) {
    //                    y--;
    //                } else if (direction == 90) {
    //                    x++;
    //                } else if (direction == 180) {
    //                    y++;
    //                } else if (direction == 270) {
    //                    x--;
    //                }
    //                currentTick += 1f / speed;
    //            }
    //        }
    //    }

    //    public Position executeCommand(Command command) {
    //        direction = (direction + command.rotate * 90) % 360;
    //
    //        for (int i = 0; i < command.forward; i++) {
    //            if (direction == 0) {
    //                y--;
    //            } else if (direction == 90) {
    //                x++;
    //            } else if (direction == 180) {
    //                y++;
    //            } else if (direction == 270) {
    //                x--;
    //            }
    //
    //            return new Position(x, y);
    //        }
    //    }
    //
    //    public List<Position> executeCommands(List<Command> commands) {
    //
    //        final List<Position> positions = new ArrayList<>();
    //        positions.add(new Position(x, y));
    //
    //        for (Command command : commands) {
    //
    //            final Position position = executeCommand(command);
    //            positions.add(position);
    //        }
    //
    //        return positions;
    //    }

    public List<Position> executeCommands(List<Command> commands) {

        final List<Position> positions = new ArrayList<>();
        positions.add(new Position(x, y));

        for (Command command : commands) {

            direction = (direction + command.rotate * 90) % 360;

            for (int i = 0; i < command.forward; i++) {
                if (direction == 0) {
                    y--;
                } else if (direction == 90) {
                    x++;
                } else if (direction == 180) {
                    y++;
                } else if (direction == 270) {
                    x--;
                }

                positions.add(new Position(x, y));
            }
        }

        return positions;
    }
}
