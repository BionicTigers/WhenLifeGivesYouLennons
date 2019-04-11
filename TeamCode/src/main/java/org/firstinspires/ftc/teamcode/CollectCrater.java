package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the depot code pulled from AutoGeneric
 */

@Autonomous(name = "Collect  Crater", group = "Auto")

public class CollectCrater extends LinearOpMode {
    public void runOpMode() {
        AutoOptions autoOptions = new AutoOptions(AutoOptions.StartPos.CRATER, this, telemetry);
        while (!isStarted()) {
            telemetry.addData("cool", "waiting to start");
            telemetry.update();
        }
        // waitForStart();

        opModeIsActive();

        autoOptions.runAutonomous();
    }

    public boolean isStopping() {
        return opModeIsActive();
    }
}
