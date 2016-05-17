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

            float ambient = (float) 0;

            Vector3 V = hitPoint.getNegative();
            V.normalize();
            Vector3 R = normal.multiply(2*toLight.dot(normal)).subtract(toLight);
            float specular = (float) Math.pow(Math.max(R.dot(V), 0), 32);

            Vector3 len = light.position.subtract(hitPoint);
            float attenuation = (float) (2000/Math.sqrt(len.x*len.x + len.y*len.y + len.z*len.z));

            if(!inShadow(new Ray(hitPoint, toLight))){
                intensities += Math.max(0, Math.min(1, (specular/10+diffuse+ambient)*light.intensity)) * attenuation;

                r += (intensities * light.color.getRed()/255);
                g += (intensities * light.color.getGreen()/255);
                b += (intensities * light.color.getBlue()/255);
            }
        }
        r = Math.min(r, 1);
        g = Math.min(g, 1);
        b = Math.min(b, 1);

        return new Color(r, b, g);
    }

    private boolean inShadow(Ray ray){
        ray.origin = ray.origin.add(ray.direction.multiply(0.1f));
        for (Sphere sphere : spheres) {
            if (sphere.hit(ray) != -1){
                return true;
            }
        }
        return false;
    }

    private Color traceRay(Ray ray, int depth){
        if (depth <= 0)
            return new Color(0, 0, 0);
        float t = -1;
        Sphere s = new Sphere(0, 0, 0, 0, 0);
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

            Color reflectedColor = traceRay(new Ray(hitPoint, reflectedVector), depth-1);
            //Color reflectedColor = new Color(0, 0, 0);

            float r = phong.getRed()*(1-s.reflectance) + reflectedColor.getRed()*(s.reflectance);
            float g = phong.getGreen()*(1-s.reflectance) + reflectedColor.getGreen()*(s.reflectance);
            float b = phong.getBlue()*(1-s.reflectance) + reflectedColor.getBlue()*(s.reflectance);



            r = Math.min(r, 255);
            g = Math.min(g, 255);
            b = Math.min(b, 255);



            return new Color(Math.round(r), Math.round(g), Math.round(b));

        }else {
            return new Color(0, 0, 0);
        }
    }

    public int[][] trace(){
        int[][] image = new int[width][height];
        for (int i = -width/2, x = 0; i < width/2; i++, x++){
            for (int j = -height/2, y = 0; j < height/2; j++, y++){
                Ray eyeRay = new Ray(new Vector3(0, 0, 0), new Vector3(i, j, projectionPlane));
                eyeRay.direction.normalize();
                image[x][y] = traceRay(eyeRay, 10).getRGB();

            }
        }
        return image;
    }
}
