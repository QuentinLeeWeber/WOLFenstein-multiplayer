import java.lang.Math;

class Wall {
    public int[] a = new int[2];
    public int[] b = new int[2];
    public BoundingBox boundingBox;

    public Wall(int[] _a, int[] _b) {
        a = _a;
        b = _b;
        boundingBox = new BoundingBox(Math.min(a[0], b[0]), Math.min(a[1], b[1]), Math.max(1, Math.abs(b[0] - a[0])), Math.max(1, Math.abs(b[1] - a[1])));
    }
}
