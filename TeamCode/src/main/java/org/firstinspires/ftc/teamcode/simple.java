package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Disabled
@Autonomous(name ="simple ")
public class simple extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Navigation nav = new Navigation(this, telemetry, true);
        while (opModeIsActive()){
            nav.goDistance(2f,0.55f,0.55f);
            nav.holdForDrive();
            nav.goDistance(-2f,0.55f,0.55f);

        }

    }
}
