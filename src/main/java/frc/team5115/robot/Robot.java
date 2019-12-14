package frc.team5115.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.Timer;
import frc.team5115.base.Controls;
import frc.team5115.base.Heartbeat;
import frc.team5115.subsystems.*;
import frc.team5115.wrappers.Limelight;


public class Robot extends TimedRobot {

    public static Drivesystem drivesystem;

    public static Controls joy;
    public static Limelight limelight;
    public static Manipulator manipulator;

    public static Heartbeat timer;

    double start;
    double duration = 5;

    public void robotInit() {
        drivesystem = new Drivesystem();

        joy = new Controls();
        limelight = new Limelight();
        manipulator = new Manipulator();

        drivesystem.init();
        manipulator.update();
        limelight.cameraMode();
    }

    public void autonomousInit() {
        start = Timer.getFPGATimestamp();
    }

    public void autonomousPeriodic() {
        if (start + duration <= Timer.getFPGATimestamp()) {
            drivesystem.drive(.5,0,0,0);
        }
    }

    public void teleopPeriodic() {
        drivesystem.update();
        manipulator.update();
        System.out.println("Yaw: " + drivesystem.gyro.getYaw());
        System.out.println("Roll: " + drivesystem.gyro.getRoll());
        System.out.println("Pitch: " + drivesystem.gyro.getPitch());
    }
}


