package frc.team5115.calculations;

import frc.team5115.base.Time;

public class PID {
    private double P, I, D = 0;
    private double previousError = 0;
    private int integral = 0;

    private boolean finished = false;

    private Time timer;

    public PID(double P, double I, double D) {
        timer = new Time();
        timer.start();
        this.P = P;
        this.I = I;
        this.D = D;
    }

    public double PID(double setpoint, double currentValue, double tolerance) {
        timer.check();

        double currentError = currentValue - setpoint;
        finished = currentError < tolerance;
        integral += currentError * timer.getDifference();
        double derivative = (currentError - previousError) / timer.getDifference();
        previousError = currentError;

        return P * currentError + I * integral + D * derivative;
    }

    public void reset(){
        timer.start();
        previousError = 0;
        integral = 0;
        finished = false;
    }

    public boolean isFinished() { return finished; }
}