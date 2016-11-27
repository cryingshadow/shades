package radar;

import java.util.*;
import java.util.Map.*;

import data.*;
import robocode.*;
import robocode.util.*;
import robots.*;

/**
 * Tries to scan for the robot that has not been scanned for the longest time (or all robots if not every other robot
 * has been scanned during the last iteration).
 * @author cryingshadow
 */
public class OldestScanned implements RadarStrategy {

    /**
     * The direction in which to turn the radar. Only its sign is relevant but it is a double for convenience.
     */
    private double scanDirection;

    /**
     * Robot currently sought by the radar. Null if we did not see all enemies during the recent iteration of scanning
     * for enemies.
     */
    private String soughtByRadar;

    /**
     * Constructor.
     */
    public OldestScanned() {
        this.scanDirection = 1.0;
        this.soughtByRadar = null;
    }

    @Override
    public void onRobotDeath(final Shade robot, final RobotDeathEvent event) {
        if (event.getName().equals(this.soughtByRadar)) {
            this.soughtByRadar = null;
        }
    }

    @Override
    public void onScannedRobot(final Shade robot, final ScannedRobotEvent event) {
        final Map<String, RobotData> enemies = robot.getEnemies();
        final String enemyName = event.getName();
        if (this.finishedScanRound(robot, enemyName)) {
            final Optional<Entry<String, RobotData>> entry = enemies.entrySet().stream().findFirst();
            if (entry.isPresent()) {
                final RobotData data = entry.get().getValue();
                final Optional<ScannedRobotEvent> maybeEvent = data.getMostRecentScanEvent();
                if (maybeEvent.isPresent()) {
                    final ScannedRobotEvent recentEvent = maybeEvent.get();
                    this.scanDirection =
                        Utils.normalRelativeAngle(
                            robot.getAbsoluteBearing(recentEvent) - robot.getRadarHeadingRadians()
                        );
                    this.soughtByRadar = entry.get().getKey();
                }
            }
        }
        robot.out.println("Scanned bot: " + enemyName);
    }

    @Override
    public void repeatForever(final Shade robot) {
        robot.setTurnRadarRightRadians(this.scanDirection * Double.POSITIVE_INFINITY);
        robot.scan();
    }

    /**
     * @param robot Our own robot.
     * @param enemyName The name of the currently scanned enemy.
     * @return True if we have data for all remaining enemies and the scanned enemy is the one we are currently looking
     *         for or we do not look for a specific enemy.
     */
    private boolean finishedScanRound(final Shade robot, final String enemyName) {
        return
            robot.getEnemies().size() == robot.getOthers()
            && (this.soughtByRadar == null || enemyName.equals(this.soughtByRadar));
    }

}
