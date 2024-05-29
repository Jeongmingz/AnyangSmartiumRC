package com.example.controller.rest;

public class ControlData {
    private int quadrant;
    private int speed;
    private double steering;
    private String focus;

    public ControlData(int speed, int angle) {
        this.quadrant = getQuadrant(angle);
        this.speed = (int)(speed * 0.15);
        this.steering = (this.speed != 0) ? getSteering(angle) : 0;
        this.focus = (speed == 0 && angle == 0) ? "break" : getManeuver();
    }

    private int getQuadrant(int angle){
        return (int)getAnglePer90(angle);
    }

    private double getSteering(int angle){
        double steering = (getAnglePer90(angle) - quadrant - getDirection())
                * getSign();

        return Math.floor(steering * 10) /10.0;
    }

    private double getAnglePer90(int angle){
        return angle / 90.0;
    }

    private int getDirection(){
        return (quadrant == 1 || quadrant == 3) ? 0 : 1;
    }

    private int getSign(){
        return (quadrant < 2) ? -1 : 1;
    }

    private String getManeuver(){
        return (quadrant< 2) ? "forward" : "backward";
    }

    public int getSpeed() {
        return speed;
    }

    public double getSteering() {
        return steering;
    }

    public String getFocus() {
        return focus;
    }

    @Override
    public String toString() {
        return "ControlData{" +
                "speed=" + speed +
                ", steering=" + steering +
                ", focus='" + focus + '\'' +
                '}';
    }
}
