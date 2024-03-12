import java.awt.*;
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;


class Wall {
    public int[] a = new int[2];
    public int[] b = new int[2];
    public float[] physicA = new float[2];
    public float[] physicB = new float[2];
    public BoundingBox boundingBox;
  
    //damit die wände abhänig von ihrer ausrichtung zur kamera eine andere Farbe haben können, für bessere sichtbarkeit
    public Color renderColor = new Color(0, 0, 0);

    public Wall(int[] _a, int[] _b) {
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
        float R = rand.nextFloat();
        float G = rand.nextFloat();
        float B = rand.nextFloat();
        renderColor = new Color(R, G, B);
      
        boundingBox = new BoundingBox(Math.min(a[0], b[0]), Math.min(a[1], b[1]), Math.max(1, Math.abs(b[0] - a[0])), Math.max(1, Math.abs(b[1] - a[1])));
    }
  
  public boolean hasInternalPoint(int[] p) {
    double dist_p_w1 = Math.sqrt(Math.pow(a[0] - p[0], 2) + Math.pow(b[0] - p[1], 2));
    double dist_p_w2 = Math.sqrt(Math.pow(a[1] - p[0], 2) + Math.pow(b[1] - p[1], 2));
    double dist_w1_w2 = Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
    
    return (dist_p_w1 + dist_p_w2) == dist_w1_w2;
    }
}
