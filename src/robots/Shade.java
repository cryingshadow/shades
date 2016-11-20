package robots;

import robocode.*;

/**
 * Parent class for my robots containing utility methods.
 * @author cryingshadow
 */
public abstract class Shade extends AdvancedRobot {

    /**
     * @param event The scanned robot.
     * @return The absolute bearing of the scanned robot in radians.
     */
    public double getAbsoluteBearing(ScannedRobotEvent event) {
        return this.getHeadingRadians() + event.getBearingRadians();
    }

}
