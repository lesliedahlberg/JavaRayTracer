package com.lesliedahlberg;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class RayTracer {
    int projectionPlane;
    int width;
    int height;
    ArrayList<Sphere> spheres;
    ArrayList<Light> lights;

    public RayTracer(int projectionPlane, int width, int height, ArrayList<Sphere> spheres, ArrayList<Light> lights){
        this.projectionPlane = projectionPlane;
        this.width = width;
        this.height = height;
        this.spheres = spheres;
        this.lights = lights;
    }

    private Color phong(Vector3 hitPoint, Sphere sphere, Vector3 normal){
       float r = 0, g = 0, b = 0;

        for (Light light : lights) {
            float intensities = 0;
            Vector3 toLight = light.position.subtract(hitPoint);
            toLight.normalize();
            float diffuse = Math.max(0, normal.dot(toLight));

            float ambient = (float) 0.1;


            Vector3 V = hitPoint.getNegative();
            V.normalize();
            Vector3 R = normal.multiply(2*toLight.dot(normal)).subtract(toLight);
            float specular = (float) Math.pow(Math.max(R.dot(V), 0), 32);



            intensities += Math.min(1, (specular/10+diffuse+ambient)*light.intensity);
            r += intensities * light.color.getRed();
            g += intensities * light.color.getGreen();
            b += intensities * light.color.getBlue();

        }
        r = Math.min(r, 255);
        g = Math.min(g, 255);
        b = Math.min(b, 255);


        return new Color((int) r, (int) b, (int) g);
    }

    private Color traceRay(Ray ray, ArrayList<Sphere> spheres, int depth){
        if (depth <= 0)
            return new Color(0, 0, 0);
        float t = -1;
        Sphere s = new Sphere(0, 0, 0, 0);
        for (Sphere sphere : spheres) {
            float t0 = sphere.hit(ray);
            if (t0 >= 0 && (t0 < t || t == -1)){
                t = t0;
                s = sphere;
            }
        }
        if(t >= 0){
            Vector3 hitPoint = ray.origin.add(ray.direction.multiply(t));
            Vector3 normal = hitPoint.subtract(s.center);
            normal.normalize();
            Color phong = phong(hitPoint, s, normal);
            Vector3 toEye = hitPoint.getNegative();
            toEye.normalize();
            Vector3 reflectedVector = normal.multiply(2*toEye.dot(normal)).subtract(toEye);
            reflectedVector.normalize();
            Color reflectedColor = traceRay(new Ray(hitPoint, reflectedVector), spheres, depth-1);
            float r = phong.getRed()*(1-s.reflectance) + reflectedColor.getRed()*(s.reflectance);
            float g = phong.getGreen()*(1-s.reflectance) + reflectedColor.getGreen()*(s.reflectance);
            float b = phong.getBlue()*(1-s.reflectance) + reflectedColor.getBlue()*(s.reflectance);



            r = Math.min(r, 255);
            g = Math.min(g, 255);
            b = Math.min(b, 255);

            return new Color((int)r, (int)g, (int)b);
        }else {
            return new Color(10, 0, 0);
        }
    }

    public int[][] trace(){
        int[][] image = new int[width][height];
        for (int i = -width/2, x = 0; i < width/2; i++, x++){
            for (int j = -height/2, y = 0; j < height/2; j++, y++){

                //Vector3 eyeRay = new Vector3(i, j, projectionPlane);
                Ray eyeRay = new Ray(new Vector3(0, 0, 0), new Vector3(i, j, projectionPlane));
                eyeRay.direction.normalize();
                image[x][y] = traceRay(eyeRay, spheres, 10).getRGB();

            }
        }
        return image;
    }
}
