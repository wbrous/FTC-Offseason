package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Sensors {
    private IMU imu;
    private ColorSensor colorSensor;

    public Sensors(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, "imu");
        colorSensor = hardwareMap.get(ColorSensor.class, "color");

        IMU.Parameters parameters = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
            )
        );
        imu.initialize(parameters);
    }

    public void resetIMU() {
        imu.resetYaw();
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public boolean isGreenSample() {
        return colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue();
    }

    public boolean isPurpleSample() {
        return colorSensor.red() > colorSensor.green() && colorSensor.blue() > colorSensor.green();
    }
}
