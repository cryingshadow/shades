package body;

import robots.*;

/**
 * Simple movement strategy.
 * @author cryingshadow
 */
public class SimpleMovement extends AbstractRobotStrategy implements BodyStrategy {

    /**
     * Constructor.
     */
    public SimpleMovement() {
        this(null);
    }

    /**
     * Constructor.
     * @param bot The registered robot.
     */
    private SimpleMovement(final Shade bot) {
        super(bot);
    }

    @Override
    public SimpleMovement registerRobot(final Shade robot) {
        return new SimpleMovement(robot);
    }

    @Override
    public void repeatForever() {
        final Shade bot = this.getRegisteredRobot();
        bot.ahead(100);
        bot.back(100);
    }

}
