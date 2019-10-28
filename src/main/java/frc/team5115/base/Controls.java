package frc.team5115.base;

import edu.wpi.first.wpilibj.Joystick;

import static frc.team5115.base.Constants.*;

public class Controls {
    private Joystick joy;

    public Controls() {
        joy = new Joystick(0);
    }

    public double getX() { return joy.getRawAxis(X_AXIS); }
    public double getY() { return joy.getRawAxis(Y_AXIS); }
    public double getZ() { return joy.getRawAxis(Z_AXIS); }
    public double increaseThrottle() { return joy.getRawAxis(INCREASE_THROTTLE); }
    public double decreaseThrottle() { return joy.getRawAxis(DECREASE_THROTTLE); }

    public boolean intake() { return joy.getRawButton(INTAKE_BUTTON_ID);}
    public boolean outtake() { return joy.getRawButton(OUTTAKE_BUTTON_ID); }
}
