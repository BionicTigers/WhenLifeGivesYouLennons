package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This is a class for TeleOp for competitions
 * There are two different drivers/controllers:
 * gamepad1 controls the drivetrain and its different types of driving
 * gamepad2 controls all of the top mechanisms (hopperlift, lift, collection)
 * This class pulls from TeleOpNav for its automation methods
 */
@TeleOp(name = "Nickolas TeleOp", group = "Competition")
public class TeleOpNick extends OpMode {

    //Objects//
    private ElapsedTime runtime = new ElapsedTime();
    TeleOpNav nav = new TeleOpNav(); //base class for teleOp

    public void init() {
        nav.init(hardwareMap);
        nav.driveMode = 0;
    }

    public void loop() {
//////////////////////////////////////// GAMEPAD 1 /////////////////////////////////////////
// TOGGLE BUTTONS //
//        if (gamepad1.left_trigger >0.5 && (runtime.seconds() > nav.calibToggle)) {
//            nav.calibToggle = runtime.seconds() + 0.25;
//            ++nav.driveSpeed;
//        }

//        if (gamepad1.dpad_up && (runtime.seconds() > nav.speedyToggle)) {
//            nav.speedyToggle = runtime.seconds() + 0.25;
//            ++nav.backwoodsSpeed;
//        }

//        if (gamepad1.y && (runtime.seconds() > nav.driveToggle)) {
//            nav.driveToggle = runtime.seconds() + 0.25;
//            if (nav.driveMode == 0) {
//                nav.driveMode = 1;
//            } else if (nav.driveMode == 1) {
//                nav.driveMode = 2;
//            } else if (nav.driveMode == 2) {
//                nav.driveMode = 0;
//            }
//        }

// DIFFERENT DRIVE MODES //
        if (nav.driveMode == 0) {
            // ARCADE DRIVE //
            double leftStick = gamepad2.left_stick_y;
            double rightStick = -gamepad2.right_stick_x;
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

            if (nav.driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Speed: ", "NORMAL");

                nav.backLeft.setPower(leftPower *    nav.normalSpeed);
                nav.backRight.setPower(rightPower *  nav.normalSpeed);
                nav.frontLeft.setPower(leftPower *   nav.normalSpeed);
                nav.frontRight.setPower(rightPower * nav.normalSpeed);
            } else if (nav.backwoodsSpeed % 2 != 0) {
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Speed: ", "SPEEDY");

                nav.backLeft.setPower(leftPower *    nav.speedySpeed);
                nav.backRight.setPower(rightPower *  nav.speedySpeed);
                nav.frontLeft.setPower(leftPower *   nav.speedySpeed);
                nav.frontRight.setPower(rightPower * nav.speedySpeed);
            } else {
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Speed: ", "SLOW");

                nav.backLeft.setPower(leftPower *    nav.slowSpeed);
                nav.backRight.setPower(rightPower *  nav.slowSpeed);
                nav.frontLeft.setPower(leftPower *   nav.slowSpeed);
                nav.frontRight.setPower(rightPower * nav.slowSpeed);
            }
        } else if (nav.driveMode == 1) { /// TANK DRIVE ///

            double leftStick = gamepad2.left_stick_y;
            double rightStick = -gamepad2.right_stick_x;

            //Left Side
            double leftPower = gamepad2.left_stick_y;

            //Right Side
            double rightPower = gamepad2.right_stick_y;

            if (nav.driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Speed: ", "NORMAL");

                nav.backLeft.setPower(leftPower *    nav.normalSpeed);
                nav.backRight.setPower(rightPower *  nav.normalSpeed);
                nav.frontLeft.setPower(leftPower *   nav.normalSpeed);
                nav.frontRight.setPower(rightPower * nav.normalSpeed);
            } else if (nav.backwoodsSpeed % 2 != 0) {
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Speed: ", "SPEEDY");

                nav.backLeft.setPower(leftPower *    nav.speedySpeed);
                nav.backRight.setPower(rightPower *  nav.speedySpeed);
                nav.frontLeft.setPower(leftPower *   nav.speedySpeed);
                nav.frontRight.setPower(rightPower * nav.speedySpeed);
            } else {
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Speed: ", "SLOW");

                nav.backLeft.setPower(leftPower *    nav.slowSpeed);
                nav.backRight.setPower(rightPower *  nav.slowSpeed);
                nav.frontLeft.setPower(leftPower *   nav.slowSpeed);
                nav.frontRight.setPower(rightPower * nav.slowSpeed);
            }
        } else if (nav.driveMode == 2) { /// ACKERMAN DRIVE ///
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

            if (nav.driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Speed: ", "NORMAL");

                nav.backLeft.setPower(leftPower *    nav.normalSpeed);
                nav.backRight.setPower(rightPower *  nav.normalSpeed);
                nav.frontLeft.setPower(leftPower *   nav.normalSpeed);
                nav.frontRight.setPower(rightPower * nav.normalSpeed);
            } else if (nav.backwoodsSpeed % 2 != 0) {
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Speed: ", "SPEEDY");

                nav.backLeft.setPower(leftPower *    nav.speedySpeed);
                nav.backRight.setPower(rightPower *  nav.speedySpeed);
                nav.frontLeft.setPower(leftPower *   nav.speedySpeed);
                nav.frontRight.setPower(rightPower * nav.speedySpeed);
            } else if (nav.driveSpeed % 2 != 0) {
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Speed: ", "SLOW");

                nav.backLeft.setPower(leftPower *    nav.slowSpeed);
                nav.backRight.setPower(rightPower *  nav.slowSpeed);
                nav.frontLeft.setPower(leftPower *   nav.slowSpeed);
                nav.frontRight.setPower(rightPower * nav.slowSpeed);
            }

            if (gamepad1.dpad_up) {
                nav.liftyJr.setPower(1);
            } else if (!nav.limitSwitch.isPressed() && gamepad1.dpad_down) {
                nav.liftyJr.setPower(-1);
            }
        }
//////////////////////////////////////// GAMEPAD 2 /////////////////////////////////////////
        telemetry.addData("HopLimit: ", nav.limitSwitch.isPressed());
        //telemetry.addData("ExtLimit: ", nav.extendySwitch.isPressed());

//HOPPER LIFT// - automatic (dpad up,left, and down), manual - leftstick y
        if (gamepad2.dpad_down || gamepad1.right_trigger >0.5 && nav.canMoveLiftyJr && !nav.limitSwitch.isPressed()) { //
            nav.goDown();
        } else if (gamepad2.dpad_left && nav.canMoveLiftyJr) {
            nav.goUpBit();
        } else if (gamepad2.dpad_up && nav.canMoveLiftyJr) {
            nav.goUpAll();
        } else if (gamepad2.right_stick_button && nav.canMoveLiftyJr) {
            nav.goupBalance();
//        } else if (!nav.liftyJr.isBusy()){
//            if(nav.limitSwitch.isPressed() && Math.abs(gamepad2.left_stick_y) > 0){
//                nav.liftyJr.setPower(0);
//            }else {
//                nav.liftyJr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                nav.liftyJr.setPower(gamepad2.left_stick_y);
//            }
        }
        telemetry.addData("LiftyJr: ", nav.liftyJr.getCurrentPosition());

//HANGING LIFT// - automatic (dpad right), manual rightstick y
        if (gamepad2.dpad_right||gamepad1.dpad_right) {
            nav.ITS_ENDGAME_NOW();
        } else if (!nav.lifty.isBusy()) {
            nav.lifty.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            nav.lifty.setPower(-gamepad2.right_stick_y);
        }

        telemetry.addData("Lifty: ", nav.lifty.getCurrentPosition());

//COLLECTOR// - RightBumper= Intake | RightTrigger= Outtake //This is a VEX Motor, 0.5 is the maximum power
        if (gamepad2.right_bumper) { //
            nav.collecty.setPower(1);
        } else if (gamepad2.right_trigger > 0.5) {
            nav.collecty.setPower(-1);
        } else {
            nav.collecty.setPower(0);
        }
        telemetry.addData("Collector: ", nav.collecty.getPower());

        if (gamepad2.left_bumper && nav.extendy.getCurrentPosition()<2000) {
            nav.extendy.setPower(1);
        } else if (gamepad2.left_trigger > 0.5 && nav.extendy.getCurrentPosition()>0) {
            nav.extendy.setPower(-1);
        } else {
            nav.extendy.setPower(0);
        }
        telemetry.addData("Extension: ", nav.extendy.getCurrentPosition());
//COLLECTION SERVOS - Y= Top | B= Middle | A= Bottom || Droppy --> right, Droppy Jr --> left //
        if (gamepad2.y) { // top
            nav.droppy.setPosition(0.185);
            nav.droppyJr.setPosition(0.185);
            nav.setCanMove(false);
        } else if (gamepad2.b) { // middle
            nav.droppy.setPosition(0.55);
            nav.droppyJr.setPosition(0.55);
            nav.setCanMove(true);
        } else if (gamepad2.a) { // bottom
            nav.droppy.setPosition(0.7575);
            nav.droppyJr.setPosition(0.7575);
            nav.setCanMove(true);
        }
    }
}
