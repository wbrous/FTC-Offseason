package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    private Servo armServo;
    private double minPos = 0.0;
    private double maxPos = 1.0;

    public Arm(HardwareMap hardwareMap) {
        armServo = hardwareMap.get(Servo.class, "arm");
    }

    public void setPos(double pos) {
        double clamped = Math.max(minPos, Math.min(maxPos, pos));
        armServo.setPosition(clamped);
    }
}
