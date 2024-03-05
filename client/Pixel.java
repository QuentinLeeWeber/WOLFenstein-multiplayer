package client;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Pixel{

    public final float distance;
    public BufferedImage texture;

    //entspricht der x coord des Bildschirmes
    public int x;

    public Pixel(int _x, float _distance, BufferedImage _texture){
        distance = _distance;
        x = _x;
        texture = _texture;
    }

    public void draw(Graphics g) {}
}
