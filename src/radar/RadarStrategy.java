package radar;

import robocode.*;

/**
 * Strategy for handling the radar.
 * @author cryingshadow
 */
public interface RadarStrategy {

    /**
     * To be executed whenever another robot dies.
     * @param event The robot death event.
     */
    public void onRobotDeath(final RobotDeathEvent event);

    /**
     * To be executed whenever another robot is scanned.
     * @param event The scanned robot event.
     */
    public void onScannedRobot(final ScannedRobotEvent event);

    /**
     * To be executed in an infinite loop in the run method.
     */
    public void repeatForever();

}
