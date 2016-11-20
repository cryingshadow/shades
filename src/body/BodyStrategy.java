package body;

import robots.*;

/**
 *
 * @author cryingshadow
 */
public interface BodyStrategy extends RobotStrategy {

    @Override
    public BodyStrategy registerRobot(Shade robot);

}
