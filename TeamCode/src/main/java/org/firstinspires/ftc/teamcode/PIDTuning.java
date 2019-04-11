package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class PIDTuning extends LinearOpMode {
    private DcMotorEx frontLeft;
    private double velocity;
    private float speed;
    private ElapsedTime elMili;
    private double start;
    private int startEncoderPosition;
    private float positionError;

    public static float handyDandyPosPID(float positionError, float PGain) {
        return positionError * PGain;
    }

    @Override
    public synchronized void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();
        Trajectory trajectory = new Trajectory((320f) / 4f, 511f, (6400f) / 4f, 80f);
        float[][] traj = trajectory.getTrajectory();


        frontLeft = (DcMotorEx) hardwareMap.get("frontLeft");
        elMili = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        waitForStart();
        start = elMili.time();
        startEncoderPosition = frontLeft.getCurrentPosition();
        while (opModeIsActive()) {
            if (RobotValues.WTFAREWEEVENDOING == 1 && elMili.time() - start < traj.length) {
                while ((int) (elMili.time()) - start < traj.length) {
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
                    dashboardTelemetry.addData("Velocity error", speed - frontLeft.getVelocity());
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
            if (RobotValues.WTFAREWEEVENDOING == 2) {
                while ((int) (elMili.time()) - start < traj.length) {
                    positionError = startEncoderPosition - traj[(int) (elMili.time() - start)][2];
                    // Set some cool pid values
                    frontLeft.setVelocityPIDFCoefficients(RobotValues.KV, 0, 0, RobotValues.FEED_FORWARD * (traj[(int) (elMili.time() - start + 5)][1]));
//                                         dashboardTelemetry.addData("frontLeft Speeed", frontLeft.getVelocity());
                    dashboardTelemetry.addData("What it should be ", speed);
                    dashboardTelemetry.addData("Velocity error", speed - frontLeft.getVelocity());
                    dashboardTelemetry.update();

                    speed = traj[(int) (elMili.time() - start)][1];
                    telemetry.addData("velocity", speed);
                    telemetry.update();
                    //               frontRight.setVelocity(speed);
                    frontLeft.setVelocity(-speed);
//                    backRight.setVelocity(speed);
//                    backLeft.setVelocity(-speed);
                }
            }
        }
    }
}
