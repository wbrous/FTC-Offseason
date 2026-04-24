package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Sensors {
    private Rev2mDistanceSensor frontSensor;
    private RevColorSensorV3 colorSensor;
    private TouchSensor touchSensor;
    private Gyroscope gyroscope;

    public Sensors(HardwareMap hardwareMap) {

    }
}
