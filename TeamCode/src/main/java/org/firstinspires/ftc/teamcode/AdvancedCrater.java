package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the crater code pulled from AutoGeneric
 */
@Disabled
@Autonomous(name = "Advanced Crater", group = "Advance")

public class AdvancedCrater extends LinearOpMode {
    public void runOpMode() {
        AutoAdvance autoAdvance = new AutoAdvance(AutoAdvance.StartPos.CRATER, this, telemetry);

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
