package robots;

import java.util.*;
import java.util.concurrent.*;

import body.*;
import data.*;
import gun.*;
import radar.*;
import robocode.*;

/**
 * Parent class for my robots containing utility methods and allowing for configuration by injecting strategies for
 * radar, gun, and body control.
 * @author cryingshadow
 */
public abstract class Shade extends AdvancedRobot {

    /**
     * The strategy for body control.
     */
    private final BodyStrategy bodyStrategy;

    /**
     * Data about our enemies.
     */
    private final Map<String, RobotData> enemies;

    /**
     * The strategy for gun control.
     */
    private final GunStrategy gunStrategy;

    /**
     * The strategy for radar control.
     */
    private final RadarStrategy radarStrategy;

    /**
     * Constructor.
     * @param radarStrategy The strategy for radar control.
     * @param gunStrategy The strategy for gun control.
     * @param bodyStrategy The strategy for body control.
     */
    public Shade(final RadarStrategy radarStrategy, final GunStrategy gunStrategy, final BodyStrategy bodyStrategy) {
        this.radarStrategy = radarStrategy;
        this.gunStrategy = gunStrategy;
        this.bodyStrategy = bodyStrategy;
        this.enemies = new ConcurrentHashMap<String, RobotData>();
    }

    /**
     * @param event The scanned robot.
     * @return The absolute bearing of the scanned robot in radians.
     */
    public double getAbsoluteBearing(final ScannedRobotEvent event) {
        return this.getHeadingRadians() + event.getBearingRadians();
    }

    /**
     * @return Data about our enemies.
     */
    public Map<String, RobotData> getEnemies() {
        return this.enemies;
    }

    @Override
    public void onRobotDeath(final RobotDeathEvent event) {
        final String enemyName = event.getName();
        this.updateRobotData(enemyName, event);
        this.radarStrategy.onRobotDeath(this, event);
        this.gunStrategy.onRobotDeath(this, event);
        this.bodyStrategy.onRobotDeath(this, event);
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        final String enemyName = event.getName();
        this.updateRobotData(enemyName, event);
        this.radarStrategy.onScannedRobot(this, event);
        this.gunStrategy.onScannedRobot(this, event);
        this.bodyStrategy.onScannedRobot(this, event);
    }

    @Override
    public void run() {
        while (true) {
            this.radarStrategy.repeatForever(this);
            this.gunStrategy.repeatForever(this);
            this.bodyStrategy.repeatForever(this);
        }
    }

    /**
     * @param enemyName The name of the enemy with data to be updated.
     * @param event The event to be registered.
     */
    protected void updateRobotData(final String enemyName, final Event event) {
        if (!this.enemies.containsKey(enemyName)) {
            this.enemies.put(enemyName, new RobotData(this));
        }
        this.enemies.get(enemyName).registerEvent(event);
    }

}
