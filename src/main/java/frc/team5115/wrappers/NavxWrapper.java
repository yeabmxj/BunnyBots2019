package frc.team5115.wrappers;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

import static frc.team5115.base.Constants.SAMPLE_SIZE;

public class NavxWrapper {
    private AHRS gyro;

    //X: Pitch  Y: Yaw  Z: Roll
    public enum AXIS {
        X, Y, Z
    }

    private double raw = 0;

    private double sumX = 0;
    private double sumY = 0;
    private double sumZ = 0;

    private double[] sliderX = new double[SAMPLE_SIZE];
    private double[] sliderY = new double[SAMPLE_SIZE];
    private double[] sliderZ = new double[SAMPLE_SIZE];

    private SerialPort.Port[] ports = {
            SerialPort.Port.kUSB,
            SerialPort.Port.kUSB1,
            SerialPort.Port.kUSB2
    };

    public NavxWrapper() {
        while(gyro == null || !gyro.isConnected()) {
            for(SerialPort.Port port : ports) gyro = new AHRS(port);
        }
    }

    private double getRawValue(AXIS axis) {
        switch(axis) {
            case X:
                raw = gyro.getPitch();
                break;
            case Y:
                raw = gyro.getYaw();
                break;
            case Z:
                raw = gyro.getRoll();
                break;
        }
        return raw;
    }

    public double getAcceleration(int direction, AXIS axis) {
        switch(axis) {
            case X:
                raw = gyro.getRawAccelX();
                break;
            case Y:
                raw = gyro.getRawAccelY();
                break;
            case Z:
                raw = gyro.getRawAccelZ();
                break;
        }
        return direction * raw;
    }

    public double getPitch() {
        for (int i = 0; i < sliderX.length-1; i++) sumX += sliderX[i];
        return sumX / sliderX.length;
    }

    public double getYaw() {
        for (int i = 0; i < sliderY.length-1; i++) sumY += sliderY[i];
        return sumY / sliderY.length;
    }

    public double getRoll() {
        for (int i = 0; i < sliderZ.length-1; i++) sumZ += sliderZ[i];
        return sumZ / sliderZ.length;
    }

    public void init() {
        sliderX[0] = gyro.getPitch();
        sliderY[0] = gyro.getYaw();
        sliderZ[0] = gyro.getRoll();
    }

    public void update() {
        System.arraycopy(sliderX, 0, sliderX, 1, sliderX.length - 1);
        System.arraycopy(sliderY, 0, sliderY, 1, sliderY.length - 1);
        System.arraycopy(sliderZ, 0, sliderZ, 1, sliderZ.length - 1);

        sliderX[0] = getRawValue(AXIS.X);
        sliderY[0] = getRawValue(AXIS.Y);
        sliderZ[0] = getRawValue(AXIS.Z);
    }
}
