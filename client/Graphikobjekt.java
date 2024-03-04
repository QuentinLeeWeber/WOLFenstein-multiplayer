package client;

abstract class Graphikobjekt {

    public int x;
    public int y;
    public String texture;

    public int hitBoxRadius;

    public int size;

    public abstract void update();

    public Graphikobjekt(int _x, int _y, String _texture) {
        texture = _texture;
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }
    public void setX(int _x) {
        x = _x;
    }

    public int getY() {
        return y;
    }
    public void setY(int _y) {
        y = _y;
    }
}
