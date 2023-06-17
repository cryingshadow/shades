package gun;

import java.awt.geom.*;

import robocode.*;
import robots.*;
import util.*;

/**
 * Simple targetting strategy.
 * @author cryingshadow
 */
public class SimpleTargetting implements GunStrategy {

    private static double absoluteBearing(final double x1, final double y1, final double x2, final double y2) {
        final double xo = x2-x1;
        final double yo = y2-y1;
        final double hyp = Point2D.distance(x1, y1, x2, y2);
        final double arcSin = Math.toDegrees(Math.asin(xo / hyp));
        double bearing = 0;

        if (xo > 0 && yo > 0) { // both pos: lower-Left
            bearing = arcSin;
        } else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
            bearing = 360 + arcSin; // arcsin is negative here, actually 360 - ang
        } else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
            bearing = 180 - arcSin;
        } else if (xo < 0 && yo < 0) { // both neg: upper-right
            bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
        }

        return bearing;
    }

    @Override
    public void initialize(final Shade robot) {
        robot.setAdjustGunForRobotTurn(true);
        robot.enemy.reset();
    }

    @Override
    public void onRobotDeath(final Shade robot, final RobotDeathEvent event) {
        if (event.getName().equals(robot.enemy.getName())) {
            robot.enemy.reset();
        }
    }

    @Override
    public void onScannedRobot(final Shade robot, final ScannedRobotEvent event) {
        if (
            robot.enemy.none()
            || event.getDistance() < robot.enemy.getDistance() - 70
            || event.getName().equals(robot.enemy.getName())
        ) {
            robot.enemy.update(event, robot);
        }
    }

    @Override
    public void repeatForever(final Shade robot) {
        if (robot.enemy.none()) {
            return;
        }
        final double firePower = Math.min(500 / robot.enemy.getDistance(), 3);
        final double bulletSpeed = 20 - firePower * 3;
        final long time = (long)(robot.enemy.getDistance() / bulletSpeed);
        final double futureX = robot.enemy.getFutureX(time);
        final double futureY = robot.enemy.getFutureY(time);
        final double absDeg = SimpleTargetting.absoluteBearing(robot.getX(), robot.getY(), futureX, futureY);
        robot.setTurnGunRight(Geometry.normalizeBearing(absDeg - robot.getGunHeading()));
        if (robot.getGunHeat() == 0 && Math.abs(robot.getGunTurnRemaining()) < 10) {
            robot.setFire(firePower);
        }
    }

}
