package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ImuParameters;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Sensors {
    final double frontRangeInches = 10;
    final double blueCutoff = Math.pow(2,15);
    private Rev2mDistanceSensor frontSensor;
    private RevColorSensorV3 colorSensor;
    private TouchSensor touchSensor;
    private IMU imu;

    public Sensors(HardwareMap hardwareMap) {
        frontSensor = hardwareMap.get(Rev2mDistanceSensor.class, "frontSensor");
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");
        imu = hardwareMap.get(IMU.class, "imu");

        // let hunter cook :)
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN
        );

        imu.initialize(new IMU.Parameters(orientation));
    }

    public double getFrontDistance() {
        return frontSensor.getDistance(DistanceUnit.MM);
    }

    public boolean isFrontWithinRange() {
        return frontSensor.getDistance(DistanceUnit.INCH) < frontRangeInches;
    }

    public double getRedLevel() {
        return colorSensor.red() / (Math.pow(2, 16));
    }

    public boolean isBlueSample() {
        return colorSensor.blue() > blueCutoff;
    }

    public void toggleColorLight() {
        colorSensor.enableLed(!colorSensor.isLightOn()); // toggle the light (set to opposite of current state using not operator)
    }

    /**
     * returns distance in MM
     */
    public double getColorDistance() {
        return colorSensor.getDistance(DistanceUnit.MM);
    }

    public boolean isTouched() {
        return touchSensor.isPressed();
    }

    public YawPitchRollAngles getOrientation() {
        return imu.getRobotYawPitchRollAngles();
    }

    public double getYaw() {
        return getOrientation().getYaw();
    }
}
