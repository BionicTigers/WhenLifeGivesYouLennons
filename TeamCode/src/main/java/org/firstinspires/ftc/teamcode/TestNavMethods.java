package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="Test Nav Methods")
public class TestNavMethods extends LinearOpMode {
    private Navigation nav;
    @Override
    public void runOpMode(){

        nav = new Navigation(this, telemetry, true);
        waitForStart();
        while (opModeIsActive()){
        nav.updatePos();
        telemetry.addData("Position", nav.getPos());
        //telemetry.addData("How the robot should move", nav.getCorrectionDeg(270));
        //if (gamepad1.a){nav.pointTurnRelative((int)nav.getCorrectionDeg(270));}
       //nav.telemetryMethod();
            nav.curveTurn(-40f,10f,0f,15f);
            nav.pointTurnRelative(-90f);
            nav.holdForDrive();
            nav.goDistance(30f);
            nav.holdForDrive();
            nav.goDistance(-30f);
            nav.holdForDrive();
            nav.pointTurnRelative(90f);
            nav.holdForDrive();
            nav.curveTurn(10f,-11f,0f,0f);

    }
    }
////

//        nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
//        nav.setCollectorExtension(Navigation.CollectorExtension.DUMP);
//        nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
//
//        nav.setLiftHeight(Navigation.LiftHeight.SCORE);
//        nav.setLiftHeight(Navigation.LiftHeight.HOOK);
//        nav.setLiftHeight(Navigation.LiftHeight.LOWER);

//        nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
//        nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
//        nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
//        nav.hold(3f);
//        nav.setCollectorHeight(Navigation.CollectorHeight.HOLD);
//        nav.setCollectorExtension(Navigation.CollectorExtension.DUMP);
//        nav.hold(3f);
//        nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
//        nav.hold(3f);
//        nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);

//        nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
//        nav.setCollectionSweeper(Navigation.CollectorSweeper.OUTTAKE);


    }


