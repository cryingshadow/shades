package robots;

/**
 * Parent class for registering a robot with a strategy.
 * @author cryingshadow
 */
public abstract class AbstractRobotStrategy implements RobotStrategy {

    /**
     * The registered robot.
     */
    private final Shade bot;

    /**
     * Constructor.
     */
    public AbstractRobotStrategy() {
        this.bot = null;
    }

    /**
     * @param robot The registered robot.
     */
    protected AbstractRobotStrategy(final Shade robot) {
        this.bot = robot;
    }

    /**
     * @return The registered robot.
     */
    public Shade getRegisteredRobot() {
        return this.bot;
    }

}
