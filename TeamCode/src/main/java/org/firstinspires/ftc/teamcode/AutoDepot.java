package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the depot code pulled from AutoCollect
 */
@Autonomous(name = "Auto Depot", group = "Auto")
public class AutoDepot extends LinearOpMode {
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
