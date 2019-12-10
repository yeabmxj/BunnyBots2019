package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import frc.team5115.base.Heartbeat;

import static frc.team5115.base.Constants.*;
import static frc.team5115.robot.Robot.joy;

public class Manipulator {
    TalonSRX shooter;
    VictorSPX elevator;
    TalonSRX intake;

    Heartbeat timer;

    boolean directionToggle = false;

    public Manipulator() {
        shooter = new TalonSRX(SHOOTER_ID);
        elevator = new VictorSPX(ELEVATOR_ID);
        intake = new TalonSRX(INTAKE_ID);

        shooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        shooter.config_kP(0, FLYWHEEL_PROPORTIONAL);
        shooter.config_kI(0, FLYWHEEL_INTEGRAL);
        shooter.config_kD(0, FLYWHEEL_DIFFERENTIAL);

        timer = new Heartbeat();
    }



    public void update() {
        if (joy.intake()) {
            intake.set(ControlMode.PercentOutput, -.85);
        }
        else {
            intake.set(ControlMode.PercentOutput, .85);
        }

        if (joy.outtake()) {
            shooter.set(ControlMode.PercentOutput, 1);
            elevator.set(ControlMode.PercentOutput, -1);
        }
        else {
            shooter.set(ControlMode.PercentOutput, 0);
            elevator.set(ControlMode.PercentOutput, .5);
        }
    }
}