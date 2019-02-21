package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the depot code pulled from AutoGeneric
 */
@Disabled
@Autonomous(name = "Collect  Crater", group = "Auto")

public class CollectCrater extends LinearOpMode {
    public void runOpMode() {
        AutoCollect autoCollect = new AutoCollect(AutoCollect.StartPos.CRATER, this, telemetry);
        while (!isStarted()) {
            telemetry.addData("cool", "waiting to start");
            telemetry.update();
        }
        // waitForStart();

        opModeIsActive();

        autoCollect.runAutonomous();
    }

    public boolean isStopping() {
        return opModeIsActive();
    }
}
