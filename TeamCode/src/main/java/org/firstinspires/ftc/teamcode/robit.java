package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class robit {
    private DcMotor board;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private int initialposition;
    private int finalposition;
    private DcMotor backRight;
    private ElapsedTime timeboi;
    public robit (DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br){
        frontLeft = fl; frontRight = fr; backLeft= bl; backRight =br;
    }
    public robit(DcMotor board1){board = board1;}
    public float getSpeed(boolean encoders, boolean useboard){
        board.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        initialposition = board.getCurrentPosition();
        timeboi.reset();
        while (timeboi.time()<100){}
        return board.getCurrentPosition()-initialposition*10;
    }
}
