package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.team5115.wrappers.TalonWrapper;

import static frc.team5115.base.Constants.*;
import static frc.team5115.robot.Robot.*;

public class Drivesystem {
    private WPI_TalonSRX frontLeft;
    private WPI_TalonSRX frontRight;
    private WPI_TalonSRX backLeft;
    private WPI_TalonSRX backRight;

    public AHRS gyro;

    MecanumDrive driveMath;

    private double throttle = INITIAL_THROTTLE;

    public Drivesystem() {
        frontLeft = new WPI_TalonSRX(FRONT_LEFT_ID);
        frontRight = new WPI_TalonSRX(FRONT_RIGHT_ID);
        backLeft = new WPI_TalonSRX(BACK_LEFT_ID);
        backRight = new WPI_TalonSRX(BACK_RIGHT_ID);

        gyro = new AHRS(SPI.Port.kMXP);
        gyro.reset();

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

        driveMath = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    }

    public void drive(double y, double x, double z, double gyroangle) {
       driveMath.driveCartesian(y,x,z,gyroangle);
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

    public void debug() {
//        System.out.println(frontLeft.getSelectedSensorPosition());
//        System.out.println(frontRight.getSelectedSensorPosition());
//        System.out.println(backLeft.getSelectedSensorPosition());
//        System.out.println(backRight.getSelectedSensorPosition());
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
        drivesystem.drive(joy.getX(), -joy.getY(), joy.getZ(), 0);
    }
}
