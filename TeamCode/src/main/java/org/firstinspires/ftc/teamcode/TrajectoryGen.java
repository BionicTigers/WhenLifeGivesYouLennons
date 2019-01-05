package org.firstinspires.ftc.teamcode;


public class TrajectoryGen {
    public static void main(String args[]) {

        float[][] tra = S_Curve(8,10, 14f,80f);
        for (int i = 0; i < tra.length; i++) {
           System.out.println(i / 1000f + ", " + tra[i][0]+", "+tra[i][1]);
        }
    }

//    public static float[] GenerateTrajectory(float maxVelocity, float MaxAcceleration, float distance) {
//        float vn = (float) Math.sqrt((MaxAcceleration * distance));
//        if (vn < maxVelocity) {
//            return Triangle(distance, MaxAcceleration);
//        } else {
//            return Trapazoid(maxVelocity, distance, maxVelocity);
//        }
//    }
private static float[][] S_Curve(float maxVelocity, float distance, float maxAcceleration,float jerk) {
//    float sustainTime = sustainDistance / maxVelocity;
//    int timeToCompleteMili = (int) ((rampTime * 2 + sustainTime) * 1000)+1;
//    float[][] tragectory = new float[timeToCompleteMili][2];
    int t = 0;
    float velocity=0;
    float position = 0;
    float acceleration = 0;
    float sRamptime=maxAcceleration/jerk;
    double curveSus = (maxVelocity-(maxAcceleration*sRamptime))/maxAcceleration;
//    double mac = (Math.pow(maxAcceleration,2)/jerk+(maxAcceleration*curveSus));
//    System.out.println(mac);
    double rampTime = sRamptime*2+curveSus;
    System.out.println(curveSus);
    System.out.println(sRamptime);
    double rampDistance = rampTime * maxAcceleration / 2;
    float[][] tragectory = new float[(int)(rampTime*1000f)+1][2];
    int t1 =0;
    //System.out.println(sRamptime);
    for (int i = 0; i<sRamptime*1000f;i++){
        acceleration=jerk*t1/1000f;
        tragectory[t1][0]=acceleration;
        velocity = acceleration*(1f/1000f)+velocity;
        tragectory[t1][1]=velocity;
        t1++;
    }
    for (int i = 0;i<curveSus*1000f;i++ ) {
        tragectory[t1][0]=maxAcceleration;
        velocity = tragectory[t1][0]*1/1000f+velocity;
        tragectory[t1][1]= 2*velocity;
        t1++;
    }
    System.out.println(velocity);
    for (int i = 0; i<sRamptime*1000f;i++){
        acceleration=acceleration-jerk*(1/1000f);
        tragectory[t1][0]=acceleration;
        velocity=acceleration*(1f/1000f)+velocity;
        tragectory[t1][1]=velocity;
        t1++;
    }


//    for (int i = 0; i < rampTime*1000; i++) {
//        velocity = (i*maxAcceleration) / 1000;
//        position+=velocity/1000;
//        tragectory[t][0] = velocity;
//        tragectory[t][1] = position;
//        t++;
//    }
//    for (int i = 0; i<sustainTime*1000;i++){
//        velocity = 6;
//        position+=velocity/1000;
//        tragectory[t][0] = velocity;
//        tragectory[t][1] = position;
//        t++;
//
//    }
//    for (int i = 0; i < rampTime*1000; i++) {
//        velocity = velocity-(maxAcceleration / 1000);
//        position+=velocity/1000;
//        tragectory[t][0] = velocity;
//        tragectory[t][1] = position;
//        t++;
//    }
    return tragectory;
}

    private static float[][] Trapazoid(float maxVelocity, float distance, float maxAcceleration) {
        float rampTime = maxVelocity / maxAcceleration;
        float rampDistance = rampTime * maxAcceleration / 2;
        float sustainDistance = distance - rampDistance * 2;

        float sustainTime = sustainDistance / maxVelocity;
        int timeToCompleteMili = (int) ((rampTime * 2 + sustainTime) * 1000)+1;
        float[][] tragectory = new float[timeToCompleteMili][2];
        int t = 0;
        float velocity=0;
        float position = 0;

        for (int i = 0; i < rampTime*1000; i++) {
            velocity = (i*maxAcceleration) / 1000;
            position+=velocity/1000;
            tragectory[t][0] = velocity;
            tragectory[t][1] = position;
            t++;
        }
        for (int i = 0; i<sustainTime*1000;i++){
            velocity = 6;
            position+=velocity/1000;
            tragectory[t][0] = velocity;
            tragectory[t][1] = position;
            t++;

        }
        for (int i = 0; i < rampTime*1000; i++) {
            velocity = velocity-(maxAcceleration / 1000);
            position+=velocity/1000;
            tragectory[t][0] = velocity;
            tragectory[t][1] = position;
            t++;
        }
        return tragectory;
    }

    public static float[] Triangle(float distance, float MaxAcceleration) {
        int timeToCompleteMili = (int) (distance * 2f / MaxAcceleration * 1000);

        float velocity = 0;
        float[] Tragectory = new float[timeToCompleteMili];
        for (int t = 0; t < (timeToCompleteMili) / 2; t++) {
            velocity = t * MaxAcceleration / 1000;
            Tragectory[t] = velocity;
        }
        int j = 0;
        for (int t = (int) (timeToCompleteMili) / 2; t < (timeToCompleteMili); t++) {
            velocity = velocity - MaxAcceleration / 1000;
            Tragectory[t] = velocity;
            j++;
        }
        return Tragectory;
    }
}
