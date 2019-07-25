package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;

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
public class AutoGeneric {
    public enum StartPos {DEPOT, CRATER, DOUBLESAMPLING}
    private StartPos startZone;
    private OpMode opMode;
    private Telemetry telemetry;
    private Navigation nav;
    private float waiting;

    /**
     * The constructor method that zcontains everything to run in initialization.
     *
     * @param startZone - StartPos enumerator. Tells which strategy to run. Options are DEPOT, CRATER, or DOUBLESAMPLING.
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

      //  double voltreading = (float) potentiometer.getVoltage();
            //convert voltage to distance (cm)
       // double percentTurned = voltreading/5 * 100;

//        if (percentTurned > 0 &&  percentTurned <= 10){
//            waiting = 2f;
//        }else if(percentTurned > 10 &&  percentTurned <= 20){
//            waiting = 5f;
//        }else if(percentTurned > 20 &&  percentTurned <= 30){
//            waiting = 7f;
//        }else if(percentTurned > 30 &&  percentTurned <= 40){
//            waiting = 10f;
//        }else{
//            waiting = 0f;
//        }
//            telemetry.addData("raw val", "voltage:  " + Double.toString(voltreading));
//            // this is our calculated value
//            telemetry.addData("PercentRot", "percent: " + Double.toString(percentTurned));
//            telemetry.update();

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
        nav.hold(waiting);
        nav.setLiftHeight(Navigation.LiftHeight.LOWER);
        nav.goDistanceHold(12.5f);
        switch (nav.getCubePos()) {
            case MIDDLE:
                nav.goDistanceHold(15f);
                nav.goDistanceHold(-14.5f);
                break;
            case RIGHT:
                nav.pointTurnIMU(-93.5f);
                nav.goDistanceHold(21.5f);
                nav.goDistanceHold(-21.5f);
                break;
            default: //left
                nav.pointTurnIMU(3.5f);
                nav.goDistanceHold(20f);
                nav.goDistanceHold(-20f);
                break;
        }
        //-----crater depot run-----//
        if (startZone == StartPos.CRATER) {
            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(44f);
            nav.pointTurnIMU(-92f);
            nav.hold(4.5f);
            nav.goDistanceHold(-39f);
            nav.pointTurnIMU(-90f);
            // depot side //
        } else if (startZone == StartPos.DEPOT) {
            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(44.75f);
            nav.hold(2f);
            nav.pointTurnIMU(90f);
            nav.goDistanceHold(-49f);
            nav.pointTurnIMU(87f);
            //-----crater doublesampling------//
        } else if (startZone == StartPos.DOUBLESAMPLING) {
            nav.pointTurnIMU(36f); //turn to face wall
            nav.goDistanceHold(47.75f);
            nav.pointTurnIMU(-91f);

            nav.goDistanceHold(-40f);
            nav.pointTurnIMU(-36f);
            nav.goDistanceHold(-7f);
            //nav.curveTurn(-39f, 10f, 0f, -3.5f);
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
            //nav.goDistanceHold(9.5f);
            nav.pointTurnIMU(275f);
        }
        //-----marker deploy and driving to crater-----//
        nav.setTeamMarker(1f);
        nav.hold(1f);
        switch (startZone) {
            case DOUBLESAMPLING:
                nav.goDistance(67f, 0.65f, 0.65f);
                break;
            case CRATER: //depot and crater
                nav.goDistance(55f, 0.6f, 0.6f);
                nav.holdForDrive();
                break;
            case DEPOT: //depot and crater
                nav.goDistance(59f, 0.6f, 0.6f);
                nav.holdForDrive();
                break;
        }

        nav.setCollectorHeight(Navigation.CollectorHeight.COLLECT);
        nav.holdForDrive();
        nav.hold(1f);

    }
}