package com.lesliedahlberg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            ImageIO.write(image, "jpg", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "/Users/lesliedahlberg/Desktop/image.jpg";
        int[][] array;
        int projectionPlane = 2000;
        int width = 2000;
        int height = 2000;

        Sphere sphere = new Sphere(0, 0, 10, 3);
        Vector3 light = new Vector3(5, -5, 5);
        RayTracer rayTracer = new RayTracer(projectionPlane, width, height, sphere, light);
        array = rayTracer.trace();
        arrayToJPG(array, path);
    }
}
