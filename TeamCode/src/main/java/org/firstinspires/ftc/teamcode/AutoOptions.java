package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
 * A class to run Autonomous given a strategy using the imu for current position and then using encoders to measure different for the turns.
 * Distances forward and backward is fully PID encoder methods based on inches.
 * Methods for this class is found in Navigation.
 *
 * This is our "second" iteration of autonomous and most current version of the autonomous.
 * There is a plan to eventually retire methods of this autonomous and create a new auto using motion tracking and vuforia to track distances as seen in TrajectoryGen.
 * This class is used during competition and practice and includes all of the three autonomous programs by calling them (autocrater, autodepot, autodoublesampling)
 *
 */
public class AutoOptions {
    public enum StartPos {DEPOT, CRATER, DOUBLESAMPLING}

    private StartPos startZone;
    private OpMode opMode;
    private Telemetry telemetry;
    private Navigation nav;

    /**
     * The constructor method that zcontains everything to run in initialization.
     *
     * @param startZone - StartPos enumerator. Tells which strategy to run. Options are DEPOT, CRATER, or DOUBLESAMPLING.
     * @param opMode    - The OpMode required to access motors. Often, 'this' will suffice.
     * @param telemetry - Telemetry of the current OpMode, used to output data to the screen.
     */
    public AutoOptions(AutoOptions.StartPos startZone, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        this.startZone = startZone;
        this.opMode = opMode;
        this.telemetry = telemetry;
        nav = new Navigation(opMode, telemetry, true);
        nav.calibrateHeading();
        nav.hold(0.1f);
    }

    /**
     * Class which runs an autonomous given AutoGeneric has been instantiated.
     */
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
            case MIDDLE:
                nav.goDistanceHold(14f);
                nav.goDistanceHold(-14f);
                break;
            case RIGHT:
                nav.pointTurnIMU(-93.5f);
                nav.goDistanceHold(20.5f);
                nav.goDistanceHold(-20.5f);
                break;
            default: //left
                nav.pointTurnIMU(3.25f);
                nav.goDistanceHold(26f);
                nav.goDistanceHold(-26f);
                break;
        }
        //-----crater depot run-----//
        if (startZone == StartPos.CRATER) {
            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(-45f);
            nav.pointTurnIMU(0f);
            nav.curveTurn(-48f, 0f, 11f, -6f);
            nav.hold(7f);
            nav.goDistanceHold(10f);
            nav.pointTurnIMU(180f);
            nav.goDistanceHold(-60f);
            // depot side //
        }

        //-----marker deploy and driving to crater-----//
        nav.holdForDrive();
        nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
        nav.hold(1f);

    }
}