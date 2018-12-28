package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class simple extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Navigation nav = new Navigation(this, telemetry, true);
        while (opModeIsActive()){
            nav.driveMethodComplex(2, 0, .00f,  -1f, -1f, false, 0.25f,.75f);
        }

    }
}
