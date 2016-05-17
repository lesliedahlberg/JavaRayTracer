package com.lesliedahlberg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void arrayToJPG(int[][] array, String path){
        BufferedImage image = new BufferedImage(array.length, array[0].length, BufferedImage.TYPE_INT_RGB);
        File imageFile = new File(path);
        for(int x = 0; x < array.length; x++){
            for (int y = 0; y < array[0].length; y++){
                int value = array[x][y];
                image.setRGB(x, y, value);
            }
        }
        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "/Users/lesliedahlberg/Desktop/image.png";
        int[][] array;
        int projectionPlane = 2000;
        int width = 1920;
        int height = 1080;

        ArrayList<Sphere> spheres = new ArrayList<Sphere>();

        spheres.add(new Sphere(-250, 0, 2000, 250, Color.BLUE, 0.5f));
        spheres.add(new Sphere(250, 0, 2000, 250, Color.GREEN, 0.5f));
        spheres.add(new Sphere(-500, 250, 4000, 1000, Color.RED, 1));

        //spheres.add(new Sphere(0, 0, 2000, 100, Color.BLUE));
        //spheres.add(new Sphere(-1000, 0, 2000, 500, Color.WHITE));
        //spheres.add(new Sphere(1000, 0, 5000, 1000, Color.ORANGE));
        //spheres.add(new Sphere(0, 0, 100000, 100000, Color.yellow));


        ArrayList<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3(1000, -1000, 1000), 1, new Color(255, 255, 255)));




        RayTracer rayTracer = new RayTracer(projectionPlane, width, height, spheres, lights);

        array = rayTracer.renderImage();
        arrayToJPG(array, path);
    }
}
