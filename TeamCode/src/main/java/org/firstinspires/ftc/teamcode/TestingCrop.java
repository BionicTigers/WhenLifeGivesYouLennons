package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name = "thing")
public class TestingCrop extends LinearOpMode {
    @Override
    public void runOpMode() {
        Navigation nav = new Navigation(this,telemetry,true,false);
        waitForStart();
        while (opModeIsActive()){
            nav.telemetryMethod();        }
    }
}
