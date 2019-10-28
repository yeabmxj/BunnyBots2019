package frc.team5115.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import frc.team5115.base.Controls;
import frc.team5115.subsystems.*;
import frc.team5115.wrappers.Limelight;
import frc.team5115.wrappers.NavxWrapper;

public class Robot extends TimedRobot {

    public static Drivesystem drivesystem;
    public static Intake intake;
    public static Outtake outtake;

    public static Controls joy;
    public static NavxWrapper gyro;
    public static Limelight limelight;

    public void robotInit() {
        drivesystem = new Drivesystem();
        intake = new Intake();
        outtake = new Outtake();

        joy = new Controls();
        gyro = new NavxWrapper();
        limelight = new Limelight();

        gyro.init();
        intake.init();
        outtake.init();
        drivesystem.init();
    }

    public void robotPeriodic() {
        gyro.update();
        intake.update(joy.intake());
        outtake.update(joy.outtake());
        drivesystem.update();
    }
}


