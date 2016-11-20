package data;

/**
 * The absolute bearing of an enemy at a certain time.
 * @author cryingshadow
 */
public class AbsoluteBearing {

    /**
     * The absolute bearing of an enemy.
     */
    private final double absoluteBearing;

    /**
     * The timestamp when the bearing was scanned.
     */
    private final long timestamp;

    /**
     * @param absoluteBearing The absolute bearing of an enemy.
     * @param timestamp The timestamp when the bearing was scanned.
     */
    public AbsoluteBearing(final double absoluteBearing, final long timestamp) {
        this.absoluteBearing = absoluteBearing;
        this.timestamp = timestamp;
    }

    /**
     * @return The absolute bearing of an enemy.
     */
    public double getAbsoluteBearing() {
        return this.absoluteBearing;
    }

    /**
     * @return The timestamp when the bearing was scanned.
     */
    public long getTimestamp() {
        return this.timestamp;
    }

}
