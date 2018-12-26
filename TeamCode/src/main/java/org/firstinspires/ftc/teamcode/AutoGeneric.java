package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
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
        nav = new Navigation(opMode, telemetry,true);
        nav.hold(0.1f);


    }
    /**
     * Run this to run Autonomous.
     */
    public void runOpMode() {
        nav.updateCubePos();
        nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
        nav.setLiftHeight(Navigation.LiftHeight.HOOK);
        nav.holdForLift();
        nav.goDistance(3f);
        nav.holdForLift();
        nav.pointTurnRelative(-95f);
        nav.holdForDrive();
        nav.setLiftHeight(Navigation.LiftHeight.LOWER); //Lowering lift back to starting position
        nav.goDistance(13f);
        nav.holdForDrive();
        //-----crater depot run-----//
        if(startZone == StartPos.CRATER) {
            //-----unhooking-----//
            switch(nav.getCubePos()) {
                case MIDDLE:
                    nav.goDistance(15f);
                    nav.holdForDrive();
                    nav.goDistance(-8f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(97f);
                    nav.holdForDrive();
                    nav.goDistance(45.5f); //49
                    nav.holdForDrive();
                    nav.pointTurnRelative(-136f);
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-55f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-17f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(140f);
                    nav.holdForDrive();
                    nav.goDistance(48f); //47
                    nav.holdForDrive();
                    nav.pointTurnRelative(-128f);
                    break;
                default: //Left
                    nav.pointTurnRelative(50f);
                    nav.holdForDrive();
                    nav.goDistance(18f);
                    nav.holdForDrive();
                    nav.goDistance(-15f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(35f);
                    nav.holdForDrive();
                    nav.goDistance(42.5f); //50
                    nav.holdForDrive();
                    nav.pointTurnRelative(-135f);
                    break; }

                nav.holdForDrive();
                nav.goDistance(-40f);
                nav.holdForDrive();
                nav.setTeamMarker(0.8f);
                nav.hold(1);
                nav.goDistance(63f);
                nav.holdForDrive();
                nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
                nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
                nav.hold(2);
            }

        //-----crater doublesampling and depot run-----//
        else if(startZone == StartPos.DOUBLESAMPLING) {
            switch (nav.getCubePos()) {
                case MIDDLE:
                    nav.goDistance(15f);
                    nav.holdForDrive();
                    nav.goDistance(-9f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(85f);
                    nav.holdForDrive();
                    nav.goDistance(44f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(-135);
                    nav.holdForDrive();
                    nav.goDistance(-40f);
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-55f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-16f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(135f);
                    nav.holdForDrive();
                    nav.goDistance(44f); //50.5
                    nav.holdForDrive();
                    nav.pointTurnRelative(-134f);
                    nav.holdForDrive();
                    nav.goDistance(-40f);
                    break;
                default: //Left
                    nav.pointTurnRelative(50f);
                    nav.holdForDrive();
                    nav.goDistance(18f);
                    nav.holdForDrive();
                    nav.goDistance(-14f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(35f);
                    nav.holdForDrive();
                    nav.goDistance(42.5f); //47
                    nav.holdForDrive();
                    nav.pointTurnRelative(-130f); //128
                    nav.holdForDrive();
                    nav.goDistance(-45f);
                    break; }

                nav.holdForDrive();
            switch (nav.getCubePos()) {
                case MIDDLE:
                    nav.pointTurnRelative(-57f);
                    nav.holdForDrive();
                    nav.goDistance(30f);
                    nav.holdForDrive();
                    nav.goDistance(-31f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(59f);
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-20f);
                    nav.holdForDrive();
                    nav.goDistance(30f);
                    nav.holdForDrive();
                    nav.goDistance(-30f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(28f);
                    break;
                default: //middle
                    nav.pointTurnRelative(-80f);
                    nav.holdForDrive();
                    nav.goDistance(30f);
                    nav.holdForDrive();
                    nav.goDistance(-30f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(82f);
                    break; }
            nav.holdForDrive();
            nav.setTeamMarker(0.8f);
            nav.hold(1);
            nav.goDistance(63f);
            nav.holdForDrive();
            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
            nav.hold(2);
        }

        //-----depot depot run-----//
        else if (startZone == StartPos.DEPOT) {
            switch(nav.getCubePos()) {
                case MIDDLE:
                    nav.goDistance(15f);
                    nav.holdForDrive();
                    nav.goDistance(-12f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(80f);
                    nav.holdForDrive();
                    nav.goDistance(45f); //53
                    nav.holdForDrive();
                    nav.pointTurnRelative(50f);
                    break;
                case RIGHT:
                    nav.pointTurnRelative(-45f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-15f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(130f);
                    nav.holdForDrive();
                    nav.goDistance(47f); //50.5
                    nav.holdForDrive();
                    nav.pointTurnRelative(47f);
                    break;
                default:
                    nav.pointTurnRelative(50f);
                    nav.holdForDrive();
                    nav.goDistance(20f);
                    nav.holdForDrive();
                    nav.goDistance(-20f);
                    nav.holdForDrive();
                    nav.pointTurnRelative(30f);
                    nav.holdForDrive();
                    nav.goDistance(51f); //54
                    nav.holdForDrive();
                    nav.pointTurnRelative(51f);
                    break; }
            nav.holdForDrive();
            nav.goDistance(-58f);
            nav.holdForDrive();
            nav.pointTurnRelative(90f);
            nav.holdForDrive();
            nav.setTeamMarker(0.8f);
            nav.hold(1);
            nav.pointTurnRelative(-91f);
            nav.holdForDrive();
            nav.goDistance(63f);
            nav.holdForDrive();
            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
            nav.hold(2);
        }

    }
}