package commons;

import java.util.List;

public class Entity {

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = 90;
    }

    public int x;
    public int y;
    public int direction;

    public void executeCommands(List<Command> commands) {

        for (Command command : commands) {
            direction = (direction + command.rotate * 90) % 360;

            if (direction == 0) {
                y -= command.forward;
            } else if (direction == 90) {
                x += command.forward;
            } else if (direction == 180) {
                y += command.forward;
            } else if (direction == 270) {
                x -= command.forward;
            }
        }
    }
}
