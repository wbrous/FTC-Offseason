package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class FirstTeleOp extends LinearOpMode {
    double armUp = 1;
    double armDown = 0;

    double servoMax = 0.75; // anything out of these values will cause damage to the servo!
    double servoMin = 0.25;

    double kP1 = 1 / 3000;
    double kD1 = 0.00001;
    double kF1 = 1 / 6000;

    double kP2 = 0;
    double kD2 = 0;
    double kF2 = 0;

    double desired = 1000;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private DcMotorEx flywheel1;
    private DcMotorEx flywheel2;

    private PDF controller1;
    private PDF controller2;


    private Servo arm;


    @Override
    public void runOpMode() throws InterruptedException {
        // code that runs after we press "init"

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");

        controller1 = new PDF(kP1, kD1, kF1);
        controller2 = new PDF(kP2, kD2, kF2);

        arm = hardwareMap.get(Servo.class, "arm");

        waitForStart();

        while (opModeIsActive()) {
            calculateDrive();

            controlArm(gamepad1.b);

            controlFlywheel();
        }
    }

    private double clipPower(double inputPower) {
        return Range.clip(inputPower, -1.0, 1.0);
    }

    private void calculateDrive() {
        double powerReduction;

        boolean isSlowMode = gamepad1.a;

        double triggerValue = gamepad1.right_trigger;

        if (isSlowMode) {
            powerReduction = Range.scale(triggerValue, 0, 1, 1, 0);
        } else {
            powerReduction = 1.0;
        }

        double turnAmount = gamepad1.right_stick_x;

        double x = gamepad1.left_stick_x; // if x is negative -> left
        double y = gamepad1.left_stick_y;

        double theta = Math.atan2(y, x); // angle that we want to go
        double r = Math.hypot(x, y); // power that we want to go at that angle

        double newY = r * Math.sin(theta);
        double newX = r * Math.cos(theta);

        double leftFrontPower = newY + newX + turnAmount;
        double rightFrontPower = newY - newX - turnAmount;
        double leftBackPower = newY + newX + turnAmount;
        double rightBackPower = newY - newX - turnAmount;

        leftFrontPower = clipPower(leftFrontPower);
        rightFrontPower = clipPower(rightFrontPower);
        leftBackPower = clipPower(leftBackPower);
        rightBackPower = clipPower(rightBackPower);

        leftFront.setPower(leftFrontPower * powerReduction);
        rightFront.setPower(rightFrontPower * powerReduction);
        leftBack.setPower(leftBackPower * powerReduction);
        rightBack.setPower(rightBackPower * powerReduction);
    }

    private void controlArm(boolean isArmUp) {

        double pos;

        if (isArmUp) {
            pos = armUp;
        } else {
            pos = armDown;
        }

        pos = Range.scale(pos, 0, 1, servoMin, servoMax);

        arm.setPosition(pos);
    }

    double lastError = 0;
    private void controlFlywheel() {
        // INTEGRATE PDF.java
    }
}