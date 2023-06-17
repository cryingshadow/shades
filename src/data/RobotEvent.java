package data;

import robocode.*;

/**
 * An event enhanced by additional data.
 * @author cryingshadow
 */
public class RobotEvent {

    /**
     * The event.
     */
    private final Event event;

    /**
     * Own status at event time.
     */
    private final RobotStatus status;

    /**
     * @param event The event.
     * @param status Own status at event time.
     */
    public RobotEvent(final Event event, final RobotStatus status) {
        this.event = event;
        this.status = status;
    }

    /**
     * @return The event.
     */
    public Event getEvent() {
        return this.event;
    }

    /**
     * @return Own status at event time.
     */
    public RobotStatus getStatus() {
        return this.status;
    }

}
