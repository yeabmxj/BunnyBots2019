package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team5115.wrappers.TalonWrapper;

import static frc.team5115.base.Constants.*;
import static frc.team5115.robot.Robot.*;

public class Drivesystem {
    private TalonWrapper frontLeft;
    private TalonWrapper frontRight;
    private TalonWrapper backLeft;
    private TalonWrapper backRight;

    private double throttle = INITIAL_THROTTLE;

    public Drivesystem() {
        frontLeft = new TalonWrapper(FRONT_LEFT_ID);
        frontRight = new TalonWrapper(FRONT_RIGHT_ID);
        backLeft = new TalonWrapper(BACK_LEFT_ID);
        backRight = new TalonWrapper(BACK_RIGHT_ID);

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    }

    public void drive(double y, double x, double z, double gyroangle, double throttle, double speedCap) {
        y = deadband(y);
        x = deadband(x);

        double[] processed = rotate(x,y,gyroangle);

        double[] wheelSpeeds = new double[5];
        wheelSpeeds[1] = processed[0] + processed[1] + z;
        wheelSpeeds[2] = processed[0] - processed[1] + z;
        wheelSpeeds[3] = processed[0] + processed[1] + z;
        wheelSpeeds[4] = processed[0] + processed[1] - z;

        normalize(wheelSpeeds);

        frontLeft.set(wheelSpeeds[1] * throttle * speedCap);
        frontRight.set(wheelSpeeds[2] * throttle * speedCap);
        backLeft.set(wheelSpeeds[3] * throttle * speedCap);
        backRight.set(wheelSpeeds[4] * throttle * speedCap);
    }

    public double throttle(double increase, double decrease) {
        throttle += 0.03 *(increase - decrease);

        if (throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }
        return throttle;
    }

    private double[] rotate(double x, double y, double gyroangle) {
        double cosA = Math.cos(gyroangle * (Math.PI / 180));
        double sinA = Math.sin(gyroangle * (Math.PI / 180));
        double[] out = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }

    private double deadband(double value) {
        if (Math.abs(value) > DEAD_BAND) {
            if (value > 0.0) {
                return (value - DEAD_BAND) / (1.0 - DEAD_BAND);
            } else {
                return (value + DEAD_BAND) / (1.0 - DEAD_BAND);
            }
        } else {
            return 0.0;
        }
    }

    private void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

    public void init() {
        update();
    }

    public void update() {
        drivesystem.drive(joy.getY(), joy.getX(), joy.getZ(), gyro.getYaw(), SPEED_CAP, drivesystem.throttle(joy.increaseThrottle(), joy.decreaseThrottle()));
    }
}
