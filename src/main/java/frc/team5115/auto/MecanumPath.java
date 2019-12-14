package frc.team5115.auto;

import frc.team5115.calculations.PID;

import static frc.team5115.robot.Robot.drivesystem;


public class MecanumPath {
    private PID translate;

    double speedCap = 1;

    double tolerance = 1;

    public enum states {
        STRAFE_LEFT, STRAFE_RIGHT, 
    }

    public MecanumPath() {
        translate = new PID(1,0,0);
    }

    public void drive(double translateX, double rotateY, double strafeZ) {
        translateX = translate.PID(translateX, 0, 1);
        rotateY = translate.PID(rotateY, 0, 1);
        strafeZ = translate.PID(strafeZ, 0, 1);

        //drivesystem.drive(rotateY, translateX, strafeZ, 0, speedCap,0);
    }
}
