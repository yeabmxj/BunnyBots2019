package frc.team5115.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.team5115.base.Heartbeat;
import static frc.team5115.base.Constants.*;

import static frc.team5115.base.Constants.*;
import static frc.team5115.robot.Robot.joy;

public class BallManipulator {

    WPI_TalonSRX elevator;
    WPI_TalonSRX shooter;
    WPI_VictorSPX intake;

    Heartbeat timer;

    boolean directionToggle = false;

    //tee hee it says ball
    public BallManipulator(){
        elevator = new WPI_TalonSRX(ELEVATOR_ID);
        shooter = new WPI_TalonSRX(SHOOTER_ID);
        intake = new WPI_VictorSPX(INTAKE_ID);
        timer = new Heartbeat();

        shooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        shooter.config_kP(0, FLYWHEEL_PROPORTIONAL);
        shooter.config_kI(0, FLYWHEEL_INTEGRAL);
        shooter.config_kD(0, FLYWHEEL_DIFFERENTIAL);
    }

    public void startTimer(){
        timer.start();
    }

    public void intake(){
        intake.set(ControlMode.PercentOutput, 1);
    }

    public void zeroIntake(){
        intake.set(ControlMode.PercentOutput, 0);
    }

    public void shoot(){
//        if(timer.getDifference() > 500){
//            timer.check();
//            directionToggle = !directionToggle;
//        }
//        if(directionToggle){
//            lower();
//        } else {
//            raise();
//        }
        raise();
        shooter.set(ControlMode.Velocity, metersToTicks(computeInitialVelocity()));
        System.out.println(shooter.getSelectedSensorVelocity());
    }

    public void raise(){
        elevator.set(ControlMode.PercentOutput, 0.5);
    }

    public void lower(){
        elevator.set(ControlMode.PercentOutput, -0.5);
    }

    public void zeroShooter(){
        elevator.set(ControlMode.PercentOutput, 0);
        shooter.set(ControlMode.PercentOutput, 0);
    }

    //returns in m/s
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
        if (joy.intake()) {
            intake();
        }
        else {
            zeroIntake();
        }

        if (joy.outtake()) {
            shoot();
        }
    }
}