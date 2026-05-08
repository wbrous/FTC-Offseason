package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Sensors {
    private final IMU imu;
    private final RevColorSensorV3 colorSensor;

    public Sensors(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, "imu");
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "color");

        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(orientation));
    }

    public void resetIMU() {
        imu.resetYaw();
    }

    /** Returns heading (yaw) in degrees. */
    public double getHeading() {
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        return angles.getYaw(AngleUnit.DEGREES);
    }

    /** Green sample: dominant green channel. */
    public boolean isGreenSample() {
        int r = colorSensor.red();
        int g = colorSensor.green();
        int b = colorSensor.blue();
        return g > r && g > b;
    }

    /** Purple sample: red and blue both dominate green. */
    public boolean isPurpleSample() {
        int r = colorSensor.red();
        int g = colorSensor.green();
        int b = colorSensor.blue();
        return r > g && b > g;
    }
}
