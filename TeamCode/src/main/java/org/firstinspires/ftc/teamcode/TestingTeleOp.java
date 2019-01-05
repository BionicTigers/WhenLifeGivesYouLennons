//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.TouchSensor;
//
//@TeleOp(name="Mongoose TelefdfOp", group="Testing")
//
//public class TestingTeleOp extends OpMode {
//    private TeleOpNav nav;
//    private boolean canMoveLiftyJr;
//    private TouchSensor limitSwitch;
//    private DcMotor lifty;
//    private int liftySpeed;
//
//
//    @Override
//    public void init() {
//        nav = new TeleOpNav(this);
//        canMoveLiftyJr = true;
//        limitSwitch = hardwareMap.touchSensor.get("limitSwitch");
//        lifty = hardwareMap.dcMotor.get("lifty");
//        lifty.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        liftySpeed = 1;
//
//    }
//
//    @Override
//    public void loop() {
//        if (limitSwitch.isPressed()/* && (runtime.seconds() > liftToggle)*/) {
//            nav.resetEncoders();
//        }
//        lifty.setPower(-gamepad2.right_stick_y);
//        telemetry.addData("Thing", lifty.getCurrentPosition());
//        telemetry.update();
//
//    }
//}
