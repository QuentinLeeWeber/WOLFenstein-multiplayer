import java.awt.Graphics;

public abstract class Pixel{

    public final float distance;
    public float height;

    //entspricht der x coord des Bildschirmes
    public int x;

    public Pixel(int _x, float _distance){
        distance = _distance;
        x = _x;
    }

    public void draw(Graphics g) {

    }
}
