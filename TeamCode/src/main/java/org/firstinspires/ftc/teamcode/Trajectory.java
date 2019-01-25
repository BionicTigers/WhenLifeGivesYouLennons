package org.firstinspires.ftc.teamcode;


public class Trajectory {
    private float[][] traj;
    public Trajectory(float maxVelocity, float distance, float maxAcceleration, float jerk){
        traj=fineIllCreateAGeneralGenClass(maxVelocity,distance,maxAcceleration,jerk);
    }

    public float[][] getTraj() {
        return traj;
    }

    public  float[][] fineIllCreateAGeneralGenClass(float maxVelocity, float distance, float maxAcceleration, float jerk) {
        float t1Time = maxAcceleration / jerk;
        float t2Time = (maxVelocity - (maxAcceleration * t1Time)) / maxAcceleration;
        float t1Displacement = (1 / 6f) * (float) Math.pow(t1Time, 3) * jerk;
        float t2Displacement = (1 / 2f) * (float) Math.pow(t2Time, 2) * maxAcceleration;
        float t3Displacement = t1Displacement;
        float rampDisplacement = (t1Displacement + t2Displacement + t3Displacement);
        float[][] trajectory;
        if (rampDisplacement > distance) {
            trajectory = Triangle(distance, maxAcceleration);
        } else {
            trajectory = S_Curve(maxVelocity, distance, maxAcceleration, jerk);
        }
        return trajectory;
    }

    public  float[][] okButwhatIfItsShort(float maxVelocity, float distance, float maxAcceleration, float jerk) {

        float velocity = 0;
        float position = 0;
        float acceleration = 0;

        float t1Time = Math.abs(maxAcceleration / jerk);
        float t2Time = Math.abs(maxVelocity - (maxAcceleration * t1Time)) / maxAcceleration;
        float t1Displacement = (1 / 6f) * (float) Math.pow(t1Time, 3) * jerk;
        float t2Displacement = (1 / 2f) * (float) Math.pow(t2Time, 2) * maxAcceleration;

        float t3Displacement = t1Displacement;
        float rampTime = (t1Time + t2Time + t1Time);
        float rampDisplacement = (t1Displacement + t2Displacement + t3Displacement);
        float[][] trajectory = new float[(int) (rampTime * 1000f * 2f) + 5][3];
        int t1 = 0;

        for (int i = 0; i < t1Time * 1000f; i++) {
            acceleration = jerk * t1 / 1000f;
            trajectory[t1][0] = acceleration;
            velocity = acceleration * (1f / 1000f) + velocity;
            trajectory[t1][1] = velocity;
            t1++;
            System.out.println("in 1");

        }
        System.out.println(rampTime);
        for (int x = 0; x < t2Time * 1000f; x++) {
            trajectory[t1][0] = maxAcceleration;
            velocity = trajectory[t1][0] * 1 / 1000f + velocity;
            trajectory[t1][1] = velocity;
            t1++;
            System.out.println("in 2");
            //System.out.println();

        }

        for (int i = 0; i < t1Time * 1000f; i++) {
            acceleration = acceleration - jerk * (1 / 1000f);
            trajectory[t1][0] = acceleration;
            velocity = acceleration * (1f / 1000f) + velocity;
            trajectory[t1][1] = velocity;
            t1++;
            System.out.println("in 3");
        }

        for (int i = 0; i < t1Time * 1000f; i++) {
            //System.out.println(t1);
            acceleration = jerk * i / 1000f;
            trajectory[t1][0] = acceleration;
            velocity = velocity - acceleration * (1f / 1000f);
            trajectory[t1][1] = velocity;
            t1++;
            System.out.println("in 4");

        }
        for (int i = 0; i < t2Time * 1000f; i++) {
            trajectory[t1][0] = maxAcceleration;
            velocity = velocity - maxAcceleration / 1000f;
            trajectory[t1][1] = velocity;
            t1++;
            System.out.println("in 5");
        }
        for (int i = 0; i < t1Time * 1000f - 1; i++) {
            acceleration = acceleration - jerk * (1 / 1000f);
            trajectory[t1][0] = acceleration;
            velocity = velocity - acceleration * (1f / 1000f);
            trajectory[t1][1] = velocity;
            t1++;
            System.out.println("in 6");

        }
        return trajectory;
    }

    private  float[][] S_Curve(float maxVelocity, float distance, float maxAcceleration, float jerk) {
        System.out.println("in sCurve");
        float velocity = 0;
        float position = 0;
        float acceleration = 0;
        float t1Time = maxAcceleration / jerk;
        float t2Time = (maxVelocity - (maxAcceleration * t1Time)) / maxAcceleration;
        float t1Displacement = (1 / 6f) * (float) Math.pow(t1Time, 3) * jerk;
        float t2Displacement = (1 / 2f) * (float) Math.pow(t2Time, 2) * maxAcceleration;
        float t3Displacement = t1Displacement;
        float rampTime = (t1Time + t2Time + t1Time);
        float rampDisplacement = (t1Displacement + t2Displacement + t3Displacement);
        float t4Displacement = distance - (rampDisplacement * 2);
        float t4Time = maxVelocity / t4Displacement;
        float[][] trajectory = new float[(int) (rampTime * 1000 + rampTime * 1000f + t4Time * 1000f) + 1][3];
        System.out.println(rampTime + " " + t4Time);
        int t1 = 0;
        for (int i = 0; i < t1Time * 1000f; i++) {
            acceleration = jerk * t1 / 1000f;
            trajectory[t1][0] = acceleration;
            velocity = acceleration * (1f / 1000f) + velocity;
            trajectory[t1][1] = velocity;
            t1++;
           

        }
        for (int i = 0; i < t2Time * 1000f; i++) {
            trajectory[t1][0] = maxAcceleration;
            velocity = trajectory[t1][0] * 1 / 1000f + velocity;
            trajectory[t1][1] = velocity;
            t1++;
        }
        for (int i = 0; i < t1Time * 1000f; i++) {
            acceleration = acceleration - jerk * (1 / 1000f);
            trajectory[t1][0] = acceleration;
            velocity = acceleration * (1f / 1000f) + velocity;
            trajectory[t1][1] = velocity;
            t1++;


        }
        for (int i = 0; i < t4Time * 1000f; i++) {
            trajectory[t1][0] = 0.0f;
            trajectory[t1][1] = maxVelocity;
            t1++;
        }
        for (int i = 0; i < t1Time * 1000f; i++) {
            acceleration = jerk * i / 1000f;
            trajectory[t1][0] = acceleration;
            velocity = velocity - acceleration * (1f / 1000f);
            trajectory[t1][1] = velocity;
            t1++;

        }
        for (int i = 0; i < t2Time * 1000f; i++) {
            trajectory[t1][0] = maxAcceleration;
            velocity = velocity - maxAcceleration / 1000f;
            trajectory[t1][1] = velocity;
            t1++;
        }
        for (int i = 0; i < t1Time * 1000f - 1; i++) {
            acceleration = acceleration - jerk * (1 / 1000f);
            trajectory[t1][0] = acceleration;
            velocity = velocity - acceleration * (1f / 1000f);
            trajectory[t1][1] = velocity;
            t1++;

        }


        return trajectory;
    }

    private  float[][] Trapazoid(float maxVelocity, float distance, float maxAcceleration) {
        float rampTime = maxVelocity / maxAcceleration;
        float rampDistance = rampTime * maxAcceleration / 2;
        float sustainDistance = distance - rampDistance * 2;

        float sustainTime = sustainDistance / maxVelocity;
        int timeToCompleteMili = (int) ((rampTime * 2 + sustainTime) * 1000) + 1;
        float[][] trajectory = new float[timeToCompleteMili][2];
        int t = 0;
        float velocity = 0;
        float position = 0;

        for (int i = 0; i < rampTime * 1000; i++) {
            velocity = (i * maxAcceleration) / 1000;
            position += velocity / 1000;
            trajectory[t][0] = velocity;
            trajectory[t][1] = position;
            t++;
        }
        for (int i = 0; i < sustainTime * 1000; i++) {
            velocity = 6;
            position += velocity / 1000;
            trajectory[t][0] = velocity;
            trajectory[t][1] = position;
            t++;

        }
        for (int i = 0; i < rampTime * 1000; i++) {
            velocity = velocity - (maxAcceleration / 1000);
            position += velocity / 1000;
            trajectory[t][0] = velocity;
            trajectory[t][1] = position;
            t++;
        }

        return trajectory;
    }

    public  float[][] Triangle(float distance, float MaxAcceleration) {
        int timeToCompleteMili = (int) (distance * 2f / MaxAcceleration * 1000);

        float velocity = 0;
        float[][] trajectory = new float[timeToCompleteMili][1];
        for (int t = 0; t < (timeToCompleteMili) / 2; t++) {
            velocity = t * MaxAcceleration / 1000;
            trajectory[t][0] = velocity;
        }
        int j = 0;
        for (int t = (int) (timeToCompleteMili) / 2; t < (timeToCompleteMili); t++) {
            velocity = velocity - MaxAcceleration / 1000;
            trajectory[t][0]= velocity;
            j++;
        }
        return trajectory;
    }
}
