package frc.team5115.base;

import edu.wpi.first.wpilibj.Timer;

public class Time {
    private double checkpoint, difference;

    public void start() { checkpoint = Timer.getFPGATimestamp() * 1000; }
    public void check() {
        difference = Timer.getFPGATimestamp() * 1000 - checkpoint;
        checkpoint = Timer.getFPGATimestamp() * 1000;
    }
    public double getDifference() { return difference / 1000; }
}