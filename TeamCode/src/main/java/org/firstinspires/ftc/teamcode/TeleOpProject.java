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

    // Extra Motors
    DcMotor intake;
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
            // Main loop code goes here
        }
    }
}
