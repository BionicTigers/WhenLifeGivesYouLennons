package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Auto Double Sampling", group="Auto")
public class AutoDoubleSampling extends LinearOpMode {
    public void runOpMode() {
        AutoGeneric autoGeneric = new AutoGeneric(AutoGeneric.StartPos.DOUBLESAMPLING, this, telemetry);
        while(!isStarted()){
            telemetry.addData("cool","waiting to start");
            telemetry.update();
        }
        //waitForStart();

        autoGeneric.runOpMode();
    }
    public boolean isStopping() {
        return opModeIsActive();
    }
}
