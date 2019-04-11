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
public class AutoOptions {
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
    public AutoOptions(AutoOptions.StartPos startZone, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
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
                nav.setCollectorExtension(Navigation.CollectorExtension.DUMP);
                break;
            case LEFT: //left
                nav.pointTurnIMU(0f);
                nav.setCollectorExtension(Navigation.CollectorExtension.DUMP);
                break;
        }

        nav.setCollectorHeight(Navigation.CollectorHeight.LAND);
        nav.setCollectionSweeper(Navigation.CollectorSweeper.OUTTAKE);
        nav.hold(1f);

        nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
        nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
        nav.hold(0.25f);
        nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);

        nav.pointTurnIMU(36f); //turn to face wall
        nav.goDistanceHold(45f);
        nav.pointTurnIMU(90f);
        nav.goDistanceHold(-40f);

        if (startZone == StartPos.CRATER) {
            nav.pointTurnIMU(-90f);
        } else if (startZone == StartPos.DEPOT) {
            nav.pointTurnIMU(87f);
        } else if (startZone == StartPos.DOUBLESAMPLING) {
            nav.pointTurnIMU(-36f);
            nav.goDistanceHold(-7f);
            switch (nav.getCubePos()) {
                case MIDDLE:
                    nav.pointTurnIMU(-135f);
                    break;
                case RIGHT:
                    nav.pointTurnIMU(-90f);
                    break;
                default: //Left
                    nav.pointTurnIMU(-175f);
            }
            nav.goDistanceHold(30f);
            nav.goDistanceHold(-30f);
            nav.pointTurnIMU(-36f);
            nav.pointTurnIMU(275f);
        }
        nav.setTeamMarker(1f);
        nav.hold(1f);
        switch (startZone) {
            case DOUBLESAMPLING:
                nav.goDistance(67f, 0.7f, 0.7f);
                break;
            case CRATER: //depot and crater
                nav.goDistance(59.5f, 0.7f, 0.7f);
                nav.holdForDrive();
                break;
            case DEPOT: //depot and crater
                nav.goDistance(59f, 0.7f, 0.7f);
                nav.holdForDrive();
                break;
        }

        nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
        nav.holdForDrive();
        nav.hold(1f);

    }
}
