package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;


@TeleOp(name="Outreach TeleOp", group="Outreach")


public class TeleOpOutreach extends OpMode {

    //DRIVETRAIN\\
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor backRight;

    //VARIABLES//
    private double leftPower, rightPower;
    private double leftStick, rightStick, gasPedal;
    private double driveToggle, calibToggle, controlsToggle;
    private int calibMode, driveMode, controlsMode;
    private double buttonThreshold;

    //OBJECTS//
    private BNO055IMU imu;
    private ElapsedTime runtime = new ElapsedTime();


    public void init() {

        //DRIVETRAIN//
        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        //IMU//
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //VARIABLES//
        calibToggle = 0; driveToggle = 0; controlsToggle = 0;
        calibMode = 0; driveMode = 0; controlsMode = 0;
        buttonThreshold = 0.25;
    }


    public void loop() {

        if (gamepad2.a && (runtime.seconds() > controlsToggle)) {
            controlsToggle = runtime.seconds() + buttonThreshold;
            controlsMode++;
        }

        if (controlsMode % 2 == 0) {

            // DRIVE MODE TOGGLING
            if (gamepad1.y && (runtime.seconds() > driveToggle)) {
                driveToggle = runtime.seconds() + buttonThreshold;
                if (driveMode == 0) {
                    driveMode = 1;
                } else if (driveMode == 1) {
                    driveMode = 2;
                } else if (driveMode == 2) {
                    driveMode = 3;
                } else if (driveMode == 3) {
                    driveMode = 0;
                }
            }

            if (gamepad1.b && (runtime.seconds() > calibToggle)) {
                calibToggle = runtime.seconds() + buttonThreshold;
                calibMode++;
            }

            if (gamepad1.x) {
                BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
                parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
                parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
                parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
                parameters.loggingEnabled = true;
                parameters.loggingTag = "IMU";
                parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

                imu.initialize(parameters);

                BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
                String filename = "BNO055IMUCalibration.json";
                File file = AppUtil.getInstance().getSettingsFile(filename);
                ReadWriteFile.writeFile(file, calibrationData.serialize());
                telemetry.log().add("saved to '%s'", filename);
            }

            // DIFFERENT DRIVE MODES //
            if (driveMode == 0) {

                // MERCURY DRIVE //
                double m1, m2, m3, m4;

                if (calibMode % 2 == 1) {
                    Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
                    double P = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
                    double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x);
                    double rightX = -gamepad1.right_stick_x;

                    m1 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                    m2 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) + rightX;
                    m3 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                    m4 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) + rightX;

                    telemetry.addData("Mode: ", "ORIENTED");
                    telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                    telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));
                } else {
                    double P = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
                    double robotAngle = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
                    double rightX = -gamepad1.right_stick_x;
                    double sinRAngle = Math.sin(robotAngle);
                    double cosRAngle = Math.cos(robotAngle);

                    m1 = (P * sinRAngle) + (P * cosRAngle) + rightX;
                    m2 = (P * sinRAngle) - (P * cosRAngle) - rightX;
                    m3 = (P * sinRAngle) - (P * cosRAngle) + rightX;
                    m4 = (P * sinRAngle) + (P * cosRAngle) - rightX;

                    telemetry.addData("Mode: ", "MECANUM");
                    telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                    telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));
                }

                backLeft.setPower(m1);
                backRight.setPower(m2);
                frontLeft.setPower(m3);
                frontRight.setPower(m4);
            } else if (driveMode == 1) {

                // ARCADE DRIVE //
                leftStick = gamepad1.left_stick_y;
                rightStick = gamepad1.right_stick_x;

                //Left Side
                if (Math.abs(rightStick) > 0.5) {
                    leftPower = leftStick / 2 - rightStick / 2;
                } else {
                    leftPower = leftStick - rightStick / 2;
                }

                //Right Side
                if (Math.abs(rightStick) > 0.5) {
                    rightPower = leftStick / 2 + rightStick / 2;
                } else {
                    rightPower = leftStick + rightStick / 2;
                }

                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));

                backLeft.setPower(leftPower);
                backRight.setPower(rightPower);
                frontLeft.setPower(leftPower);
                frontRight.setPower(rightPower);
            } else if (driveMode == 2) {

                /// TANK DRIVE ///
                leftPower = gamepad1.left_stick_y;
                rightPower = gamepad1.right_stick_y;

                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));

                backLeft.setPower(leftPower);
                backRight.setPower(rightPower);
                frontLeft.setPower(leftPower);
                frontRight.setPower(rightPower);
            } else if (driveMode == 3) {

                /// ACKERMAN DRIVE ///
                leftStick = (-gamepad1.left_stick_x);

                gasPedal = (gamepad1.left_trigger - gamepad1.right_trigger);

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

                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Stick: ", "X = " + round(leftStick) + ", G: " + round(gasPedal));
                telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));

                backLeft.setPower(leftPower);
                backRight.setPower(rightPower);
                frontLeft.setPower(leftPower);
                frontRight.setPower(rightPower);
            }
        } else {

            // DRIVE MODE TOGGLING
            if (gamepad2.y && (runtime.seconds() > driveToggle)) {
                driveToggle = runtime.seconds() + buttonThreshold;
                if (driveMode == 0) {
                    driveMode = 1;
                } else if (driveMode == 1) {
                    driveMode = 2;
                } else if (driveMode == 2) {
                    driveMode = 3;
                } else if (driveMode == 3) {
                    driveMode = 0;
                }
            }

            if (gamepad2.b && (runtime.seconds() > calibToggle)) {
                calibToggle = runtime.seconds() + buttonThreshold;
                calibMode++;
            }

            if (gamepad2.x) {
                BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
                parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
                parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
                parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
                parameters.loggingEnabled = true;
                parameters.loggingTag = "IMU";
                parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

                imu.initialize(parameters);

                BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
                String filename = "BNO055IMUCalibration.json";
                File file = AppUtil.getInstance().getSettingsFile(filename);
                ReadWriteFile.writeFile(file, calibrationData.serialize());
                telemetry.log().add("saved to '%s'", filename);
            }

            // DIFFERENT DRIVE MODES //
            if (driveMode == 0) {

                // MERCURY DRIVE //
                double m1, m2, m3, m4;

                if (calibMode % 2 == 1) {
                    Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
                    double P = Math.hypot(-gamepad2.left_stick_x, -gamepad2.left_stick_y);
                    double robotAngle = Math.atan2(-gamepad2.left_stick_y, gamepad2.left_stick_x);
                    double rightX = -gamepad2.right_stick_x;

                    m1 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                    m2 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) + rightX;
                    m3 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                    m4 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) + rightX;

                    telemetry.addData("THIS IS A ", "HOSTILE TAKEOVER");
                    telemetry.addData("", "");
                    telemetry.addData("Mode: ", "ORIENTED");
                    telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                    telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));
                } else {
                    double P = Math.hypot(-gamepad2.left_stick_x, -gamepad2.left_stick_y);
                    double robotAngle = Math.atan2(-gamepad2.left_stick_y, -gamepad2.left_stick_x);
                    double rightX = -gamepad2.right_stick_x;
                    double sinRAngle = Math.sin(robotAngle);
                    double cosRAngle = Math.cos(robotAngle);

                    m1 = (P * sinRAngle) + (P * cosRAngle) + rightX;
                    m2 = (P * sinRAngle) - (P * cosRAngle) - rightX;
                    m3 = (P * sinRAngle) - (P * cosRAngle) + rightX;
                    m4 = (P * sinRAngle) + (P * cosRAngle) - rightX;

                    telemetry.addData("THIS IS A ", "HOSTILE TAKEOVER");
                    telemetry.addData("", "");
                    telemetry.addData("Mode: ", "MECANUM");
                    telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                    telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));
                }

                backLeft.setPower(m1);
                backRight.setPower(m2);
                frontLeft.setPower(m3);
                frontRight.setPower(m4);
            } else if (driveMode == 1) {

                // ARCADE DRIVE //
                leftStick = gamepad2.left_stick_y;
                rightStick = gamepad2.right_stick_x;

                //Left Side
                if (Math.abs(rightStick) > 0.5) {
                    leftPower = leftStick / 2 - rightStick / 2;
                } else {
                    leftPower = leftStick - rightStick / 2;
                }

                //Right Side
                if (Math.abs(rightStick) > 0.5) {
                    rightPower = leftStick / 2 + rightStick / 2;
                } else {
                    rightPower = leftStick + rightStick / 2;
                }

                telemetry.addData("THIS IS A ", "HOSTILE TAKEOVER");
                telemetry.addData("", "");
                telemetry.addData("Mode: ", "ARCADE");
                telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));

                backLeft.setPower(leftPower);
                backRight.setPower(rightPower);
                frontLeft.setPower(leftPower);
                frontRight.setPower(rightPower);
            } else if (driveMode == 2) {

                /// TANK DRIVE ///
                leftPower = gamepad2.left_stick_y;
                rightPower = gamepad2.right_stick_y;

                telemetry.addData("THIS IS A ", "HOSTILE TAKEOVER");
                telemetry.addData("", "");
                telemetry.addData("Mode: ", "TANK");
                telemetry.addData("Stick: ", "X = " + round(rightStick) + ", Y = " + round(leftStick));
                telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));

                backLeft.setPower(leftPower);
                backRight.setPower(rightPower);
                frontLeft.setPower(leftPower);
                frontRight.setPower(rightPower);
            } else if (driveMode == 3) {

                /// ACKERMAN DRIVE ///
                leftStick = (-gamepad2.left_stick_x);

                gasPedal = (gamepad2.left_trigger - gamepad2.right_trigger);

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

                telemetry.addData("THIS IS A ", "HOSTILE TAKEOVER");
                telemetry.addData("", "");
                telemetry.addData("Mode: ", "ACKERMAN");
                telemetry.addData("Stick: ", "X = " + round(leftStick) + ", G: " + round(gasPedal));
                telemetry.addData("Power: ", "L = " + round(leftPower) + ", R = " + round(rightPower));

                backLeft.setPower(leftPower);
                backRight.setPower(rightPower);
                frontLeft.setPower(leftPower);
                frontRight.setPower(rightPower);
            }
        }
    }

    private static double round(double value) { //Allows telemetry to display nicely
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}