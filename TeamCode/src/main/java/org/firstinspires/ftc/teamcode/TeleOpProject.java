package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TeleOpProject")
public class TeleOpProject extends LinearOpMode {

    // Main Movement Motors
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    // i don't even know what this one does :sob:
    DcMotorEx launchLeft;
    DcMotorEx launchRight;

    // Extra Motors
    DcMotor intake;
    boolean intakeOn = false;
    boolean lastA = false; // debouncing

    Servo hood;

    PDF flywheelController;


    @Override
    public void runOpMode() throws InterruptedException {
        // movement motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        launchLeft = hardwareMap.get(DcMotorEx.class, "launchLeft");
        launchLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // extra motors
        intake = hardwareMap.get(DcMotor.class, "intake");
        hood = hardwareMap.get(Servo.class, "hood");

        flywheelController = new PDF(0, 0, 0);

        waitForStart();

        while (opModeIsActive()) {
            /* Section 1: Mecanum Drive */
            double y = -gamepad1.left_stick_y; // negative because y-stick is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double frontLeftPower = y + x + rx;
            double backLeftPower = y - x + rx;
            double frontRightPower = y - x - rx;
            double backRightPower = y + x - rx;

            // Denominator is the largest motor power (absolute value) or 1.0.
            // This scales all motor powers down proportionally so they stay
            // between -1 and 1 while maintaining the robot's intended direction.
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            frontLeftPower /= denominator;
            backLeftPower /= denominator;
            frontRightPower /= denominator;
            backRightPower /= denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            /* Section 2: Intake Toggle using A button */
            if (gamepad1.a && !lastA) {
                intakeOn = !intakeOn;
            }
            lastA = gamepad1.a;

            if (intakeOn) {
                intake.setPower(1.0);
            } else {
                intake.setPower(0.0);
            }

            /* Section 3: Hood Servo */
            double joystickValue = gamepad1.right_stick_y;

            // max .55 min .35 middle .45
            double scaledHoodPosition = .45 - (joystickValue * .1);
            hood.setPosition(scaledHoodPosition);

            /* Section 4: Flywheel PDF Control */
            double currentVelocity = launchLeft.getVelocity();
            double power = flywheelController.calc(2000, currentVelocity);

            launchLeft.setPower(power);
        }
    }
}
