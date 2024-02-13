import java.awt.*;

class Player extends Kreatur {


    public Player(int x, int y, Level level) {
        super(x, y, level);
        size = 14;
        hitBoxRadius = size;
    }

    public void update() {
    }
    
    public void wurdeGetroffen() {
        System.out.println("Ouch! :(");
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(direction), getX() + size/2, getY() + size/2);
        g2d.drawPolygon(new int[]{getX(), getX() + size/2, getX() + size}, new int[]{getY() + size, getY(), getY() + size}, 3);
    }
}
