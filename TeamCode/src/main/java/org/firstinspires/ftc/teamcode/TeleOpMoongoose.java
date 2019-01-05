package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.BigDecimal;
import java.math.RoundingMode;

@TeleOp(name = "Moongoose TeleOp", group = "Competition")
public class TeleOpMoongoose extends OpMode {


    //Objects//
    private ElapsedTime runtime = new ElapsedTime();
    private TeleOpNav nav;

    public void init() {
        nav.init(hardwareMap);
        nav = new TeleOpNav();
    }


    public void loop() {
        //////////////////////////////////////// GAMEPAD 1 /////////////////////////////////////////
        // TOGGLE BUTTONS //
        if (gamepad1.left_bumper && (runtime.seconds() > nav.calibToggle)) {
            nav.calibToggle = runtime.seconds() + 0.5;
            ++nav.driveSpeed;
        }
        if (gamepad1.y && (runtime.seconds() > nav.driveToggle)) {
            nav.driveToggle = runtime.seconds() + 0.5;
            if (nav.driveMode == 0) {
                nav.driveMode = 1;
            } else if (nav.driveMode == 1) {
                nav.driveMode = 2;
            } else if (nav.driveMode == 2) {
                nav.driveMode = 0;
            }
        }

        // DIFFERENT DRIVE MODES //
        if (nav.driveMode == 0) {
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

            if (nav.driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Speed: ", "NORMAL");

                nav.backLeft.setPower(leftPower * nav.normalSpeed);
                nav.backRight.setPower(rightPower * nav.normalSpeed);
                nav.frontLeft.setPower(leftPower * nav.normalSpeed);
                nav.frontRight.setPower(rightPower * nav.normalSpeed);
            } else {
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Speed: ", "SLOW");

                nav.backLeft.setPower(leftPower * nav.slowSpeed);
                nav.backRight.setPower(rightPower * nav.slowSpeed);
                nav.frontLeft.setPower(leftPower * nav.slowSpeed);
                nav.frontRight.setPower(rightPower * nav.slowSpeed);
            }
        } else if (nav.driveMode == 1) {
            /// TANK DRIVE ///
            double leftStick = gamepad1.left_stick_y;
            double rightStick = -gamepad1.right_stick_x;

            //Left Side
            double leftPower = gamepad1.left_stick_y;

            //Right Side
            double rightPower = gamepad1.right_stick_y;

            if (nav.driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Speed: ", "NORMAL");

                nav.backLeft.setPower(leftPower * nav.normalSpeed);
                nav.backRight.setPower(rightPower * nav.normalSpeed);
                nav.frontLeft.setPower(leftPower * nav.normalSpeed);
                nav.frontRight.setPower(rightPower * nav.normalSpeed);
            } else {
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Speed: ", "SLOW");

                nav.backLeft.setPower(leftPower * nav.slowSpeed);
                nav.backRight.setPower(rightPower * nav.slowSpeed);
                nav.frontLeft.setPower(leftPower * nav.slowSpeed);
                nav.frontRight.setPower(rightPower * nav.slowSpeed);
            }
        } else if (nav.driveMode == 2) {
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

            if (nav.driveSpeed % 2 == 0) {
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Speed: ", "NORMAL");

                nav.backLeft.setPower(leftPower * nav.normalSpeed);
                nav.backRight.setPower(rightPower * nav.normalSpeed);
                nav.frontLeft.setPower(leftPower * nav.normalSpeed);
                nav.frontRight.setPower(rightPower * nav.normalSpeed);
            } else {
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Speed: ", "SLOW");

                nav.backLeft.setPower(leftPower * nav.slowSpeed);
                nav.backRight.setPower(rightPower * nav.slowSpeed);
                nav.frontLeft.setPower(leftPower * nav.slowSpeed);
                nav.frontRight.setPower(rightPower * nav.slowSpeed);
            }
        }
        //////////////////////////////////////// GAMEPAD 2 /////////////////////////////////////////
        telemetry.addData("Limit: ", nav.limitSwitch.isPressed());

        if (gamepad1.dpad_right && (runtime.seconds() > nav.manualToggle)) {
            nav.manualToggle = runtime.seconds() + 0.5;
            ++nav.manualMode;
            if (nav.manualMode % 2 == 0) {
                nav.lifty.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                nav.liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                nav.lifty.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                nav.liftyJr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
        }

        if (nav.manualMode % 2 == 0) {
            if (gamepad2.dpad_down && nav.canMoveLiftyJr && !nav.limitSwitch.isPressed()) {
                nav.goDown();
            } else if (gamepad2.dpad_left && nav.canMoveLiftyJr) {
                nav.goUpBit();
            } else if (gamepad2.dpad_up && nav.canMoveLiftyJr) {
                nav.goUpAll();
            }
            if (gamepad2.dpad_right) {
                nav.ITS_ENDGAME_NOW();
            }
            if (nav.limitSwitch.isPressed()) {
                nav.resetEncoders();
            }
        } else {
            nav.liftyJr.setPower(gamepad2.left_stick_y);
            nav.lifty.setPower(-gamepad2.right_stick_y);
        }
        

        telemetry.addData("ManualMode: ", nav.manualMode);
        telemetry.addData("LiftyJr: ", nav.liftyJr.getCurrentPosition());


        //Collector// - RightBumper= Intake | RightTrigger= Outtake //This is a VEX Motor, 0.5 is the maximum power
        if (gamepad2.right_bumper) { //
            nav.collecty.setPower(-0.5);
        } else if (gamepad2.right_trigger > 0.5) {
            nav.collecty.setPower(0.5);
        } else {
            nav.collecty.setPower(0);
        }
        telemetry.addData("Collector: ", nav.collecty.getPower());

        //Collection Extension motor// - LeftBumper= Deploy | LeftTrigger= Retract
        if (gamepad2.left_bumper) {
            nav.extendy.setPower(-1);
        } else if (gamepad2.left_trigger > 0.5) {
            nav.extendy.setPower(1);
        } else {
            nav.extendy.setPower(0);
        }
        telemetry.addData("Extension: ", nav.extendy.getCurrentPosition());

        //Collector Dropper// - Y= Top | B= Middle | A= Bottom //
        // COLLECTION SERVOS - Droppy --> right, Droppy Jr --> left //
//        if (gamepad2.y) { //top
//            droppy.setPosition(0.2);
//            droppyJr.setPosition(0.2);
//            nav.setCanMove(false);
//        } else if (gamepad2.b) { //middle
//            droppy.setPosition(0.5);
//            droppyJr.setPosition(0.5);
//            nav.setCanMove(true);
//        } else if (gamepad2.a) { //bottom
//            droppy.setPosition(0.875);
//            droppyJr.setPosition(0.875);
//            nav.setCanMove(true);
//        }

        //Trim stuff for later //
//        if(!dPadDown) {
//            if(gamepad2.dpad_up) {
//                trim -= 0.05;
//                dPadDown = true;
//            }
//            else if(gamepad2.dpad_down) {
//                trim += 0.05;
//                dPadDown = true;
//            }
//        }
//        else if(!gamepad2.dpad_up && !gamepad2.dpad_down) {
//            dPadDown = false;
//        }
    }
}