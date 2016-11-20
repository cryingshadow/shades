package robots;

/**
 * Strategy for a robot.
 * @author cryingshadow
 */
public interface RobotStrategy {

    /**
     * @param robot The robot for which this strategy is to be applied.
     * @return This strategy with the specified registered robot.
     */
    public RobotStrategy registerRobot(Shade robot);

    /**
     * To be executed in an infinite loop in the run method.
     */
    public default void repeatForever() {
        // do nothing
    }

}
