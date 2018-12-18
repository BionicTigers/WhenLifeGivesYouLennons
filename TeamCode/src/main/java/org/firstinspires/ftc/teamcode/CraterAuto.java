package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@Autonomous(name="CraterAuto", group="Auto")
public class CraterAuto extends LinearOpMode {
    public void runOpMode() {
        AutoTester autoTester = new AutoTester(AutoTester.StartPos.CRATERAUTO, this, telemetry);

        waitForStart();

        opModeIsActive();

        autoTester.runOpMode();
    }

    public boolean isStopping() {
        return opModeIsActive();
    }
}
