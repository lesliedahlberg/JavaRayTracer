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
    float radiosityCoeff = 0.5f;

    public RayTracer(int projectionPlane, int width, int height, ArrayList<Sphere> spheres, ArrayList<Light> lights){
        this.projectionPlane = projectionPlane;
        this.width = width;
        this.height = height;
        this.spheres = spheres;
        this.lights = lights;
    }

    private Color diffusePhong(Vector3 hitPoint, Sphere sphere, Vector3 normal){
       float r = 0, g = 0, b = 0;

        for (Light light : lights) {
            Vector3 toLight = new Vector3(hitPoint, light.position);
            toLight.normalize();
            float intensity = Math.max(0, normal.dot(toLight));

            if(!pointInShadow(new Ray(hitPoint, toLight))){
                r += sphere.color.getRed()/255 * light.color.getRed()/255 * intensity * light.intensity;
                g += sphere.color.getGreen()/255 * light.color.getGreen()/255 * intensity * light.intensity;
                b += sphere.color.getBlue()/255 * light.color.getBlue()/255 * intensity * light.intensity;
            }
        }
        r = Math.min(r, 1);
        g = Math.min(g, 1);
        b = Math.min(b, 1);

        return new Color(r, b, g);
    }

    private boolean pointInShadow(Ray ray){
        ray.origin = ray.origin.add(ray.direction.multiply(1));
        for (Sphere sphere : spheres) {
            if (sphere.hit(ray) != -1){
                return false;
            }
        }
        return false;
    }

    private Color traceRay(Ray ray, int depth){
        if (depth <= 0)
            return new Color(0, 0, 0);
        float t = -1;
        Sphere hitSphere = new Sphere(0, 0, 0, 0, Color.BLACK, 1);
        for (Sphere sphere : spheres) {
            float t0 = sphere.hit(ray);
            if (t0 >= 0 && (t0 < t || t == -1)){
                t = t0;
                hitSphere = sphere;
            }
        }
        if(t >= 0){
            Vector3 hitPoint = ray.origin.add(ray.direction.multiply(t));
            Vector3 normal = new Vector3(hitSphere.center, hitPoint);
            normal.normalize();
            Vector3 toEye = hitPoint.getNegative();
            toEye.normalize();
            Vector3 reflectedVector = normal.multiply(2*toEye.dot(normal)).subtract(toEye);
            reflectedVector.normalize();

            Color diffuse = diffusePhong(hitPoint, hitSphere, normal);
            Color specular = traceRay(new Ray(hitPoint.add(normal), reflectedVector), depth-1);

            float red = diffuse.getRed() + specular.getRed();
            float green = diffuse.getGreen() + specular.getGreen();
            float blue = diffuse.getBlue() + specular.getBlue();

            Color opacity = new Color(0, 0, 0);
            if(hitSphere.opacity < 1){
                if(ray.direction.dot(normal)>0){
                    opacity = traceRay(new Ray(hitPoint.add(normal), ray.direction), depth-1);
                }else {
                    opacity = traceRay(new Ray(hitPoint.add(normal.getNegative()), ray.direction), depth-1);
                }

            }

            int r = Math.round(Math.min(red*hitSphere.opacity + opacity.getRed()*(1-hitSphere.opacity), 255));
            int g = Math.round(Math.min(green*hitSphere.opacity + opacity.getGreen()*(1-hitSphere.opacity), 255));
            int b = Math.round(Math.min(blue*hitSphere.opacity + opacity.getBlue()*(1-hitSphere.opacity), 255));

            return new Color(r, g, b);

        }else {
            return new Color(0, 0, 0);
        }
    }

    public int[][] renderImage(){
        int[][] image = new int[width][height];
        for (int i = -width/2, x = 0; i < width/2; i++, x++){
            for (int j = -height/2, y = 0; j < height/2; j++, y++){
                Ray eyeRay = new Ray(new Vector3(0, 0, 0), new Vector3(i, j, projectionPlane));
                eyeRay.direction.normalize();
                image[x][y] = traceRay(eyeRay, 5).getRGB();
            }
        }
        return image;
    }
}
