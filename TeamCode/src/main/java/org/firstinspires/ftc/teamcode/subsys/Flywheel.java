package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Flywheel {
    private final DcMotorEx motor;
    private final PDF controller;

    private double desiredSpeed;
    private boolean enabled;

    public Flywheel(HardwareMap hardwareMap, double kP, double kD, double kF) {
        motor = hardwareMap.get(DcMotorEx.class, "flywheel");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        controller = new PDF(kP, kD, kF);
        desiredSpeed = 0;
        enabled = false;
    }

    public void setDesiredSpeed(double speed) {
        this.desiredSpeed = speed;
    }

    /** Call every loop iteration. Reads encoder velocity, applies PDF, sets power. */
    public void update() {
        if (!enabled) {
            motor.setPower(0);
            return;
        }
        double currentVelocity = motor.getVelocity(); // ticks/sec
        double power = controller.calc(desiredSpeed, currentVelocity);
        motor.setPower(power);
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
        motor.setPower(0);
    }
}
