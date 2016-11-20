package robots;

import radar.*;
import robocode.*;

/**
 * My first robot.
 * @author cryingshadow
 */
public class SilentShade extends Shade {

    /**
     * The strategy for radar control.
     */
    private final RadarStrategy radarStrategy;

    /**
     * Constructor.
     */
    public SilentShade() {
        this.radarStrategy = new OldestScanned(this);
    }

    @Override
    public void onRobotDeath(final RobotDeathEvent event) {
        this.radarStrategy.onRobotDeath(event);
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        this.radarStrategy.onScannedRobot(event);
        this.fire(1);
    }

    @Override
    public void run() {
        while (true) {
            this.radarStrategy.repeatForever();
            this.handleBody();
            this.handleGun();
        }
    }

    /**
     *
     */
    private void handleBody() {
        // TODO Auto-generated method stub
        this.ahead(100);
        this.turnGunRight(360);
        this.back(100);
        this.turnGunRight(360);
    }

    /**
     *
     */
    private void handleGun() {
        // TODO Auto-generated method stub

    }

}
