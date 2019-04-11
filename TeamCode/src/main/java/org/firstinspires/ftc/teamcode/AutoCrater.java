package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * A class made to run the crater code pulled from AutoGeneric
 */
@Autonomous(name = "Auto Crater", group = "Auto")
public class AutoCrater extends LinearOpMode {
    public void runOpMode() {
        AutoCollect autoCollect = new AutoCollect(AutoCollect.StartPos.CRATER, this, telemetry);

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
