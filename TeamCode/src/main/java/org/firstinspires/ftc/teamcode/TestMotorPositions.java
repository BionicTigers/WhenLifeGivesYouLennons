package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Test Motor Positions", group="Test")
public class TestMotorPositions extends LinearOpMode {

    @Override public void runOpMode() {
        Navigation nav = new Navigation(this,telemetry,true);

        waitForStart();

        while(opModeIsActive()) {
//            nav.turnForHeading(90f,0.5f);
//            nav.gameState++;
//            nav.telemetryMethod();
//            nav.turnForHeading(-90f,0.5f);
//            nav.gameState++;
//            nav.telemetryMethod();
//            nav.turnForHeading(45f,0.5f);
//            nav.gameState++;
//            nav.telemetryMethod();
        }
    }

}
