package radar;

import java.util.*;
import java.util.Map.*;

import data.*;
import robocode.*;
import robocode.util.*;
import robots.*;
import util.*;

/**
 * Tries to scan for the robot that has not been scanned for the longest time (or all robots if not every other robot
 * has been scanned during the last iteration).
 * @author cryingshadow
 */
public class OldestScanned implements RadarStrategy {

    /**
     * Comparator for enemy data based on the last time an enemy has been scanned.
     */
    private static final Comparator<Entry<String, RobotData>> ENTRY_COMPARATOR =
        new Comparator<Entry<String,RobotData>>() {

            @Override
            public int compare(final Entry<String, RobotData> o1, final Entry<String, RobotData> o2) {
                final Optional<RobotEvent> opt1 = o1.getValue().getMostRecentScanEvent();
                final Optional<RobotEvent> opt2 = o2.getValue().getMostRecentScanEvent();
                if (opt1.isPresent()) {
                    if (opt2.isPresent()) {
                        return Long.compare(opt1.get().getEvent().getTime(), opt2.get().getEvent().getTime());
                    }
                    return 1;
                }
                if (opt2.isPresent()) {
                    return -1;
                }
                return 0;
            }

        };

    /**
     * @param enemies Data about our enemies.
     * @return The data entry for the enemy that has not been scanned for the longest time.
     */
    private static Entry<String, RobotData> findOldestScanned(final Map<String, RobotData> enemies) {
        return enemies.entrySet().stream().min(OldestScanned.ENTRY_COMPARATOR).get();
    }

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
    public void initialize(final Shade robot) {
        robot.setAdjustRadarForGunTurn(true);
        robot.setAdjustRadarForRobotTurn(true);
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
        robot.out.println("Scanned bot: " + enemyName);
        if (this.finishedScanRound(robot, enemyName)) {
            final Entry<String, RobotData> entry = OldestScanned.findOldestScanned(enemies);
            final RobotData data = entry.getValue();
            // must exist and encapsulate a ScannedRobotEvent
            final RobotEvent robotEvent = data.getMostRecentScanEvent().get();
            final ScannedRobotEvent recentEvent = (ScannedRobotEvent)robotEvent.getEvent();
            final RobotStatus status = robotEvent.getStatus();
            final double absoluteBearing =
                Geometry.getAbsoluteBearing(status.getHeadingRadians(), recentEvent.getBearingRadians());
            this.scanDirection = Utils.normalRelativeAngle(absoluteBearing - robot.getRadarHeadingRadians());
            this.soughtByRadar = entry.getKey();
            robot.out.println("Changed direction to: " + this.scanDirection);
            robot.out.println("Sought bot: " + this.soughtByRadar);
        }
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
