package com.lesliedahlberg;

import java.awt.*;

/**
 * Created by lesliedahlberg on 18/05/16.
 */
public abstract class Shape {
    public abstract float Opacity();
    public abstract Color Color();
    public abstract float hit(Ray eyeRay);
    public abstract Vector3 normal(Vector3 hitPoint);
}
