package com.lesliedahlberg;

import java.awt.*;

/**
 * Created by lesliedahlberg on 17/05/16.
 */
public class Light {
    Vector3 position;
    float intensity;
    Color color;

    public Light(Vector3 position, float intensity, Color color){
        this.position = position;
        this.intensity = intensity;
        this.color = color;
    }
}
