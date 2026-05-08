package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsys.Arm;
import org.firstinspires.ftc.teamcode.subsys.Drive;
import org.firstinspires.ftc.teamcode.subsys.Flywheel;
import org.firstinspires.ftc.teamcode.subsys.Sensors;

@TeleOp(name = "WilsTeleOp")
public class WilsTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive       = new Drive(hardwareMap);
        Arm arm           = new Arm(hardwareMap);
        Flywheel flywheel = new Flywheel(hardwareMap, 1.0/3000, 0.00001, 1.0/6000);
        Sensors sensors   = new Sensors(hardwareMap);

        flywheel.setDesiredSpeed(1000);

        waitForStart();

        while (opModeIsActive()) {
            // Drivetrain — 4-arg mecanum with slow-mode
            drive.mecanum(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                gamepad1.right_stick_x,
                gamepad1.right_trigger
            );

            // Arm — B button toggles up/down
            arm.setPos(gamepad1.b ? 0.75 : 0.25);

            // Flywheel — A enables, X disables
            if (gamepad1.a) flywheel.enable();
            if (gamepad1.x) flywheel.disable();
            flywheel.update();

            // Telemetry
            telemetry.addData("Heading", sensors.getHeading());
            telemetry.addData("Green?", sensors.isGreenSample());
            telemetry.addData("Purple?", sensors.isPurpleSample());
            telemetry.update();
        }

        drive.stop();
    }
}
