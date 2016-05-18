package com.lesliedahlberg;


import java.awt.*;
import java.util.Vector;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class Sphere extends Shape{
    Vector3 center;
    int radius;
    float opacity = 1;
    Color color;
    public Sphere(int x, int y, int z, int r, Color color, float opacity){
        center = new Vector3(x, y, z);
        radius = r;
        this.opacity = opacity;

        this.color = color;
    }

    public Vector3 normal(Vector3 hitPoint){
        return new Vector3(center, hitPoint);
    }

    @Override
    public float Opacity() {
        return opacity;
    }

    @Override
    public Color Color() {
        return color;
    }

    public float hit(Ray eyeRay) {
        float A = eyeRay.direction.dot(eyeRay.direction);
        float B = 2*(eyeRay.origin.subtract(center).dot(eyeRay.direction));
        float C = eyeRay.origin.subtract(center).dot(eyeRay.origin.subtract(center)) - radius*radius;
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
