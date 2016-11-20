package robots;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

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
        this.radarStrategy = radarStrategy.registerRobot(this);
        this.gunStrategy = gunStrategy.registerRobot(this);
        this.bodyStrategy = bodyStrategy.registerRobot(this);
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
        this.updateRobotData(
            enemyName,
            (data) -> data.registerRobotDeathEvent(event)
        );
        this.radarStrategy.onRobotDeath(event);
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        final String enemyName = event.getName();
        this.updateRobotData(
            enemyName,
            (data) -> data.registerScannedRobotEvent(event)
        );
        this.radarStrategy.onScannedRobot(event);
        this.fire(1);
    }

    @Override
    public void run() {
        while (true) {
            this.radarStrategy.repeatForever();
            this.gunStrategy.repeatForever();
            this.bodyStrategy.repeatForever();
        }
    }

    /**
     * @param enemyName The name of the enemy with data to be updated.
     * @param update The void function performing the update.
     */
    protected void updateRobotData(final String enemyName, final Consumer<RobotData> update) {
        if (!this.enemies.containsKey(enemyName)) {
            this.enemies.put(enemyName, new RobotData(this));
        }
        update.accept(this.enemies.get(enemyName));
    }

}
