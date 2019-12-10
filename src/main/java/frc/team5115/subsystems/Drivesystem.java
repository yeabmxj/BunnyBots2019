package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.team5115.wrappers.TalonWrapper;

import static frc.team5115.base.Constants.*;
import static frc.team5115.robot.Robot.*;

public class Drivesystem {
    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    private TalonSRX backLeft;
    private TalonSRX backRight;


    private double throttle = INITIAL_THROTTLE;

    public Drivesystem() {
        frontLeft = new TalonSRX(FRONT_LEFT_ID);
        frontRight = new TalonSRX(FRONT_RIGHT_ID);
        backLeft = new TalonSRX(BACK_LEFT_ID);
        backRight = new TalonSRX(BACK_RIGHT_ID);

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    }

    public void drive(double y, double x, double z, double gyroangle, double throttle, double speedCap) {
        y = deadband(y);
        x = deadband(x);

        //double[] processed = rotate(x,y,gyroangle);

        Vector2d input = new Vector2d(y,x);
        input.rotate(-gyroangle);

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[FRONT_LEFT_ID] = input.x + input.y + z;
        wheelSpeeds[FRONT_RIGHT_ID] = -input.x + input.y - z;
        wheelSpeeds[BACK_LEFT_ID] = -input.x + input.y + z;
        wheelSpeeds[BACK_RIGHT_ID] = input.x + input.y - z;

        normalize(wheelSpeeds);

        frontLeft.set(ControlMode.PercentOutput, wheelSpeeds[FRONT_LEFT_ID] * throttle * speedCap);
        frontRight.set(ControlMode.PercentOutput, wheelSpeeds[FRONT_RIGHT_ID] * throttle * speedCap * -1);
        backLeft.set(ControlMode.PercentOutput, wheelSpeeds[BACK_LEFT_ID] * throttle * speedCap);
        backRight.set(ControlMode.PercentOutput, wheelSpeeds[BACK_RIGHT_ID] * throttle * speedCap * -1);
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
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

    public void init() {
        update();
    }
    public void update() {
        drivesystem.drive(joy.getY(), -joy.getX(), joy.getZ(), 0, 1, SPEED_CAP);
    }
}
