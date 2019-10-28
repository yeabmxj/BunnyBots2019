package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Spark;

import static frc.team5115.base.Constants.*;

public class Intake {
    private Spark intake;
    private Spark feeder;

    public Intake() {
        intake = new Spark(INTAKE_ID);
        feeder = new Spark(FEEDER_ID);
    }

    public void actuate(double intakeSpeed, double feederSpeed) {
        intake.set(intakeSpeed);
        feeder.set(feederSpeed);
    }

    public void init() {
        actuate(0,FEEDER_SPEED);
    }

    public void update(boolean intaking) {
        if (intaking) {
            actuate(INTAKE_SPEED, FEEDER_SPEED);
        }
        else {
            actuate(0, FEEDER_SPEED);
        }
    }
}
