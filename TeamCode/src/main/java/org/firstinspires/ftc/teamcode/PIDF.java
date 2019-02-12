package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
 class MotorControl extends Thread{
     @Override
     public void run() {

        // super.run();
     }
 }

@Autonomous(name = "lol")
public class PIDF extends LinearOpMode {
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backRight;
    private double velocity;
    private float speed;
    private ElapsedTime elMili;
    private double start;
    private int startEncoderPosition;
    private float positionError;
    public static float handyDandyPosPID(float positionError, float PGain){
        return positionError*PGain;
    }

    @Override

    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();
        Trajectory trajectory = new Trajectory((320f)/4f, 511f, (6400f)/4f, 80f);
        float[][] traj = trajectory.getTrajectory();


        frontLeft = (DcMotorEx) hardwareMap.get("frontLeft");
//        frontRight = (DcMotorEx) hardwareMap.get("frontRight");
//        backLeft = (DcMotorEx) hardwareMap.get("backLeft");
//        backRight = (DcMotorEx) hardwareMap.get("backRight");
        elMili = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        waitForStart();
        start = elMili.time();
        startEncoderPosition = frontLeft.getCurrentPosition();
        while(opModeIsActive()) {
            if(RobotValues.WTFAREWEEVENDOING == 0){
                telemetry.addData("What do you want to do","woo");
                telemetry.update();
                start = elMili.time();
                dashboardTelemetry.addData("frontLeft Speeed", frontLeft.getVelocity());
//                dashboardTelemetry.addData("frontRight",frontRight.getVelocity());
//                dashboardTelemetry.addData("backLeft",backLeft.getVelocity());
//                dashboardTelemetry.addData( "backRight",backRight.getVelocity());
//                dashboardTelemetry.addData("backRight pos", backRight.getCurrentPosition());
//                dashboardTelemetry.addData("backRight pos", backLeft.getCurrentPosition());
//                dashboardTelemetry.addData("backRight pos", frontRight.getCurrentPosition());
//                dashboardTelemetry.addData("backRight pos", frontRight.getCurrentPosition());
//                dashboardTelemetry.addData("What it should be ", speed);
                dashboardTelemetry.addData("Velocity error",speed-frontLeft.getVelocity());
                dashboardTelemetry.update();
            }if(RobotValues.WTFAREWEEVENDOING==1){
            while ((int)(elMili.time())-start<traj.length) {
                //positionError = startEncoderPosition - traj[(int) (elMili.time() - start)][2];
                // Set some cool pid values
                frontLeft.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
//                frontRight.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
//                backLeft.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
//                frontLeft.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
                dashboardTelemetry.addData("frontLeft Speeed", frontLeft.getVelocity());
//                dashboardTelemetry.addData("frontRight",frontRight.getVelocity());
//                dashboardTelemetry.addData("backLeft",backLeft.getVelocity());
//               dashboardTelemetry.addData( "backRight",backRight.getVelocity());
//               dashboardTelemetry.addData("backRight pos", backRight.getCurrentPosition());
                dashboardTelemetry.addData("What it should be ", speed);
                dashboardTelemetry.addData("Velocity error",speed-frontLeft.getVelocity());
                dashboardTelemetry.update();

                speed = traj[(int) (elMili.time() - start)][1];
                telemetry.addData("velocity", speed);
                telemetry.update();
              //  frontRight.setVelocity(-speed);
                frontLeft.setVelocity(speed);
                //backRight.setVelocity(-speed);
                //backLeft.setVelocity(speed);
            }
            start = elMili.time();
        }
            if(RobotValues.WTFAREWEEVENDOING==2){
                while ((int)(elMili.time())-start<traj.length) {
                    positionError = startEncoderPosition - traj[(int) (elMili.time() - start)][2];
                    // Set some cool pid values
                    frontLeft.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
//                    frontRight.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
//                    backLeft.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
//                    frontLeft.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
                    dashboardTelemetry.addData("frontLeft Speeed", frontLeft.getVelocity());
                    dashboardTelemetry.addData("What it should be ", speed);
                    dashboardTelemetry.addData("Velocity error",speed-frontLeft.getVelocity());
                    dashboardTelemetry.update();

                    speed = traj[(int) (elMili.time() - start)][1];
                    telemetry.addData("velocity", speed);
                    telemetry.update();
     //               frontRight.setVelocity(speed);
                    frontLeft.setVelocity(-speed);
//                    backRight.setVelocity(speed);
//                    backLeft.setVelocity(-speed);
                }
            }}
    }
    


}


