package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the doublesampling code pulled from AutoCollect
 */
@Autonomous(name = "Auto Double Sampling", group = "Auto")
public class AutoDoubleSampling extends LinearOpMode {
    public void runOpMode() {
        AutoCollect autoCollect = new AutoCollect(AutoCollect.StartPos.DOUBLESAMPLING, this, telemetry);
        while (!isStarted()) {
            telemetry.addData("cool", "waiting to start");
            telemetry.update();
        }
        //waitForStart();

        autoCollect.runAutonomous();
    }

    public boolean isStopping() {
        return opModeIsActive();
    }
}
