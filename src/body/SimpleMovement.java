package body;

import robots.*;
import util.*;

/**
 * Simple movement strategy.
 * @author cryingshadow
 */
public class SimpleMovement implements BodyStrategy {

    private static boolean tooCloseToWalls(final Shade robot) {
        final int margin = 60;
        return robot.getX() < margin
            || robot.getX() > robot.getBattleFieldWidth() - margin
            || robot.getY() < margin
            || robot.getY() > robot.getBattleFieldHeight() - margin;
    }

    private int direction = 1;

    private int wallCooldown = 0;

    @Override
    public void repeatForever(final Shade robot) {
        if (this.wallCooldown == 0 && SimpleMovement.tooCloseToWalls(robot)) {
            this.direction *= -1;
            this.wallCooldown = 10;
        } else if (robot.getVelocity() == 0) {
            if (this.wallCooldown == 0) {
                this.direction *= -1;
            }
        }
        this.wallCooldown = Math.max(this.wallCooldown - 1, 0);
        robot.setAhead(100 * this.direction);
        if (robot.enemy.none()) {
            robot.setTurnRight(30);
        } else {
            robot.setTurnRight(Geometry.normalizeBearing(robot.enemy.getBearing() + 90 - (15 * this.direction)));
        }
    }

}
