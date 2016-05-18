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

        ArrayList<Shape> shapes = new ArrayList<Shape>();

        shapes.add(new Sphere(0, 0, 2000, 100, Color.WHITE, 1));
        shapes.add(new Plane(new Vector3(0, 1, 0), new Vector3(0, 100, 0), Color.WHITE, 1));



        ArrayList<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3(2000, -2000, 2000), 0.2f, Color.WHITE));
        lights.add(new Light(new Vector3(0, -2000, 0), 0.2f, Color.WHITE));





        RayTracer rayTracer = new RayTracer(projectionPlane, width, height, shapes, lights);

        array = rayTracer.renderImage();
        arrayToJPG(array, path);
    }
}
