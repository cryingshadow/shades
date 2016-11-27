package gun;

import robocode.*;
import robots.*;

/**
 * Simple targetting strategy.
 * @author cryingshadow
 */
public class SimpleTargetting implements GunStrategy {

    @Override
    public void onScannedRobot(final Shade robot, final ScannedRobotEvent event) {
        robot.fire(1);
    }

    @Override
    public void repeatForever(final Shade robot) {
        robot.turnGunRight(360);
    }

}
