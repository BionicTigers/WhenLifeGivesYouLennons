package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
/**
 * This is a testing class (specifically with drive methods from autonomous)
 */
@Disabled
@Autonomous(name="Test Nav Methods")
public class TestNavMethods extends LinearOpMode {
    private Navigation nav;
    @Override

    public void runOpMode(){

        nav = new Navigation(this, telemetry, true);
        waitForStart();
        while (opModeIsActive()) {
        nav.updatePos();
        telemetry.addData("Position", nav.getPos());
        nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
        nav.hold(4f);
        nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
        nav.hold(4f);

        }

    }
    }


