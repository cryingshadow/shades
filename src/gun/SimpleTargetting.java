package gun;

import robots.*;

/**
 * Simple targetting strategy.
 * @author cryingshadow
 */
public class SimpleTargetting extends AbstractRobotStrategy implements GunStrategy {

    /**
     * Constructor.
     */
    public SimpleTargetting() {
        this(null);
    }

    /**
     * Constructor.
     * @param bot The registered robot.
     */
    private SimpleTargetting(final Shade bot) {
        super(bot);
    }

    @Override
    public SimpleTargetting registerRobot(final Shade robot) {
        return new SimpleTargetting(robot);
    }

    @Override
    public void repeatForever() {
        this.getRegisteredRobot().turnGunRight(360);
    }

}
