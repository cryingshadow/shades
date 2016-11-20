package radar;

import robocode.*;
import robots.*;

/**
 * Strategy for handling the radar.
 * @author cryingshadow
 */
public interface RadarStrategy extends RobotStrategy {

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

    @Override
    public RadarStrategy registerRobot(Shade robot);

}
