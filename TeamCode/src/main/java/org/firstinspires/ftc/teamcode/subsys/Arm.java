package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Arm {
    private final Servo arm;

    private static final double MIN_POSITION = 0.25;
    private static final double MAX_POSITION = 0.75;

    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(Servo.class, "arm");
    }

    /** Set servo position, clamped to [MIN_POSITION, MAX_POSITION]. */
    public void setPos(double position) {
        arm.setPosition(Range.clip(position, MIN_POSITION, MAX_POSITION));
    }
}
