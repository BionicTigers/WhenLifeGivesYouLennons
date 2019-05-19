package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the Depot code pulled from AutoGeneric
 */
@Autonomous(name = "Advanced Depot", group = "Advance")
public class AdvancedDepot extends LinearOpMode {
    public void runOpMode() {
        AutoAdvance autoAdvance = new AutoAdvance(AutoAdvance.StartPos.DEPOT, this, telemetry);

        while (!isStarted()) {
            telemetry.addData("coolio", "waiting to start this party");
            telemetry.update();
        }
        //waitForStart();
        autoAdvance.runAutonomous();
    }

    public boolean isStopping() {
        return opModeIsActive();
    }

}
