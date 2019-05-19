package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * depot auto:
 * -lands, deposits teammarker, samples, scores sampling, parks
 * crater auto:
 * -lands, collects from crater, scores collected into lander, samples, drops team marker, parks
 */
public class AutoAdvance {
    public enum StartPos {DEPOT, CRATER}

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
    public AutoAdvance(AutoAdvance.StartPos startZone, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
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
        nav.goDistanceHold(10f);
        nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
        nav.holdForExtendy();

        if (startZone == StartPos.DEPOT){
            nav.setCollectionSweeper(Navigation.CollectorSweeper.OUTTAKE);
            nav.hold(1);
            nav.setCollectorExtension(Navigation.CollectorExtension.DUMP);

            switch (nav.getCubePos()) {
                case RIGHT:
                    nav.pointTurnIMU(-93.55f);
                    break;
                case LEFT:
                    nav.pointTurnIMU(0f);
                    break;
            }

            //Collect Sampling//
            nav.setCollectorHeight(Navigation.CollectorHeight.LAND);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.OUTTAKE);
            nav.hold(1f);

            //Retract to move to lander//
            nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
            nav.hold(0.5f);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);

            //Move to lander, dump, move away//

            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
            nav.pointTurnIMU(-33f);
            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.WAIT);

            nav.goDistance(-18.5f,0.75f,0.75f);

            nav.pointTurnIMU(-45f);

            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.DROP);
            nav.holdForLiftJr();

            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.LOWER);
            nav.goDistance(19f,0.75f,0.75f);

            //Move to crater//
            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(44.75f);

            nav.pointTurnIMU(90f);
            nav.goDistanceHold(10f);


            } else if (startZone == StartPos.CRATER){

            //Collect out of crater//
            nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
            nav.hold(3f);

            nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);

            //Move to Lander//

            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
            nav.pointTurnIMU(-95f);
            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.WAIT);

            nav.goDistance(-18.5f,0.75f,0.75f);

            nav.pointTurnIMU(-45f);

            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.DROP);
            nav.holdForLiftJr();

            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.LOWER);


            //Go back to middle
            nav.pointTurnIMU(-95f);

            nav.goDistance(15f,0.75f,0.75f);
            nav.hold(1);


            //sample//
            switch (nav.getCubePos()) {
                case RIGHT:
                    nav.pointTurnIMU(-93.55f);
                    break;
                case MIDDLE: //left
                    nav.pointTurnIMU(-45f);
                    break;
                default:
                    nav.pointTurnIMU(0f);
                    break;
            }

            nav.setCollectorHeight(Navigation.CollectorHeight.LAND);
            nav.hold(1f);
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);

//            nav.setCollectorExtension(Navigation.CollectorExtension.DUMP);
//            nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
//            nav.hold(1f);
//
//            nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
//            nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);

            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(45f);
            nav.pointTurnIMU(-90f);
            nav.goDistanceHold(10f);

        }

    }
}
