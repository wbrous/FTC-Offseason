package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsys.Arm;
import org.firstinspires.ftc.teamcode.subsys.Drive;
import org.firstinspires.ftc.teamcode.subsys.Flywheel;
import org.firstinspires.ftc.teamcode.subsys.Sensors;

@TeleOp(name="WilsTeleOp")
public class WilsTeleOp extends LinearOpMode {

    private Drive drive;
    private Arm arm;
    private Flywheel flywheel;
    private Sensors sensors;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap);
        arm = new Arm(hardwareMap);
        flywheel = new Flywheel(hardwareMap);
        sensors = new Sensors(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            drive.mecanumDrive(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                gamepad1.right_stick_x,
                gamepad1.right_trigger
            );

            if (gamepad2.a) {
                flywheel.enable();
                flywheel.setDesiredSpeed(2000);
            }
            if (gamepad2.b) {
                flywheel.disable();
            }

            flywheel.update();

            double armPos = (-gamepad2.right_stick_y + 1.0) / 2.0;
            arm.setPos(armPos);
        }
    }
}
