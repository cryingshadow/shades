package gun;

import robots.*;

/**
 *
 * @author cryingshadow
 */
public interface GunStrategy extends RobotStrategy {

    @Override
    public GunStrategy registerRobot(Shade robot);

}
