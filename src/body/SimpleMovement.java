package body;

import robots.*;

/**
 * Simple movement strategy.
 * @author cryingshadow
 */
public class SimpleMovement implements BodyStrategy {

    @Override
    public void repeatForever(final Shade robot) {
        robot.ahead(100);
        robot.back(100);
    }

}
