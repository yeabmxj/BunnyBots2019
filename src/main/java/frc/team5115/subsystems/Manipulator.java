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

    public void startTimer(){
        timer.start();
    }

    public void intake(){
        intake.set(ControlMode.PercentOutput, -1);
    }

    public void zeroIntake(){
        intake.set(ControlMode.PercentOutput, .4);
    }

    public void shoot(){

        /*
        double start = Timer.getFPGATimestamp();

         */

        raise();
        shooter.set(ControlMode.Velocity, metersToTicks(computeInitialVelocity()));
        shooter.set(ControlMode.PercentOutput, 1);
        System.out.println(shooter.getSelectedSensorVelocity());
    }

    public void raise(){
        elevator.set(ControlMode.PercentOutput, -1);
    }

    public void lower(){
        elevator.set(ControlMode.PercentOutput, 0);
    }

    public void zeroShooter(){
        elevator.set(ControlMode.PercentOutput, 0);
        shooter.set(ControlMode.PercentOutput, 0);
    }

    public double computeInitialVelocity(){
        double numerator = GRAVITY_METERS * Math.pow(DISTANCE_TEMPORARY_METERS, 2);
        double denominator = (MAGIC_CONSTANT * (Math.pow(DISTANCE_TEMPORARY_METERS, 2) * Math.tan(BALL_ANGLE_RADIANS) - Y_TARGET_METERS));
        return Math.sqrt(numerator / denominator);
    }

    public double metersToTicks(double meters) {
        return 0;
    }

    public void init() {
        startTimer();
    }

    public void update() {
        if (joy.intake()) { intake(); }
        else { zeroIntake(); }

        if (joy.outtake()) { shoot(); }
        else {
            zeroShooter();
            lower();
        }
    }
} 