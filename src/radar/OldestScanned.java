package radar;

import java.util.*;

import data.*;
import robocode.*;
import robocode.util.*;
import robots.*;

/**
 * Tries to scan for the robot that has not been scanned for the longest time (or all robots if not every other robot
 * has been scanned during the last iteration).
 * @author cryingshadow
 */
public class OldestScanned extends AbstractRobotStrategy implements RadarStrategy {

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
        this(null, 1.0, null);
    }

    /**
     * Constructor.
     * @param bot Our own robot.
     * @param scanDirection
     * @param soughtByRadar
     */
    private OldestScanned(
        final Shade bot,
        final double scanDirection,
        final String soughtByRadar
    ) {
        super(bot);
        this.scanDirection = scanDirection;
        this.soughtByRadar = soughtByRadar;
    }

    @Override
    public void onRobotDeath(final RobotDeathEvent event) {
        this.soughtByRadar = null;
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        final Shade bot = this.getRegisteredRobot();
        Map<String, RobotData> enemies = bot.getEnemies();
        final String enemyName = event.getName();
        if (this.finishedScanRound(enemyName)) {
            this.scanDirection =
                Utils.normalRelativeAngle(enemies.values().iterator().next().getMostRecentAbsoluteBearing() - bot.getRadarHeadingRadians());
            this.soughtByRadar = enemies.keySet().iterator().next();
        }
        bot.out.println("Scanned bot: " + enemyName);
    }

    @Override
    public OldestScanned registerRobot(final Shade robot) {
        return new OldestScanned(robot, this.scanDirection, this.soughtByRadar);
    }

    @Override
    public void repeatForever() {
        final Shade bot = this.getRegisteredRobot();
        bot.setTurnRadarRightRadians(this.scanDirection * Double.POSITIVE_INFINITY);
        bot.scan();
    }

    /**
     * @param enemyName The name of the currently scanned enemy.
     * @return True if we have data for all remaining enemies and the scanned enemy is the one we are currently looking
     *         for or we do not look for a specific enemy.
     */
    private boolean finishedScanRound(final String enemyName) {
        Shade bot = this.getRegisteredRobot();
        return
            bot.getEnemies().size() == bot.getOthers()
            && (this.soughtByRadar == null || enemyName.equals(this.soughtByRadar));
    }

}
