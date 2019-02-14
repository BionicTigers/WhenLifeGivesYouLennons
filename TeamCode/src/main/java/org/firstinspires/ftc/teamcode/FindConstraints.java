package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;

@TeleOp (name = "finding PID constraints")
public class FindConstraints extends OpMode {
public DcMotorEx frontLeft;
    private int starto;
    private int endo;
    private double oldspeed=0;
    private double maxVel=0;
    private double acc=0;
    private double maxAcc=0;
    ElapsedTime elTiempo;


    @Override
    public void init() {
        frontLeft = (DcMotorEx) hardwareMap.get("extendy");

        elTiempo = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    @Override
    public void loop()  {
        frontLeft.setPower(1);


            starto = (int)elTiempo.time();
            if(frontLeft.getVelocity()>maxVel)
                maxVel = frontLeft.getVelocity();
            acc = (oldspeed-frontLeft.getVelocity())/(endo-starto);
            if (acc>maxAcc)
                maxAcc=acc;
            endo = (int)elTiempo.time();
            oldspeed = frontLeft.getVelocity();
            telemetry.addData("maxVel",frontLeft.getVelocity());
            telemetry.addData("maxAcc",maxAcc);
            telemetry.addData("position", frontLeft.getCurrentPosition());
            telemetry.update();
        try {
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
