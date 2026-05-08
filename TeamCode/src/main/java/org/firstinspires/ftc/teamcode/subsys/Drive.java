package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

    public Drive(HardwareMap hardwareMap) {
        frontLeft  = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight  = hardwareMap.get(DcMotorEx.class, "backRight");
    }

    public void mecanumDrive(double leftStickX, double leftStickY, double rightStickX) {
        mecanumDrive(leftStickX, leftStickY, rightStickX, 0.0);
    }

    public void mecanumDrive(double leftStickX, double leftStickY, double rightStickX, double rightTrigger) {
        double y = -leftStickY;
        double x = leftStickX;
        double rx = rightStickX;

        double fl = y + x + rx;
        double bl = y - x + rx;
        double fr = y - x - rx;
        double br = y + x - rx;

        double max = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
        fl /= max;
        bl /= max;
        fr /= max;
        br /= max;

        double multiplier = 1.0 - rightTrigger * 0.5;
        fl *= multiplier;
        bl *= multiplier;
        fr *= multiplier;
        br *= multiplier;

        frontLeft.setPower(fl);
        backLeft.setPower(bl);
        frontRight.setPower(fr);
        backRight.setPower(br);
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
