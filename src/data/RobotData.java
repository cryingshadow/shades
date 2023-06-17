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
     * The events observed for this enemy.
     */
    private final Deque<RobotEvent> events;

    /**
     * @param robot The own robot.
     */
    public RobotData(final Shade robot) {
        this.events = new LinkedBlockingDeque<RobotEvent>();
    }

    /**
     * @return The death event of this enemy.
     */
    public Optional<RobotEvent> getDeathEvent() {
        return this.isAlive() ? Optional.empty() : Optional.of(this.events.getFirst());
    }

    /**
     * @return The most recently observed absolute bearing of this enemy.
     */
    public Optional<RobotEvent> getMostRecentScanEvent() {
        return
            this.events.stream().filter(e -> e.getEvent() instanceof ScannedRobotEvent).findFirst();
    }

    /**
     * @return True iff this robot is still alive.
     */
    public boolean isAlive() {
        return !(this.events.peekFirst().getEvent() instanceof RobotDeathEvent);
    }

    /**
     * Registers the specified event.
     * @param event The event to register.
     * @param status Own status at the time of the event.
     */
    public void registerEvent(final Event event, final RobotStatus status) {
        this.events.addFirst(new RobotEvent(event, status));
    }

}
