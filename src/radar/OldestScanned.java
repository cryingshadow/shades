package radar;

import java.util.*;

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
     * Map from names to absolute bearing angle in radians for all enemies.
     */
    private final Map<String, Double> enemies;

    /**
     * Our own robot.
     */
    private final Shade me;

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
     * @param me Our own robot.
     */
    public OldestScanned(final Shade me) {
        this.me = me;
        this.enemies = new LinkedHashMap<String, Double>();
        this.scanDirection = 1.0;
        this.soughtByRadar = null;
    }

    @Override
    public void onRobotDeath(final RobotDeathEvent event) {
        this.enemies.remove(event.getName());
        this.soughtByRadar = null;
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        final String enemyName = event.getName();
        this.updateEnemyBearing(enemyName, this.me.getAbsoluteBearing(event));
        if (this.finishedScanRound(enemyName)) {
            this.scanDirection =
                Utils.normalRelativeAngle(this.enemies.values().iterator().next() - this.me.getRadarHeadingRadians());
            this.soughtByRadar = this.enemies.keySet().iterator().next();
        }
        this.me.out.println("Scanned bot: " + enemyName);
    }

    @Override
    public void repeatForever() {
        this.me.setTurnRadarRightRadians(this.scanDirection * Double.POSITIVE_INFINITY);
        this.me.scan();
    }

    /**
     * @param enemyName The name of the currently scanned enemy.
     * @return True if we have data for all remaining enemies and the scanned enemy is the one we are currently looking
     *         for or we do not look for a specific enemy.
     */
    private boolean finishedScanRound(final String enemyName) {
        return
            this.enemies.size() == this.me.getOthers()
            && (this.soughtByRadar == null || enemyName.equals(this.soughtByRadar));
    }

    /**
     * Updates the absolute bearing of the specified enemy.
     * @param enemyName The name of the enemy to update.
     * @param absoluteBearing The new absolute bearing of the specified enemy in radians.
     */
    private void updateEnemyBearing(final String enemyName, final double absoluteBearing) {
        this.enemies.put(enemyName, absoluteBearing);
    }

}
