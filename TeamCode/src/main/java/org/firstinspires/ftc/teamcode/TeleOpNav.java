package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This is the base class from teleOpMoongoose
 */
public class TeleOpNav {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    //Other Motors//
    public DcMotor extendy;            // lead screw horizontal extension
    public DcMotor lifty;              // lift for hanging
    public DcMotor liftyJr;            // lift for hopper and deposit into
    public DcMotor collecty;           // collection intake

    //Servos//
    public Servo teamMarker;           // team Marker servo (only used in teleOp in case of emergency
    public Servo droppy;               // intake position servo (right)
    public Servo droppyJr;             // intake position servo (left)
    public TouchSensor limitSwitch;    // touch sensor for liftyJr -- hopper lift, placed on bottom of lift

    //Variables//
    public double normalSpeed, slowSpeed;
    public double calibToggle, driveToggle, liftToggle, manualToggle;
    public int driveSpeed, driveMode, liftMode;
    public double liftySpeed, liftyJrSpeed;
    public boolean canMoveLiftyJr, liftylike;
    public int limitable;
    public double trim = 0;
    public boolean dPadDown = false;
    public int manualMode;
    private ElapsedTime runtime = new ElapsedTime();
    public int goinDown;

    //Objects//
    public ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap hwMap) {
        frontLeft = hwMap.dcMotor.get("frontLeft");
        frontRight = hwMap.dcMotor.get("frontRight");
        backLeft = hwMap.dcMotor.get("backLeft");
        backRight = hwMap.dcMotor.get("backRight");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Other Motors//
        extendy = hwMap.dcMotor.get("extendy");
        lifty = hwMap.dcMotor.get("lifty");
        liftyJr = hwMap.dcMotor.get("liftyJr");

        lifty.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifty.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftyJr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //Servos//
        teamMarker = hwMap.servo.get("teamMarker");
        teamMarker.setPosition(0.2);
        collecty = hwMap.dcMotor.get("collecty");
        droppy = hwMap.servo.get("droppy");
        droppyJr = hwMap.servo.get("droppyJr");
        droppyJr.setDirection(Servo.Direction.REVERSE);

        //Sensors//
        limitSwitch = hwMap.touchSensor.get("limitSwitch");

        calibToggle = 0;
        driveToggle = 0;
        liftToggle = 0;
        driveSpeed = 0;
        driveMode = 0;
        liftMode = 0;
        limitable = 0;
        canMoveLiftyJr = true;
        liftylike = false;

        //Speed Offsets//
        normalSpeed = .80;
        slowSpeed = normalSpeed / 2;
        liftySpeed = 1;
        liftyJrSpeed = 1;
    }

    public void setCanMove(Boolean canMove) {
        canMoveLiftyJr = canMove;
    }

    public void resetEncoders() {
        liftyJr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /////// AUTOMATION FOR TELEOP ////////

    public void goUpBit() {                 // This method goes up using an encoder to where the hopper lift is under the bar only slighty in preparation for dropping
        liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftyJr.setPower(1);
        liftyJr.setTargetPosition(-1500);
    }

    public void goUpAll() {                 // This method goes up using an encoder for the hopper lift to fully dump into the lander
        liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftyJr.setTargetPosition(-2050);
        liftyJr.setPower(1);
    }

    public void goDown() {                  // This method brings the hopper lift back to the lowest position possible (usually on the limit switch)
        liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        goinDown = 1;
        liftyJr.setTargetPosition(0);
        liftyJr.setPower(1);
    }

    public final void ITS_ENDGAME_NOW() { // This method raises the hanging lift to in between the handle
        lifty.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lifty.setPower(1);
        lifty.setTargetPosition(10464);
        liftylike = true;
    }

    public double round(double value) { //Allows telemetry to display nicely
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
