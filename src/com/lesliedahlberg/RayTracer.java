package com.lesliedahlberg;

import java.awt.*;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class RayTracer {
    int projectionPlane;
    int width;
    int height;
    Sphere sphere;
    Vector3 light;

    public RayTracer(int projectionPlane, int width, int height, Sphere sphere, Vector3 light){
        this.projectionPlane = projectionPlane;
        this.width = width;
        this.height = height;
        this.sphere = sphere;
        this.light = light;
    }

    private int phong(Vector3 hitPoint, Vector3 normal){
        Vector3 toLight = light.subtract(hitPoint);
        toLight.normalize();
        float diffuse = Math.max(0, normal.dot(toLight));

        float ambient = (float) 0.1;


        Vector3 V = hitPoint.getNegative();
        V.normalize();
        Vector3 R = normal.multiply(2*toLight.dot(normal)).subtract(toLight);
        //float specular = (float) Math.max(Math.pow(R.dot(V), 32), 0);
        float specular = (float) Math.pow(Math.max(R.dot(V), 0), 32);

        float intensities = Math.min(1, (specular/10+diffuse+ambient));
        return new Color(intensities, intensities, intensities).getRGB();
    }

    public int[][] trace(){
        int[][] image = new int[width][height];
        for (int i = -width/2, x = 0; i < width/2; i++, x++){
            for (int j = -height/2, y = 0; j < height/2; j++, y++){
                Vector3 eyeRay = new Vector3(i, j, projectionPlane);
                eyeRay.normalize();
                float t = sphere.hit(eyeRay);
                if(t >= 0){
                    Vector3 hitPoint = eyeRay.multiply(t);
                    Vector3 normal = hitPoint.subtract(sphere.center);
                    normal.normalize();
                    image[x][y] = phong(hitPoint, normal);
                }else {
                    image[x][y] = new Color(10, 0, 0).getRGB();
                }
            }
        }
        return image;
    }
}
