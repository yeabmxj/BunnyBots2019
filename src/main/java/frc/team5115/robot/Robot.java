package frc.team5115.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;

import frc.team5115.base.Controls;
import frc.team5115.subsystems.*;
import frc.team5115.wrappers.Limelight;
import frc.team5115.wrappers.NavxWrapper;
import static frc.team5115.base.Constants.*;


public class Robot extends TimedRobot {

    public static Drivesystem drivesystem;

    public static Controls joy;
    public static Limelight limelight;
    public static BallManipulator ballManipulator;
    public static Manipulator manipulator;

    VictorSPX motor;

    public void robotInit() {
        drivesystem = new Drivesystem();

        joy = new Controls();
        limelight = new Limelight();
        manipulator = new Manipulator();

        drivesystem.init();
        //ballManipulator.init();
        manipulator.update();
        motor = new VictorSPX(8);
        limelight.cameraMode();
    }

    public void robotPeriodic() {
        //motor.set(ControlMode.PercentOutput, 1);
        drivesystem.update();
        //ballManipulator.update();
        manipulator.update();
    }
}


