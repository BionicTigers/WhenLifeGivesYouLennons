package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FindConstraints extends LinearOpMode {
public DcMotorEx frontLeft;
    private int starto;
    private int endo;
    private double oldspeed=0;
    private double maxVel=0;
    private double acc=0;
    private double maxAcc=0;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = (DcMotorEx) hardwareMap.get("frontLeft");
        frontLeft.setPower(1);
        ElapsedTime elTiempo = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while (opModeIsActive()){
            starto = (int)elTiempo.time();
            if(frontLeft.getVelocity()>maxVel)
                maxVel = frontLeft.getVelocity();
            acc = (oldspeed-frontLeft.getVelocity())/(endo-starto);
            if (acc>maxAcc)
                maxAcc=acc;
            endo = (int)elTiempo.time();
            oldspeed = frontLeft.getVelocity();
            telemetry.addData("maxVel",maxVel);
            telemetry.addData("maxAcc",maxAcc);
        }
        }
}
