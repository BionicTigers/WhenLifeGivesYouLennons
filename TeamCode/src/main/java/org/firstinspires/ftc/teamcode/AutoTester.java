package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A class to run Autonomous given a strategy.
 */
public class AutoTester {

    public enum StartPos {DEPOTAUTO, CRATERAUTO, DOUBLESAMPLINGAUTO}

    ;
    private StartPos startZone;
    private OpMode opMode;
    private Telemetry telemetry;
    private Navigation nav;

    /**
     * The constructor method that contains everything to run in initialization.
     *
     * @param startZone - StartPos enumerator. Tells which strategy to run. Options are DEPOT, CRATER, or DOUBLESAMPLING.
     * @param opMode    - The OpMode required to access motors. Often, 'this' will suffice.
     * @param telemetry - Telemetry of the current OpMode, used to output data to the screen.
     */
    public AutoTester(AutoTester.StartPos startZone, com.qualcomm.robotcore.eventloop.opmode.LinearOpMode opMode, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        this.startZone = startZone;
        this.opMode = opMode;
        this.telemetry = telemetry;
        nav = new Navigation(opMode, telemetry,true);
        nav.hold(0.1f);

    }

    // Run this to run Autonomous. //
    public void runOpMode() {
        nav.updateCubePos();
       // nav.setCollectorHeight(Navigation.CollectorHeight.DUMP);
       // nav.setLiftHeight(Navigation.LiftHeight.HOOK);
       // nav.holdForLift();
        nav.turnToHeading(45f);
        //nav.goDistance(3f);
       // nav.holdForLift();
        //nav.pointTurnRelative(-90f);
        nav.holdForDrive();
       // nav.setLiftHeight(Navigation.LiftHeight.LOWER); //Lowering lift back to starting position
      //  nav.goDistance(15f);
      //  nav.holdForDrive();
//        switch (nav.getCubePos()) { //all of them for sampling
//            case MIDDLE:
//                nav.goDistance(12f); //less forward (same total distance as before)
//                nav.holdForDrive();
//                nav.goDistance(-12f);  //same distance back
//                nav.holdForDrive();
//                nav.pointTurnRelative(80f); //90 degrees to left
//                break;
//            case RIGHT:
//                nav.pointTurnRelative(-60f); //turning 5 degrees more
//                nav.holdForDrive();
//                nav.goDistance(20f); //going same distance forward and back
//                nav.holdForDrive();
//                nav.goDistance(-20f);
//                nav.holdForDrive();
//                nav.pointTurnRelative(140f); //turning total 90
//                break;
//            default:
//                nav.pointTurnRelative(55f);
//                nav.holdForDrive();
//                nav.goDistance(15f);
//                nav.holdForDrive();
//                nav.goDistance(-15f);
//                nav.holdForDrive();
//                nav.pointTurnRelative(25f); //turning total of 90
//
//                break; }
//
//        //-----crater depot run-----//
        if (startZone == StartPos.CRATERAUTO) {
            nav.holdForDrive();
//            nav.goDistance(44f);
//            nav.holdForDrive();
//            nav.pointTurnRelative(-125f);
            nav.holdForDrive();
//            nav.goDistance(-38f);
}
//        // depot side //
//        else if (startZone == StartPos.DEPOTAUTO) {
//            nav.holdForDrive();
//            nav.goDistance(45f);
//            nav.holdForDrive();
//            nav.pointTurnRelative(43f); //want a little bit more for gliding on the wall
//            nav.holdForDrive();
//            nav.goDistance(-35f); }
//
//        //-----crater doublesampling------//
//        else if (startZone == StartPos.DOUBLESAMPLINGAUTO) {
//            nav.holdForDrive();
//            nav.goDistance(44f);
//            nav.holdForDrive();
//            nav.pointTurnRelative(-128f);
//            nav.holdForDrive();
//            nav.curveTurn(-40f,10f,0f,15f);
//            switch (nav.getCubePos()) {
//                case MIDDLE:
//                    nav.pointTurnRelative(-90f);
//                    nav.holdForDrive();
//                    nav.goDistance(30f);
//                    nav.holdForDrive();
//                    nav.goDistance(-30f);
//                    nav.holdForDrive();
//                    nav.pointTurnRelative(90f);
//                    break;
//                case RIGHT:
//                    nav.pointTurnRelative(-45f);
//                    nav.holdForDrive();
//                    nav.goDistance(30f);
//                    nav.holdForDrive();
//                    nav.goDistance(-30f);
//                    nav.holdForDrive();
//                    nav.pointTurnRelative(45f);
//                    break;
//                default: //Left
//                    nav.pointTurnRelative(-135f);
//                    nav.holdForDrive();
//                    nav.goDistance(30f);
//                    nav.holdForDrive();
//                    nav.goDistance(-30f);
//                    nav.holdForDrive();
//                    nav.pointTurnRelative(135f);
//                    nav.holdForDrive(); }
//
//                nav.curveTurn(10f,-11f,0f,0f); }
//
//        //-----marker deploy and driving to crater-----//
//        nav.holdForDrive();
//        nav.setTeamMarker(0.8f);
//        nav.hold(1);
//        nav.goDistance(60f);
//        nav.holdForDrive();
//        nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
//        nav.setCollectorExtension(Navigation.CollectorExtension.OUT);
//        nav.hold(2);
    }
}