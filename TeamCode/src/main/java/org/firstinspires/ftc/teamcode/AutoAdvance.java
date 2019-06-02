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
        nav.pointTurnIMU(-47f);
        nav.goDistanceHold(13f);
        nav.setLiftHeight(Navigation.LiftHeight.LOWER);


        if (startZone == StartPos.DEPOT){
            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
            nav.holdForExtendy();

            nav.setCollectorHeight(Navigation.CollectorHeight.HOLD);
            nav.holdForExtendy();
            nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
            nav.hold(0.5f);

            switch (nav.getCubePos()) {
                case RIGHT:
                    nav.setCollectorExtension(Navigation.CollectorExtension.LEFT);
                    nav.pointTurnIMU(-90f);
                    break;
                case LEFT:
                    nav.setCollectorExtension(Navigation.CollectorExtension.LEFT);
                    nav.pointTurnIMU(-7f);
                    break;
                default:
                    nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
                    break;
            }
            nav.holdForExtendy();

            //Collect Sampling//
            nav.setCollectorHeight(Navigation.CollectorHeight.LAND);
            nav.hold(1f);

            //Retract to move to lander//
            nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
            nav.hold(1.5f);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);

            //Move to lander, dump, move away//
            nav.setCollectorHeight(Navigation.CollectorHeight.HOLD);

            nav.pointTurnIMU(-33f);
            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.WAIT);

            nav.goDistance(-14f,0.85f,0.85f);
            nav.holdForDrive();

            nav.pointTurnIMU(-5f);
            nav.goDistance(-2f,0.85f,0.85f);

            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.DROP);
            nav.hold(2f);

            //PARK//
            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.LOWER);
            nav.setCollectorHeight(Navigation.CollectorHeight.MIDDLE);
            nav.pointTurnIMU(7f);
            nav.goDistanceHold(47f);
            nav.pointTurnIMU(85f);
            nav.goDistanceHold(20f);
            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);

            } else if (startZone == StartPos.CRATER){

            nav.setCollectorExtension(Navigation.CollectorExtension.COLLECT);
            nav.holdForExtendy();

            //Collect out of crater//
            nav.setCollectorHeight(Navigation.CollectorHeight.LAND);

            nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
            nav.hold(3f);


            nav.setCollectorHeight(Navigation.CollectorHeight.HOLD);

            nav.setCollectorExtension(Navigation.CollectorExtension.PARK);
            nav.holdForExtendy();
            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
            nav.hold(2f);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.OFF);

            //Move to Lander//

            nav.setCollectorHeight(Navigation.CollectorHeight.HOLD);

            nav.pointTurnIMU(-60f);
            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.WAIT);

            nav.goDistance(-14f,0.85f,0.85f);
            nav.holdForDrive();

            nav.pointTurnIMU(-80f);
            nav.goDistance(-15f,0.85f,0.85f);
            nav.pointTurnIMU(-45f);

            nav.goDistance(-5f,0.85f,0.85f);

            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.DROP);
            nav.hold(2f);

            nav.setLiftyJrHeight(Navigation.LiftyJrHeight.LOWER);

            nav.goDistanceHold(20f);

            switch (nav.getCubePos()) {
                case RIGHT:
                    nav.setCollectorExtension(Navigation.CollectorExtension.LEFT);
                    nav.pointTurnIMU(-90f);
                    break;
                case LEFT:
                    nav.setCollectorExtension(Navigation.CollectorExtension.LEFT);
                    nav.pointTurnIMU(-7f);
                    break;
                default:
                    nav.setCollectorExtension(Navigation.CollectorExtension.DUMP);
                    nav.pointTurnIMU(-54f);
                    break;
            }


            nav.setCollectorHeight(Navigation.CollectorHeight.LAND);
            nav.setCollectionSweeper(Navigation.CollectorSweeper.INTAKE);
            nav.hold(1f);

            nav.pointTurnIMU(45);
            nav.goDistanceHold(25f);
            nav.pointTurnIMU(90f);

            nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);

            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
            nav.holdForExtendy();

            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);



            //PARK//
//            nav.pointTurnIMU(36f); //turn to face wall
//            nav.goDistanceHold(45f);
//            nav.pointTurnIMU(-90f);
//            nav.goDistanceHold(10f);

            //extend to park//

//            nav.pointTurnIMU(-45f);
//            nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
//            nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);

        }

    }
}
