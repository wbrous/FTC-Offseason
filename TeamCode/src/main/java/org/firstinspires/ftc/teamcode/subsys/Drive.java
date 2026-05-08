package org.firstinspires.ftc.teamcode.subsys;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {
    private final DcMotorEx frontLeft;
    private final DcMotorEx frontRight;
    private final DcMotorEx backLeft;
    private final DcMotorEx backRight;

    public Drive(HardwareMap hardwareMap) {
        frontLeft  = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight  = hardwareMap.get(DcMotorEx.class, "backRight");

        // Reverse left-side motors so positive power = forward
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Mecanum drive.
     * @param leftStickX  strafe input (positive = right)
     * @param leftStickY  forward input (negative = forward on gamepad)
     * @param rightStickX turn input (positive = clockwise)
     * @param rightTrigger slow-mode factor: 0.0 = full speed, 1.0 = zero speed
     */
    public void mecanum(double leftStickX, double leftStickY,
                        double rightStickX, double rightTrigger) {
        double speedMultiplier = 1.0 - rightTrigger; // 0→1 maps to 1→0

        double y = -leftStickY; // negate so pushing stick forward is positive
        double x =  leftStickX;
        double turn = rightStickX;

        double fl = y + x + turn;
        double fr = y - x - turn;
        double bl = y - x + turn;
        double br = y + x - turn;

        // Normalize so no value exceeds [-1, 1]
        double max = Math.max(Math.abs(fl),
                     Math.max(Math.abs(fr),
                     Math.max(Math.abs(bl), Math.abs(br))));
        if (max > 1.0) {
            fl /= max;
            fr /= max;
            bl /= max;
            br /= max;
        }

        frontLeft.setPower(fl * speedMultiplier);
        frontRight.setPower(fr * speedMultiplier);
        backLeft.setPower(bl * speedMultiplier);
        backRight.setPower(br * speedMultiplier);
    }

    /** Overload without slow-mode (full speed). */
    public void mecanum(double leftStickX, double leftStickY, double rightStickX) {
        mecanum(leftStickX, leftStickY, rightStickX, 0.0);
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
