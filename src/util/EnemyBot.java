package util;

import robocode.*;

public class EnemyBot {

    private double bearing, distance, energy, heading, velocity;
    private String name;

    public EnemyBot(){
        this.reset();
    }

    public double getBearing() {return this.bearing;}

    public double getDistance() {return this.distance;}

    public double getEnergy() {return this.energy;}

    public double getHeading() {return this.heading;}

    public String getName() {return this.name;}

    public double getVelocity() {return this.velocity;}

    public boolean none(){
        return this.name.equals("");
    }

    public void reset(){
        this.name = "";
        this.bearing = 0.0;
        this.distance = 0.0;
        this.energy = 0.0;
        this.heading = 0.0;
        this.velocity = 0.0;
    }

    public void update(final ScannedRobotEvent e){
        this.bearing = e.getBearing();
        this.distance = e.getDistance();
        this.energy = e.getEnergy();
        this.heading = e.getHeading();
        this.velocity = e.getVelocity();
        this.name = e.getName();
    }
}