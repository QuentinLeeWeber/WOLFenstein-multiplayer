import java.awt.*;
import java.util.Random;

class Wall {
    public float[] a = new float[2];
    public float[] b = new float[2];
    public float[] physicA = new float[2];
    public float[] physicB = new float[2];

    //damit die wände abhänig von ihrer ausrichtung zur kamera eine andere Farbe haben können, für bessere sichtbarkeit
    public Color renderColor = new Color(0, 0, 0);

    public Wall(float[] _a, float[] _b) {
        a = _a;
        b = _b;
        if(a[0] == b[0]){
            b[0] += 0.001f;
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
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        renderColor = new Color(r, g, b);
    }
}