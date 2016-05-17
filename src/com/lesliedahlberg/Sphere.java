package com.lesliedahlberg;

import java.util.Vector;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class Sphere {
    Vector3 center;
    int radius;
    public Sphere(int x, int y, int z, int r){
        center = new Vector3(x, y, z);
        radius = r;
    }

    public float hit(Vector3 eyeRay) {
        float A = eyeRay.dot(eyeRay);
        float B = 2*(center.getNegative().dot(eyeRay));
        float C = center.getNegative().dot(center.getNegative()) - radius*radius;
        float D = B*B - 4*A*C;
        if(D<0){
            return -1;
        }else {
            float t1 = (float) ((-B + Math.sqrt(D))/2*A);
            float t2 = (float) ((-B - Math.sqrt(D))/2*A);
            if (t1 >= 0 && t2 >= 0){
                if(t1 < t2)
                    return t1;
                if(t2 <= t1)
                    return t2;
            }
            if(t1 >= 0){
                return t1;
            }
            if(t2 >= 0){
                return t2;
            }

        }
        return -1;
    }
}
