package frc.team5115.subsystems;

import edu.wpi.first.wpilibj.Spark;

import static frc.team5115.base.Constants.*;

public class
Outtake {
    private Spark shooter;

    public Outtake() {
        shooter = new Spark(OUTTAKE_ID);
    }

    public void outtake(double value) {
        shooter.set(value);
    }

    public void init() {
        outtake(0);
    }

    public void update(boolean outtaking) {
        if (outtaking) {
            outtake(OUTTAKE_SPEED);
        }
        else {
            outtake(0);
        }
    }
}
