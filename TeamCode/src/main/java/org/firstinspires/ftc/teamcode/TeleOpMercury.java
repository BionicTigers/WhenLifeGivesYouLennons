package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;


@TeleOp(name="Mercury Mechanum TeleOp", group="Outreach")


public class TeleOpMercury extends OpMode {
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private int orientTime, hostileTakeover;
    private double calibToggle, takeoverToggle;
    BNO055IMU imu;
    private Orientation angles;
    public Acceleration gravity;
    private ElapsedTime runtime = new ElapsedTime();


    public void init() {
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");

        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        orientTime = 1;
        calibToggle = 0;
        takeoverToggle = 0;
        hostileTakeover = 0;
    }


    public void loop() {
        if (gamepad2.dpad_down && (runtime.seconds() > takeoverToggle)) {
            takeoverToggle = runtime.seconds() + 1;
            ++hostileTakeover;
        }

        if (hostileTakeover % 2 == 0) {
            if (gamepad1.y) { //orientation calibration
                // Get the calibration data
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

            if (gamepad1.x && (runtime.seconds() > calibToggle)) {
                calibToggle = runtime.seconds() + 1;
                ++orientTime;
            }

            if (calibToggle % 2 == 0) { //when toggled we are oriented with this math
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
                double P = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
                double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x);
                double rightX = -gamepad1.right_stick_x;


                final double v5 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                final double v6 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) + rightX;
                final double v7 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                final double v8 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) + rightX;

                motorFrontLeft.setPower(v5);//1
                motorFrontRight.setPower(v6);//2
                motorBackLeft.setPower(v7);//3
                motorBackRight.setPower(v8);//4

            } else { //regular drive
                double P = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
                double robotAngle = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
                double rightX = -gamepad1.right_stick_x;
                double sinRAngle = Math.sin(robotAngle);
                double cosRAngle = Math.cos(robotAngle);

                final double v1 = (P * sinRAngle) + (P * cosRAngle) + rightX;
                final double v2 = (P * sinRAngle) - (P * cosRAngle) - rightX;
                final double v3 = (P * sinRAngle) - (P * cosRAngle) + rightX;
                final double v4 = (P * sinRAngle) + (P * cosRAngle) - rightX;

                motorFrontRight.setPower(v1);
                motorFrontLeft.setPower(v2);
                motorBackRight.setPower(v3);
                motorBackLeft.setPower(v4);
            }
        } else {
            if (gamepad2.y) { //orientation calibration
                // Get the calibration data
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

            if (gamepad2.x && (runtime.seconds() > calibToggle)) {
                calibToggle = runtime.seconds() + 1;
                ++orientTime;
            }

            if (calibToggle % 2 == 0) { //when toggled we are oriented with this math
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
                double P = Math.hypot(-gamepad2.left_stick_x, -gamepad2.left_stick_y);
                double robotAngle = Math.atan2(-gamepad2.left_stick_y, gamepad2.left_stick_x);
                double rightX = -gamepad2.right_stick_x;


                final double v5 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                final double v6 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) + rightX;
                final double v7 = P * Math.sin(robotAngle - angles.firstAngle) - P * Math.cos(robotAngle - angles.firstAngle) - rightX;
                final double v8 = P * Math.sin(robotAngle - angles.firstAngle) + P * Math.cos(robotAngle - angles.firstAngle) + rightX;

                motorFrontLeft.setPower(v5);//1
                motorFrontRight.setPower(v6);//2
                motorBackLeft.setPower(v7);//3
                motorBackRight.setPower(v8);//4

            } else { //regular drive
                double P = Math.hypot(-gamepad2.left_stick_x, -gamepad2.left_stick_y);
                double robotAngle = Math.atan2(-gamepad2.left_stick_y, -gamepad2.left_stick_x);
                double rightX = -gamepad2.right_stick_x;
                double sinRAngle = Math.sin(robotAngle);
                double cosRAngle = Math.cos(robotAngle);

                final double v1 = (P * sinRAngle) + (P * cosRAngle) + rightX;
                final double v2 = (P * sinRAngle) - (P * cosRAngle) - rightX;
                final double v3 = (P * sinRAngle) - (P * cosRAngle) + rightX;
                final double v4 = (P * sinRAngle) + (P * cosRAngle) - rightX;

                motorFrontRight.setPower(v1);
                motorFrontLeft.setPower(v2);
                motorBackRight.setPower(v3);
                motorBackLeft.setPower(v4);
            }
        }
    }
}