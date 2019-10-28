package frc.team5115.wrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class VictorWrapper {
    private VictorSPX victorSPX;
    private DigitalInput upper;
    private DigitalInput lower;

    public VictorWrapper(int victorID) {
        victorSPX = new VictorSPX(victorID);
    }

    public VictorWrapper(int victorID, int upperID, int lowerID) {
        victorSPX = new VictorSPX(victorID);
        upper = new DigitalInput(upperID);
        lower = new DigitalInput(lowerID);
    }

    private boolean getHit() {
        return upper.get() && lower.get();
    }

    public void set(double value, boolean hit) {
        victorSPX.set(ControlMode.PercentOutput, hit ? value : 0);
    }

    public void set(double value) {
        victorSPX.set(ControlMode.PercentOutput, value);
    }

    public void setInverted(boolean inverted) {
        victorSPX.setInverted(inverted);
    }
}
