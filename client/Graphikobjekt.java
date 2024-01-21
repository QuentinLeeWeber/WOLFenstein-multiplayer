abstract class Graphikobjekt {

    private int x;
    private int y;
    public String texture;
    public BoundingBox boundingBox;

    public abstract void update();

    public Graphikobjekt(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }
    public void setX(int _x) {
        x = _x;
        if (boundingBox != null) {
            boundingBox.x = _x;
        }
    }

    public int getY() {
        return y;
    }
    public void setY(int _y) {
        y = _y;
        if (boundingBox != null) {
            boundingBox.y = _y;
        }
    }
}
