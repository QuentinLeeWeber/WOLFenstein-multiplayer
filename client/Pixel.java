package client;

import java.awt.*;

public abstract class Pixel{

    public final float distance;
    public Image texture;

    //entspricht der x coord des Bildschirmes
    public int x;

    public Pixel(int _x, float _distance, Image _texture){
        distance = _distance;
        x = _x;
        texture = _texture;
    }

    public void draw(Graphics g) {}
}
