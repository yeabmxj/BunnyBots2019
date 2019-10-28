package frc.team5115.wrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class TalonWrapper {
    private TalonSRX talonSRX;
    private DigitalInput upper;
    private DigitalInput lower;

    public TalonWrapper(int talonID) {
        talonSRX = new TalonSRX(talonID);
    }

    public TalonWrapper(int talonID, int upperID, int lowerID) {
        talonSRX = new TalonSRX(talonID);
        upper = new DigitalInput(upperID);
        lower = new DigitalInput(lowerID);
    }

    private boolean getHit() {
        return upper.get() && lower.get();
    }

    public void set(double value, boolean hit) {
        talonSRX.set(ControlMode.PercentOutput, hit ? value : 0);
    }

    public void set(double value) {
        talonSRX.set(ControlMode.PercentOutput, value);
    }

    public void configSelectedFeedbackSensor(FeedbackDevice e) {
        talonSRX.configSelectedFeedbackSensor(e);
    }
}
