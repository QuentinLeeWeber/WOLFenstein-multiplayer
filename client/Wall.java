import java.awt.*;
import java.util.Random;
import java.lang.Math;

class Wall {
    public int[] a = new int[2];
    public int[] b = new int[2];
    public float[] physicA = new float[2];
    public float[] physicB = new float[2];
 
    //damit die wände abhänig von ihrer ausrichtung zur kamera eine andere Farbe haben können, für bessere sichtbarkeit
    public Color renderColor = new Color(0, 0, 0);

    public Wall(int[] _a, int[] _b) {
        a = _a;
        b = _b;
        if(a[0] == b[0]){
            b[0] += 1;
        }
        if(a[0] <= b[0]){
            physicA[0] = a[0];
            physicB[0] = b[0];
        } else {
            physicA[0] = b[0];
            physicB[0] = a[0];
        }
        if(a[1] <= b[1]){
            physicA[1] = a[1];
            physicB[1] = b[1];
        } else {
            physicA[1] = b[1];
            physicB[1] = a[1];
        }    
        Random rand = new Random();
        float R = rand.nextFloat();
        float G = rand.nextFloat();
        float B = rand.nextFloat();
        renderColor = new Color(R, G, B);
    }

    public int getDirection() {
        float dx = b[0] - a[0];
        float dy = b[1] - a[1];

        return (int)Math.toDegrees(Math.atan(dy/dx)) + 90;
    }
}