package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "lol")
public class PIDF extends LinearOpMode {
    private DcMotorEx boardEx;
    @Override
    public void runOpMode() {
        Trajectory trajectory = new Trajectory(2940f, 1000f, 4567.2f, 80f);
        float[][] traj = trajectory.getTraj();
        ElapsedTime elMili;
        boardEx = (DcMotorEx) hardwareMap.get("clark");
        telemetry.addData("mili seconds ", traj.length);
        waitForStart();
        elMili = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while (opModeIsActive())
            while (elMili.time() < traj.length) {
                boardEx.setVelocity(traj[(int) elMili.time()][0]);
            }
    }
    }
//    public double findMaxAccel(){
//        tim = new ElapsedTime();
//        //boardEx.setPower(1);
//        double startTime = 0;
//        double finTime = 0;
//        double startVel =0;
//        double finVel = 0;
//        double maxAccel = 0;
//        double Accel;
//        while (tim.time()<1000){
//            startTime = tim.time();
//          startVel =  boardEx.getVelocity();
//          sleep(10);
//          finVel = boardEx.getVelocity();
//          finTime = tim.time();
//             Accel = (finVel - startVel) / (finTime - startTime);
//             if(maxAccel<Accel)
//                 maxAccel= Accel;
//        }
//        return maxAccel;
//    }


