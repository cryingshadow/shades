package robots;

import robocode.*;

/**
 * Strategy for a robot.
 * @author cryingshadow
 */
public interface RobotStrategy {

    /**
     * To be executed whenever another robot dies.
     * @param robot Our own robot.
     * @param event The observed event.
     */
    public default void onRobotDeath(final Shade robot, final RobotDeathEvent event) {
        // do nothing
    }

    /**
     * To be executed whenever another robot is scanned.
     * @param robot Our own robot.
     * @param event The observed event.
     */
    public default void onScannedRobot(final Shade robot, final ScannedRobotEvent event) {
        // do nothing
    }

    /**
     * To be executed in an infinite loop in the run method.
     * @param robot Our own robot.
     */
    public default void repeatForever(final Shade robot) {
        // do nothing
    }

}
