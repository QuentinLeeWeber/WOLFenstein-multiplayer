import java.awt.*;

class Player extends Kreatur {

    final static int size = 14;

    public Player(int x, int y, Level level) {
        super(x, y, level, "ignore");
        super.boundingBox = new BoundingBox(x, y, size, size);
    }

    public void update() {
    }
    
    public void wurdeGetroffen() {
        Game.getGame().leben -= 0.5;
    }

    public void draw2D(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(direction), getX() + size/2, getY() + size/2);
        g2d.drawPolygon(new int[]{getX(), getX() + size/2, getX() + size}, new int[]{getY() + size, getY(), getY() + size}, 3);
    }
}
