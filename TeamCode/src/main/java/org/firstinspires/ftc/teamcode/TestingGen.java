package org.firstinspires.ftc.teamcode;

public class TestingGen {
    public static void main(String args[]){
        Trajectory trajectory = new Trajectory((320f)/4f, 511f, (6400f)/4f, 80f);
        for(int i = 0; i<trajectory.getTrajectory().length;i++){
            System.out.println(trajectory.getTrajectory()[i][1]);
        }
    }

}
