package robots;

import java.util.*;
import java.util.concurrent.*;

import data.*;
import robocode.*;
import util.*;

/**
 * Parent class for my robots containing utility methods and allowing for configuration by injecting strategies for
 * radar, gun, and body control.
 * @author cryingshadow
 */
public abstract class Shade extends AdvancedRobot {

    public final AdvancedEnemyBot enemy = new AdvancedEnemyBot();

    /**
     * Our current status.
     */
    private RobotStatus currentStatus;

    /**
     * Data about our enemies.
     */
    private final Map<String, RobotData> enemies;

    private final List<RobotStrategy> strategies;

    public Shade(final List<RobotStrategy> strategies) {
        this.strategies = strategies;
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
        for (final RobotStrategy strategy : this.strategies) {
            strategy.onRobotDeath(this, event);
        }
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        final String enemyName = event.getName();
        this.updateRobotData(enemyName, event);
        for (final RobotStrategy strategy : this.strategies) {
            strategy.onScannedRobot(this, event);
        }
    }

    @Override
    public void onStatus(final StatusEvent event) {
        this.currentStatus = event.getStatus();
    }

    @Override
    public void run() {
        for (final RobotStrategy strategy : this.strategies) {
            strategy.initialize(this);
        }
        this.execute();
        while (true) {
            for (final RobotStrategy strategy : this.strategies) {
                strategy.repeatForever(this);
            }
            this.execute();
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
        this.enemies.get(enemyName).registerEvent(event, this.currentStatus);
    }

}
