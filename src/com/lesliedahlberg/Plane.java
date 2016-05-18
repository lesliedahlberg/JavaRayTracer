package com.lesliedahlberg;

import java.awt.*;

/**
 * Created by lesliedahlberg on 18/05/16.
 */
public class Plane extends Shape{
    Vector3 normal;
    Vector3 point;
    float opacity;
    Color color;

    public Plane(Vector3 normal,Vector3 point, Color color, float opacity){
        this.normal = normal;
        this.point = point;
        this.opacity = opacity;
        this.color = color;
    }

    public Vector3 normal(Vector3 hitPoint){
        if (hitPoint.dot(normal)>0){
            return normal.getNegative();
        }
        return normal;
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
        float t = (point.subtract(eyeRay.origin).dot(normal))/(eyeRay.direction.dot(normal));
        if (t >= 0){
            return t;
        }else {
            return -1;
        }
    }
}
