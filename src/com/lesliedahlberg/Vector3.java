package com.lesliedahlberg;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class Vector3 {

    float x, y, z;

    Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vector3(Vector3 p1, Vector3 p2) {
        this.x = p2.x - p1.x;
        this.y = p2.y - p1.y;
        this.z = p2.z - p1.z;
    }

    public Vector3 add(Vector3 vector) {
        return new Vector3(x + vector.x, y + vector.y, z + vector.z);
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

    public static Vector3 randomUnitVector(Vector3 normal){
        Vector3 random = new Vector3((float)Math.random(), (float)Math.random(), (float)Math.random());
        random.normalize();
        if(random.dot(normal) < 0){
            random = random.getNegative();
        }
        return random;
    }

}