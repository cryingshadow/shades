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
    private final Deque<Event> events;

    /**
     * @param robot The own robot.
     */
    public RobotData(final Shade robot) {
        this.events = new LinkedBlockingDeque<Event>();
    }

    /**
     * @return The death event of this enemy.
     */
    public Optional<RobotDeathEvent> getDeathEvent() {
        return this.isAlive() ? Optional.empty() : Optional.of((RobotDeathEvent)this.events.getFirst());
    }

    /**
     * @return The most recently observed absolute bearing of this enemy.
     */
    public Optional<ScannedRobotEvent> getMostRecentScanEvent() {
        return
            this.events.stream().filter(e -> e instanceof ScannedRobotEvent).map(e -> (ScannedRobotEvent)e).findFirst();
    }

    /**
     * @return True iff this robot is still alive.
     */
    public boolean isAlive() {
        return !(this.events.peekFirst() instanceof RobotDeathEvent);
    }

    /**
     * Registers the specified event.
     * @param event The event to register.
     */
    public void registerEvent(final Event event) {
        this.events.addFirst(event);
    }

}
