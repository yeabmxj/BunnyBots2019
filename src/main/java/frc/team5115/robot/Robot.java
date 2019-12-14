package frc.team5115.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import frc.team5115.base.Controls;
import frc.team5115.subsystems.*;
import frc.team5115.wrappers.Limelight;


public class Robot extends TimedRobot {

    public static Drivesystem drivesystem;

    public static Controls joy;
    public static Limelight limelight;
    public static Manipulator manipulator;

    public void robotInit() {
        drivesystem = new Drivesystem();

        joy = new Controls();
        limelight = new Limelight();
        manipulator = new Manipulator();

        drivesystem.init();
        manipulator.update();
        limelight.cameraMode();
    }

    public void robotPeriodic() {
        drivesystem.update();
        manipulator.update();
        System.out.println("Yaw: " + drivesystem.gyro.getYaw());
        System.out.println("Roll: " + drivesystem.gyro.getRoll());
        System.out.println("Pitch: " + drivesystem.gyro.getPitch());
    }
}


