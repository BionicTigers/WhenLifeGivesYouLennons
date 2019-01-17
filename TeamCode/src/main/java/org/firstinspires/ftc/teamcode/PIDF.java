package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.robotcore.util.ElapsedTime.Resolution.MILLISECONDS;
import static java.lang.Thread.sleep;

public class PIDF extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        while (true) {
            DcMotor board1 = hardwareMap.dcMotor.get("clark");
            board1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            board1.setPower(.5);
            telemetry.addData("", board1.getPower());
            telemetry.update();
        }

    }
}
