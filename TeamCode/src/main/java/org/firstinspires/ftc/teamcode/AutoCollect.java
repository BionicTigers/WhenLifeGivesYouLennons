package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A class to run Autonomous given a strategy using the imu for current position and then using encoders to measure different for the turns and collects from the crater at the end of auto
 * Distances forward and backward is fully PID encoder methods based on inches.
 * Methods for this class is found in Navigation.
 * <p>
 * This class is currently in progress and is yet to be used in competition
 */
public class AutoCollect {
    public enum StartPos {DEPOT, CRATER, DOUBLESAMPLING}

    private StartPos startZone;
    private OpMode opMode;
    private Telemetry telemetry;
    private Navigation nav;

    /**
     * The constructor method that zcontains everything to run in initialization.
     *
     * @param startZone - StartPos enumerator. Tells which strategy to run. Options are dEPOT, CRATER, or dOUBLESAMPLING.
     * @param opMode    - The OpMode required to access motors. Often, 'this' will suffice.
     * @param telemetry - Telemetry of the current OpMode, used to output data to the screen.
     */
    public AutoCollect(AutoCollect.StartPos startZone, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        this.startZone = startZone;
        this.opMode = opMode;
        this.telemetry = telemetry;
        nav = new Navigation(opMode, telemetry, true);
        nav.calibrateHeading();
        nav.hold(0.1f);
    }

    //----Run this to run Autonomous----//
    public void runAutonomous() {
        nav.updateCubePos();
        nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
        nav.setLiftHeight(Navigation.LiftHeight.HOOK);
        nav.holdForLift();
        nav.goDistanceHold(3f);
        nav.pointTurnIMU(-45f);
        nav.setLiftHeight(Navigation.LiftHeight.LOWER);
        nav.goDistanceHold(14f);
        switch (nav.getCubePos()) {
            case RIGHT:
                nav.pointTurnIMU(-93.55f);
                break;
            case LEFT: //left
                nav.pointTurnIMU(3.55f);
                break;
        }

        nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
        nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
        nav.hold(2f);
        nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
        nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);


        //-----crater depot run-----//
        if (startZone == StartPos.CRATER) {
            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(45.5f);
            nav.pointTurnIMU(-90.5f);
            nav.goDistanceHold(-37f);
            nav.pointTurnIMU(-89f);
            // depot side //
        } else if (startZone == StartPos.DEPOT) {
            nav.setCollectorHeight(Navigation.CollectorHeight.HOLD);
            nav.goDistanceHold(-10f);
            nav.hold(0.5f);
            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.DROP);
            nav.holdForLiftJr();
            nav.goDistanceHold(10f);
            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.LOWER);

            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(44.5f);
            nav.pointTurnIMU(90f);
            nav.goDistanceHold(-49f);
            nav.pointTurnIMU(89f);
            //-----crater doublesampling------//
        } else if (startZone == StartPos.DOUBLESAMPLING) {

            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(45f);
            nav.pointTurnIMU(-90f);
            nav.curveTurn(-40f, 10f, 0f, -4f);
            switch (nav.getCubePos()) {
                case MIDDLE:
                    nav.pointTurnIMU(-135f);
                    break;
                case RIGHT:
                    nav.pointTurnIMU(-103f);
                    break;
                default: //Left
                    nav.pointTurnIMU(-180f);
            }
            nav.goDistanceHold(5f);

            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
            nav.hold(2f);
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);
            nav.goDistanceHold(9f);
            nav.pointTurnIMU(277f);
        }
        //-----marker deploy and driving to crater-----//
        nav.setTeamMarker(0.8f);
        nav.hold(1);
        switch (startZone) {
            case DOUBLESAMPLING:
                nav.goDistance(69f, 0.6f, 0.6f);
                nav.holdForDrive();
                nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
                break;
            default: //left
                nav.goDistance(58f, 0.6f, 0.6f);
                nav.holdForDrive();
                nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
                nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
                nav.hold(2f);
                break;
        }


    }
}