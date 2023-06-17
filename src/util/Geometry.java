package util;

/**
 * Utility methods for geometry calculations.
 * @author cryingshadow
 */
public abstract class Geometry {

    /**
     * @param ownHeadingRadians Own heading in radians.
     * @param otherBearingRadians The other's relative bearing in radians.
     * @return The other's absolute bearing in radians.
     */
    public static double getAbsoluteBearing(final double ownHeadingRadians, final double otherBearingRadians) {
        return ownHeadingRadians + otherBearingRadians;
    }

    public static double normalizeBearing(double angle) {
        while (angle >  180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }

}
