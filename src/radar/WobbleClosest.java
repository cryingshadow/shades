package radar;

import robocode.*;
import robots.*;

public class WobbleClosest implements RadarStrategy {

    private int direction = 1;

    @Override
    public void initialize(final Shade robot) {
        robot.setAdjustRadarForGunTurn(true);
        robot.setTurnRadarRight(360);
    }

    @Override
    public void onScannedRobot(final Shade robot, final ScannedRobotEvent event) {
        this.direction *= -1;
    }

    @Override
    public void repeatForever(final Shade robot) {
        if (robot.enemy.none()) {
            robot.setTurnRadarRight(360);
        } else {
            robot.setTurnRadarRight(360 * this.direction);
        }
    }

}
