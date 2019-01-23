package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Auto Depot", group="Auto")
public class AutoDepot extends LinearOpMode {
    public void runOpMode() {
        AutoGeneric autoGeneric = new AutoGeneric(AutoGeneric.StartPos.DEPOT, this, telemetry);
        while(!isStarted()){
            telemetry.addData("cool","waiting to start");
            telemetry.update();
        }
       // waitForStart();

        opModeIsActive();

        autoGeneric.runOpMode();
    }

    public boolean isStopping() {
        return opModeIsActive();
    }
}
