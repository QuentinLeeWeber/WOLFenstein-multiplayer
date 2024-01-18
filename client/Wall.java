import java.util.ArrayList;

class Wall {
    public int[] a = new int[2];
    public int[] b = new int[2];
    public BoundingBox boundingBox;

    public Wall(int[] _a, int[] _b) {
        a = _a;
        b = _b;
        boundingBox = new BoundingBox(a[0], b[0], a[1] - a[0], b[1] - b[0]);
    }
}
