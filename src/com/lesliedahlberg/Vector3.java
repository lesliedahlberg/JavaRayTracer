package com.lesliedahlberg;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class Vector3 {

    float x, y, z; // package-private variables; nice encapsulation if you place this in a maths package of something

    Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this; // method chaining would be very useful
    }

    public void normalize() {
        float length = (float) Math.sqrt(x*x + y*y + z*z);
        x /= length;
        y /= length;
        z /= length;
    }

    public Vector3 getNegative(){
        return new Vector3(-x, -y, -z);
    }

    public float dot(Vector3 eyeRay) {
        return x*eyeRay.x + y*eyeRay.y + z*eyeRay.z;
    }

    public Vector3 multiply(float t) {
        return new Vector3(x*t, y*t, z*t);
    }

    public Vector3 subtract(Vector3 center) {
        return new Vector3(x-center.x, y-center.y, z-center.z);
    }
}