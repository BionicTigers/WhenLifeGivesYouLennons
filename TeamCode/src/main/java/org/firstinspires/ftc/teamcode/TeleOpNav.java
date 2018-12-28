package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class TeleOpNav {
    private final DcMotor lifty;
    private DcMotor liftyJr;
    private com.qualcomm.robotcore.eventloop.opmode.OpMode hardwareGetter;
    private TouchSensor limitSwitch;
    TeleOpNav(OpMode hardWareGetter){
        this.hardwareGetter = hardWareGetter;
        liftyJr = hardWareGetter.hardwareMap.dcMotor.get("liftyJr");
//        liftyJr.setDirection(DcMotor.Direction.REVERSE);
        liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        limitSwitch = hardWareGetter.hardwareMap.touchSensor.get("limitSwitch");
        lifty = hardWareGetter.hardwareMap.dcMotor.get("lifty");
        lifty.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void resetEncoders(){
        liftyJr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void goUpBit(){

        if(liftyJr.getMode() != DcMotor.RunMode.RUN_TO_POSITION){
            liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        liftyJr.setPower(1);
        liftyJr.setTargetPosition(-1500);
    }
    public void goUpAll(){

        if(liftyJr.getMode() != DcMotor.RunMode.RUN_TO_POSITION){
            liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        liftyJr.setPower(1);
        liftyJr.setTargetPosition(-2050);
    }
    public void goDown() {

        if (liftyJr.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            liftyJr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
//        while (!limitSwitch.isPressed())
        liftyJr.setPower(1);
        liftyJr.setTargetPosition(0);
//    }
    }
    public final void ITS_ENDGAME_NOW(){
        if (lifty.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            lifty.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        lifty.setPower(1);
        lifty.setTargetPosition(9800);

    }

}
