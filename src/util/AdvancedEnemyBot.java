package util;

import robocode.*;

public class AdvancedEnemyBot extends EnemyBot{

    private double x;
    private double y;

    public AdvancedEnemyBot(){
        this.reset();
    }

    public double getFutureX(final long when){
        return this.x + Math.sin(Math.toRadians(this.getHeading())) * this.getVelocity() * when;
    }
    public double getFutureY(final long when){
        return this.y + Math.cos(Math.toRadians(this.getHeading())) * this.getVelocity() * when;
    }

    public double getX(){ return this.x;}

    public double getY(){ return this.y;}

    @Override
    public void reset(){
        super.reset();
        this.x = 0.0; this.y = 0.0;
    }

    public void update(final ScannedRobotEvent e, final Robot robot){
        super.update(e);
        double absBearingDeg = robot.getHeading() + e.getBearing();
        if (absBearingDeg < 0) {
            absBearingDeg += 360;
        }
        // yes, you use the _sine_ to get the X value because 0 deg is North
        this.x = robot.getX() + Math.sin(Math.toRadians(absBearingDeg)) * e.getDistance();
        // yes, you use the _cosine_ to get the Y value because 0 deg is North
        this.y = robot.getY() + Math.cos(Math.toRadians(absBearingDeg)) * e.getDistance();
    }
}