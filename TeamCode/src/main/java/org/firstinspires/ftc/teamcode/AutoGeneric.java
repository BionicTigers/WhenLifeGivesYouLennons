package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A class to run Autonomous given a strategy using the imu for current position and then using encoders to measure different for the turns.
 * Distances forward and backward is fully PID encoder methods based on inches.
 * Methods for this class is found in Navigation.
 */
public class AutoGeneric {
    public enum StartPos {DEPOT, CRATER, DOUBLESAMPLING}

    private StartPos startZone;
    private OpMode opMode;
    private Telemetry telemetry;
    private Navigation nav;

    /**
     * The constructor method that contains everything to run in initialization.
     *
     * @param startZone - StartPos enumerator. Tells which strategy to run. Options are dEPOT, CRATER, or dOUBLESAMPLING.
     * @param opMode    - The OpMode required to access motors. Often, 'this' will suffice.
     * @param telemetry - Telemetry of the current OpMode, used to output data to the screen.
     */
    public AutoGeneric(AutoGeneric.StartPos startZone, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        this.startZone = startZone;
        this.opMode = opMode;
        this.telemetry = telemetry;
        nav = new Navigation(opMode, telemetry, true);
        nav.calibrateHeading();
        nav.hold(0.1f);
    }

    //----Run this to run Autonomous----//
    public void runOpMode() {
        nav.updateCubePos();
        // nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
        nav.setLiftHeight(Navigation.LiftHeight.HOOK);
        nav.holdForLift();
        nav.distance(3f);
        nav.turnToHeading(-45f);
        nav.setLiftHeight(Navigation.LiftHeight.LOWER);
        nav.distance(14f);
        switch (nav.getCubePos()) {
            case MIDDLE:
                nav.distance(14f);
                nav.distance(-14f);
                break;
            case RIGHT:
                nav.turnToHeading(-90f);
                nav.distance(20f);
                nav.distance(-20f);
                break;
            default: //left
                nav.turnToHeading(0f);
                nav.distance(20f);
                nav.distance(-20f);
                break;
        }
        nav.turnToHeading(40f); //turn to face wall
        nav.distance(46f);
        //-----crater depot run-----//
        if (startZone == StartPos.CRATER) {
            nav.turnToHeading(-90f);
            nav.distance(-38f);
            // depot side //
        } else if (startZone == StartPos.DEPOT) {
            nav.turnToHeading(90f);
            nav.distance(-55f);
            //-----crater doublesampling------//
        } else if (startZone == StartPos.DOUBLESAMPLING) {
            nav.turnToHeading(-90f);
            nav.curveTurn(-35f, 10f, 0f, -3f);
            switch (nav.getCubePos()) {
                case MIDDLE:
                    nav.turnToHeading(-135f);
                    break;
                case RIGHT:
                    nav.turnToHeading(-100f);
                    break;
                default: //Left
                    nav.turnToHeading(-180f);
            }
            nav.distance(30f);
            nav.distance(-35f);
            nav.turnToHeading(273f);
        }
        //-----marker deploy and driving to crater-----//
        nav.setTeamMarker(0.8f);
        nav.hold(1);
        nav.distance(62f);
//        nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
//        nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
//        nav.hold(2);
    }
}