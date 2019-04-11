package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the depot code pulled from AutoGeneric
 */

@Autonomous(name = "Collect DS", group = "Auto")
public class CollectDoubleSampling extends LinearOpMode {
    public void runOpMode() {
        AutoCollect autoCollect = new AutoCollect(AutoCollect.StartPos.DOUBLESAMPLING, this, telemetry);
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
