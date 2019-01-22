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
    private ElapsedTime tim;

    @Override
    public void runOpMode() throws InterruptedException {


        Trajectory trajectory = new Trajectory(2940f,10000f,4567.2f,80f);
        float[][]traj = trajectory.getTraj();
        ElapsedTime elMili;
        waitForStart();
        elMili= new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        while (opModeIsActive()) {
            boardEx =  (DcMotorEx) hardwareMap.dcMotor.get("clark");
            boardEx.setPower(1);


            FtcDashboard dashboard = FtcDashboard.getInstance();
            Telemetry dashboardTelemetry = dashboard.getTelemetry();

            dashboardTelemetry.addData("Vel", boardEx.getVelocity());
            dashboardTelemetry.addData("Projected Vel",traj[(int)elMili.time()/1000][1]);
            dashboardTelemetry.addData("time",elMili.time());
            dashboardTelemetry.update();
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

}
