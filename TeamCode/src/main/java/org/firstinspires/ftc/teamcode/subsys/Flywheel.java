package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Flywheel {
    private DcMotorEx flywheelMotor;
    private PDF controller;
    private double desiredSpeed;
    private boolean enabled;

    public Flywheel(HardwareMap hardwareMap) {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "flywheel");
        controller = new PDF(0, 0, 0);
        desiredSpeed = 0;
        enabled = false;
    }

    public void setDesiredSpeed(double speed) {
        desiredSpeed = speed;
    }

    public void update() {
        if (!enabled) {
            flywheelMotor.setPower(0);
            return;
        }
        double current = flywheelMotor.getVelocity();
        double power = controller.calc(desiredSpeed, current);
        flywheelMotor.setPower(power);
    }

    public void disable() {
        enabled = false;
        flywheelMotor.setPower(0);
    }

    public void enable() {
        enabled = true;
    }
}
