package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
//TODO JOJO PLEASE PLEASE TEST DURING CLASS SHOULD MAYBE PROBABLY WORK

@TeleOp(name="Mongoose TeleOp", group="Competition")
public class TeleOpMongoose extends OpMode {

    //Drivetrain Motors//
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    //Other Motors//
    private DcMotor extendy;
    private DcMotor lifty;
   // private DcMotor liftyJr;

    //Servos//
    private Servo teamMarker;
    private CRServo collecty;
    private Servo droppy;
    private Servo droppyJr;
    private TouchSensor limitSwitch;

    //Variables//
    private double normalSpeed, slowSpeed;
    private double liftySpeed, liftyJrSpeed;
    private double calibToggle, driveToggle, liftToggle;
    private int driveSpeed, driveMode, liftMode;
    private boolean canMoveLiftyJr;
    private float trim = 0;
    private boolean dPadDown = false;


    //Objects//
    private ElapsedTime runtime = new ElapsedTime();
    private TeleOpNav nav;


    public void init() {
        nav = new TeleOpNav(this);
        //Drivetrain Motors//
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);

        //Other Motors//
        extendy = hardwareMap.dcMotor.get("extendy");
        lifty = hardwareMap.dcMotor.get("lifty");
        //liftyJr = hardwareMap.dcMotor.get("liftyJr");
        lifty.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //liftyJr.setDirection(DcMotor.Direction.REVERSE);
       // lifty.setDirection(DcMotor.Direction.REVERSE);

        //Servos//
        teamMarker = hardwareMap.servo.get("teamMarker");
        teamMarker.setPosition(0.2);
        collecty = hardwareMap.crservo.get("collecty");
        droppy = hardwareMap.servo.get("droppy");
        droppyJr = hardwareMap.servo.get("droppyJr");

        droppyJr.setDirection(Servo.Direction.REVERSE);

        //Sensors//
        limitSwitch = hardwareMap.touchSensor.get("limitSwitch");

        //Variables//
        calibToggle = 0;
        driveToggle = 0;
        liftToggle = 0;
        driveSpeed = 0;
        driveMode = 0;
        liftMode = 0;
        canMoveLiftyJr = true;

        //Speed Offsets//
        normalSpeed = .7;
        slowSpeed = normalSpeed / 2;
        liftySpeed = 1;
        liftyJrSpeed = 1;
    }


    public void loop() {
        //////////////////////////////////////// GAMEPAD 1 /////////////////////////////////////////
        // TOGGLE BUTTONS //
        if (gamepad1.left_bumper && (runtime.seconds() > calibToggle)) {
            calibToggle = runtime.seconds() + 0.5;
            ++driveSpeed;
        }
        if (gamepad1.y && (runtime.seconds() > driveToggle)) {
            driveToggle = runtime.seconds() + 0.5;
            if (driveMode == 0) {
                driveMode = 1;
            } else if (driveMode == 1) {
                driveMode = 2;
            } else if (driveMode == 2) {
                driveMode = 0;
            }
        }

        // DIFFERENT DRIVE MODES //
        if (driveMode == 0) {
            // ARCADE DRIVE //
            double leftStick = gamepad1.left_stick_y;
            double rightStick = -gamepad1.right_stick_x;
            double leftPower, rightPower;

            //Left Side
            if (Math.abs(rightStick) > 0.5) {
                leftPower = leftStick / 2 + rightStick / 2;
            } else {
                leftPower = leftStick + rightStick / 2;
            }

            //Right Side
            if (Math.abs(rightStick) > 0.5) {
                rightPower = leftStick / 2 - rightStick / 2;
            } else {
                rightPower = leftStick - rightStick / 2;
            }

            if (driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Speed: ", "NORMAL");

                backLeft.setPower(leftPower * normalSpeed);
                backRight.setPower(rightPower * normalSpeed);
                frontLeft.setPower(leftPower * normalSpeed);
                frontRight.setPower(rightPower * normalSpeed);
            } else {
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Speed: ", "SLOW");

                backLeft.setPower(leftPower * slowSpeed);
                backRight.setPower(rightPower * slowSpeed);
                frontLeft.setPower(leftPower * slowSpeed);
                frontRight.setPower(rightPower * slowSpeed);
            }
        } else if (driveMode == 1) {
            /// TANK DRIVE ///
            double leftStick = gamepad1.left_stick_y;
            double rightStick = -gamepad1.right_stick_x;

            //Left Side
            double leftPower = gamepad1.left_stick_y;

            //Right Side
            double rightPower = gamepad1.right_stick_y;

            if (driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Speed: ", "NORMAL");

                backLeft.setPower(leftPower * normalSpeed);
                backRight.setPower(rightPower * normalSpeed);
                frontLeft.setPower(leftPower * normalSpeed);
                frontRight.setPower(rightPower * normalSpeed);
            } else {
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Speed: ", "SLOW");

                backLeft.setPower(leftPower * slowSpeed);
                backRight.setPower(rightPower * slowSpeed);
                frontLeft.setPower(leftPower * slowSpeed);
                frontRight.setPower(rightPower * slowSpeed);
            }
        } else if (driveMode == 2) {
            /// ACKERMAN DRIVE ///
            double leftStick = (-gamepad1.left_stick_x);
            double gasPedal = (gamepad1.left_trigger - gamepad1.right_trigger);
            double leftPower, rightPower;

            //Left Side
            if (Math.abs(leftStick) > 0.5) {
                leftPower = gasPedal / 2 + leftStick / 2;
            } else {
                leftPower = gasPedal + leftStick / 2;
            }

            //Right Side
            if (Math.abs(leftStick) > 0.5) {
                rightPower = gasPedal / 2 - leftStick / 2;
            } else {
                rightPower = gasPedal - leftStick / 2;
            }

            if (driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Speed: ", "NORMAL");

                backLeft.setPower(leftPower * normalSpeed);
                backRight.setPower(rightPower * normalSpeed);
                frontLeft.setPower(leftPower * normalSpeed);
                frontRight.setPower(rightPower * normalSpeed);
            } else {
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Speed: ", "SLOW");

                backLeft.setPower(leftPower * slowSpeed);
                backRight.setPower(rightPower * slowSpeed);
                frontLeft.setPower(leftPower * slowSpeed);
                frontRight.setPower(rightPower * slowSpeed);
            }
        }



        //////////////////////////////////////// GAMEPAD 2 /////////////////////////////////////////
        //Lift// - LeftStick= Hopper Lift Power | RightStick= Robot Lift Power
        lifty.setPower(-gamepad2.right_stick_y); //Phone mount side

        //telemetry.addData("Lifts: " + round(liftyJr.getCurrentPosition()) + "/", round(lifty.getCurrentPosition()));
        telemetry.addData("Limit: ", limitSwitch.isPressed());
//
//            if (canMoveLiftyJr) { //Camera mount side
//                if (gamepad2.left_stick_y > 0 && limitSwitch.isPressed()) {
//                    liftyJr.setPower(0);
//                } else if (gamepad2.left_stick_y < 0 && liftyJr.getCurrentPosition() < 2000){
//                    liftyJr.setPower(0);
//                } else {
//                    liftyJr.setPower(gamepad2.left_stick_y * liftyJrSpeed);
//                }
//            }
//         else {
//            if (canMoveLiftyJr) { //Camera mount side
//                if (gamepad2.left_stick_y > 0 && limitSwitch.isPressed()) {
//                    liftyJr.setPower(0);
//                } else {
//                    liftyJr.setPower(gamepad2.left_stick_y * liftyJrSpeed);
//                }
//            }
//        }
        if (limitSwitch.isPressed()/* && (runtime.seconds() > liftToggle)*/) {
            nav.resetEncoders();
        }

        if(gamepad1.dpad_down){
            while(canMoveLiftyJr){
                nav.goDown();
            }
        }
        if(gamepad1.dpad_left){
            while(canMoveLiftyJr){
                nav.goUpBit();
            }
        }
        if(gamepad1.dpad_up){
            while(canMoveLiftyJr){
               nav.goUpAll();
            }
        }

        //Team Marker Deployer// - DPadRight= Lift | DPadLeft= Lower
        if (gamepad1.dpad_right) {
            teamMarker.setPosition(0.7);
        } else if (gamepad1.dpad_left) {
            teamMarker.setPosition(0.2);
        }


        //Collector// - RightBumper= Intake | RightTrigger= Outtake //This is a VEX Motor, 0.5 is the maximum power
        if (gamepad2.right_bumper) { //
            collecty.setPower(-0.5);
        } else if (gamepad2.right_trigger > 0.5) {
            collecty.setPower(0.5);
        } else {
            collecty.setPower(0);
        }
        telemetry.addData("Collector: ", collecty.getPower());

        //Collection Extension motor// - LeftBumper= Deploy | LeftTrigger= Retract
        if (gamepad2.left_bumper) {
            extendy.setPower(-1);
        } else if (gamepad2.left_trigger > 0.5) {
            extendy.setPower(1);
        } else {
            extendy.setPower(0);
        }
        telemetry.addData("Extension: ", extendy.getCurrentPosition());

        //Collector Dropper// - Y= Top | B= Middle | A= Bottom
        if(!dPadDown) {
            if(gamepad2.dpad_up) {
                trim -= 0.05;
                dPadDown = true;
            }
            else if(gamepad2.dpad_down) {
                trim += 0.05;
                dPadDown = true;
            }
        }
        else if(!gamepad2.dpad_up && !gamepad2.dpad_down) {
            dPadDown = false;
        }

        if (gamepad2.y) { //top
            droppy.setPosition(0.2 + trim);
            droppyJr.setPosition(0.2 + trim);
            canMoveLiftyJr = false;
        } else if (gamepad2.b) { //middle
            droppy.setPosition(0.5 + trim);
            droppyJr.setPosition(0.5 + trim);
            canMoveLiftyJr = true;
        } else if (gamepad2.a) { //bottom
            droppy.setPosition(0.675 + trim);
            droppyJr.setPosition(0.675 + trim);
            canMoveLiftyJr = true;
        }

     //   telemetry.update();
    }


    private static double round(double value) { //Allows telemetry to display nicely
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}