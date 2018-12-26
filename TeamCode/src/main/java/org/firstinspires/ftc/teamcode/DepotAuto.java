package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@Autonomous(name="Depot Auto", group="Auto")
public class DepotAuto extends LinearOpMode {
    public void runOpMode() {
        AutoTester autoTester = new AutoTester(AutoTester.StartPos.DEPOTAUTO, this, telemetry);

        waitForStart();

        opModeIsActive();

        autoTester.runOpMode();
    }

    public boolean isStopping() {
        return opModeIsActive();
    }
}
