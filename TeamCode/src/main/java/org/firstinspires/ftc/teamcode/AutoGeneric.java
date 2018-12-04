package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.webserver.RobotControllerWebHandlers;

/**
 * A class to run Autonomous given a strategy.
 */
public class AutoGeneric{

    public enum StartPos {DEPOT, CRATER, DOUBLESAMPLING};
    private StartPos startZone;
    private OpMode opMode;
    private Telemetry telemetry;
    private Navigation nav;

    /**
     * The constructor method that contains everything to run in initialization.
     * @param startZone - StartPos enumerator. Tells which strategy to run. Options are DEPOT, CRATER, or DOUBLESAMPLING.
     * @param opMode - The OpMode required to access motors. Often, 'this' will suffice.
     * @param telemetry - Telemetry of the current OpMode, used to output data to the screen.
     */
    public AutoGeneric(StartPos startZone, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        this.startZone = startZone;
        this.opMode = opMode;
        this.telemetry = telemetry;
        nav = new Navigation(opMode, telemetry,false,true);
        nav.hold(0.1f);
       // nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);

    }
//fight me
// UM THATS NOT VERY GP!
    /**
     * Run this to run Autonomous.
     */
    public void runOpMode() {
        nav.updateCubePos();
//        nav.setLiftHeight(Navigation.LiftHeight.HOOK);
//        nav.holdForLift();
//        nav.goDistance(2f);
        //-----crater depot run-----//
        if(startZone == StartPos.CRATER) {

            //-----unhooking-----//
            nav.pointTurnRelative(-90f);
            nav.holdForDrive();
           // nav.setLiftHeight(Navigation.LiftHeight.LOWER);
            nav.goDistance(13f);
            nav.holdForDrive();
            switch(nav.getCubePos()) {
                case LEFT:
                    nav.pointTurnRelative(55f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-20f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(38f);
                    nav.holdForDrive();
                    nav.goDistance(50f);
                    nav.holdForDrive();
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-50f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-20f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(135f);
                    nav.holdForDrive();
                    nav.goDistance(47f);
                    nav.holdForDrive();
                    break;
                default:
                    nav.goDistance(15f);
                    nav.holdForDrive();
                    nav.goDistance(-15f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(90f);
                    nav.holdForDrive();
                    nav.goDistance(49f);
                    nav.holdForDrive();
                    break;
            }
            nav.pointTurnRelative(-135f);
            nav.holdForDrive();
            nav.goDistance(-40f);
            nav.holdForDrive();
            nav.setTeamMarker(0.8f);
            nav.hold(1);
            nav.goDistance(63f);
            nav.holdForDrive();
            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
            nav.hold(5);
            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT); //breaking crater plane
            nav.hold(2);
        }

        //-----crater doublesampling and depot run-----//
        else if(startZone == StartPos.DOUBLESAMPLING) {
            //-----unhooking-----//
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
            nav.pointTurnRelative(-90f);
            nav.holdForDrive();
           // nav.setLiftHeight(Navigation.LiftHeight.LOWER);
            nav.goDistance(13f);
            nav.holdForDrive();
            switch (nav.getCubePos()) {
                case LEFT:
                    nav.pointTurnRelative(55f);
                    nav.holdForDrive();
                    nav.goDistance(20);
                    nav.holdForDrive();
                    nav.goDistance(-13f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(38f);
                    nav.holdForDrive();
                    nav.goDistance(47f);
                    nav.holdForDrive();
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-45f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-15f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(137f);
                    nav.holdForDrive();
                    nav.goDistance(50.5f);
                    nav.holdForDrive();
                    break;
                default:
                    nav.goDistance(15f);
                    nav.holdForDrive();
                    nav.goDistance(-10f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(95f);
                    nav.holdForDrive();
                    nav.goDistance(57f);
                    nav.holdForDrive();
                    break;
            }
            nav.pointTurnRelative(-135f);
            nav.holdForDrive();
            nav.goDistance(-40f);
            nav.holdForDrive();
            switch (nav.getCubePos()) {
                case LEFT:
                    nav.pointTurnRelative(-80f);
                    nav.holdForDrive();
                    nav.goDistance(30f);
                    nav.holdForDrive();
                    nav.goDistance(-30f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(82f);
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-25f);
                    nav.holdForDrive();
                    nav.goDistance(30f);
                    nav.holdForDrive();
                    nav.goDistance(-28f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(22f);
                    break;
                default: //middle
                    nav.pointTurnRelative(-50f);
                    nav.holdForDrive();
                    nav.goDistance(30f);
                    nav.holdForDrive();
                    nav.goDistance(-31f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(49f);
                    break;
            }
            nav.holdForDrive();
            nav.setTeamMarker(0.8f);
            nav.hold(1);
            nav.goDistance(63f);
            nav.holdForDrive();
            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
            nav.hold(5);
            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT); //breaking crater plane
            nav.hold(2);
        }

        //-----depot depot run-----//
        else if (startZone == StartPos.DEPOT) {
            //-----unhooking-----//
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
            nav.pointTurnRelative(-90f);
            nav.holdForDrive();
           // nav.setLiftHeight(Navigation.LiftHeight.LOWER);
            nav.goDistance(13f);
            nav.holdForDrive();
            switch(nav.getCubePos()) {
                case LEFT:
                    nav.pointTurnRelative(55f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-20f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(39f);
                    nav.holdForDrive();
                    nav.goDistance(54f);
                    nav.holdForDrive();
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-45f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-15f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(135f);
                    nav.holdForDrive();
                    nav.goDistance(50.5f);
                    nav.holdForDrive();
                    break;
                default:
                    nav.goDistance(15f);
                    nav.holdForDrive();
                    nav.goDistance(-12f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(92f);
                    nav.holdForDrive();
                    nav.goDistance(53f);
                    nav.holdForDrive();
                    break;
            }

            nav.pointTurnRelative(45f);
            nav.holdForDrive();
            nav.goDistance(-58f);
            nav.holdForDrive();
            nav.pointTurnRelative(90f);
            nav.holdForDrive();
            nav.setTeamMarker(0.8f);
            nav.hold(1);
            nav.pointTurnRelative(-90f);
            nav.holdForDrive();
            nav.goDistance(63f);
            nav.holdForDrive();
            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
            nav.hold(5);
            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT); //breaking crater plane
            nav.hold(2);
        }

    }
}