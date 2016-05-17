package com.lesliedahlberg;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class Ray {
    Vector3 direction;
    Vector3 origin;
    public Ray(Vector3 origin, Vector3 direction){
        this.direction = direction;
        this.origin = origin;
    }
}
