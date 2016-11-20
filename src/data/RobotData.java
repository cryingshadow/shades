package data;

import java.util.*;
import java.util.concurrent.*;

import robocode.*;
import robots.*;

/**
 * Bean for data about enemy robots.
 * @author cryingshadow
 */
public class RobotData {

    /**
     * The absolute bearing angle between me and this enemy in radians.
     */
    private final Deque<AbsoluteBearing> absoluteBearings;

    /**
     * The own robot.
     */
    private final Shade bot;

    /**
     * The time of death (null means still alive).
     */
    private Long death;

    /**
     * @param robot The own robot.
     */
    public RobotData(final Shade robot) {
        this.bot = robot;
        this.absoluteBearings = new LinkedBlockingDeque<AbsoluteBearing>();
        this.death = null;
    }

    /**
     * @return the absoluteBearing
     */
    public double getMostRecentAbsoluteBearing() {
        return this.absoluteBearings.getFirst().getAbsoluteBearing();
    }

    /**
     * @return The time when this robot died. Null if it is still alive.
     */
    public Long getTimeOfDeath() {
        return this.death;
    }

    /**
     * @return True iff this robot is still alive.
     */
    public boolean isAlive() {
        return this.death == null;
    }

    /**
     * @param event The death event.
     */
    public void registerRobotDeathEvent(final RobotDeathEvent event) {
        this.death = event.getTime();
    }

    /**
     * @param event The event indicating a scanned robot.
     */
    public void registerScannedRobotEvent(final ScannedRobotEvent event) {
        this.storeAbsoluteBearing(this.bot.getAbsoluteBearing(event), event.getTime());
    }

    /**
     * @param absoluteBearing The absolute bearing angle between me and this enemy in radians.
     * @param timestamp The timestamp when the bearing was scanned.
     */
    public void storeAbsoluteBearing(final double absoluteBearing, final long timestamp) {
        this.absoluteBearings.addFirst(new AbsoluteBearing(absoluteBearing, timestamp));
    }

}
