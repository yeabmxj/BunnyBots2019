package frc.team5115.wrappers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight   {
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    private NetworkTableEntry tv;

    public Limelight() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
    }
    public double getContourInfo(String s) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(s).getDouble(0);
    }

    public void setCameraControls(String s, double value) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry(s).setNumber(value);
    }

    public double getEntry(String variableName) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(variableName).getDouble(0);
    }

}
