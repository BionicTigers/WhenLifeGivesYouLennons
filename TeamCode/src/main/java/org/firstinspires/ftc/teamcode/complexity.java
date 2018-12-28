package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name="thingyh")
public class complexity extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Navigation nav = new Navigation(this, telemetry, true);
        while (opModeIsActive()){
            nav.driveMethodComplex(2, 0, .00f,  -1f, -1f, false, 0.25f,.75f);
        }

    }
}
