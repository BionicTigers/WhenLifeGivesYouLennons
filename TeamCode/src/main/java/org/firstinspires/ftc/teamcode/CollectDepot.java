package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the depot code pulled from AutoGeneric
 */
@Disabled
@Autonomous(name = "Collect  depot", group = "Auto")
public class CollectDepot extends LinearOpMode {
    public void runOpMode() {
        AutoCollect autoCollect = new AutoCollect(AutoCollect.StartPos.DEPOT, this, telemetry);
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
