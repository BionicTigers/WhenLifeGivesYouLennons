package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Autonomous(name = "lol")
public class PIDF extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx boardEx = (DcMotorEx) hardwareMap.dcMotor.get("clark");
        Trajectory trajectory = new Trajectory(8f,10f,14f,80f);
        float[][]traj = trajectory.getTraj();
        waitForStart();
        while (opModeIsActive()) {
           boardEx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            boardEx.setPower(1);
           telemetry.addData("VEL", boardEx.getVelocity());
           telemetry.update();

        }
    }

}
