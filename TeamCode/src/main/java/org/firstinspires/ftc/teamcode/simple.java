package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name ="simple ")
public class simple extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Navigation nav = new Navigation(this, telemetry, true);
        while (opModeIsActive()){
            nav.goDistance(2f);
            nav.holdForDrive();
            nav.goDistance(-2);

        }

    }
}
