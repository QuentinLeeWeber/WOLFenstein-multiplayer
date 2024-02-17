import java.lang.Math;

class Wall {
    public int[] a = new int[2];
    public int[] b = new int[2];
    public float[] physicA = new float[2];
    public float[] physicB = new float[2];
    public BoundingBox boundingBox;

    public Wall(int[] _a, int[] _b) {
        a = _a;
        b = _b;
        if(a[0] == b[0]){
            b[0] += 0.0000001f;
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
      
        boundingBox = new BoundingBox(Math.min(a[0], b[0]), Math.min(a[1], b[1]), Math.max(1, Math.abs(b[0] - a[0])), Math.max(1, Math.abs(b[1] - a[1])));
    }
}