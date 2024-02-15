package client;

import java.awt.*;

class Player extends Kreatur {

    public int shotWidth = 20;

    public Player(int x, int y, Level level) {
        super(x, y, level);
        size = 14;
        hitBoxRadius = size/2;
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

    public void shoot(){
        for (Graphikobjekt gr : level.graphikobjekte){
            if (gr.getClass() == Enemy.class){
                System.out.println("test");
                Collision.EnemyShotCollision((Enemy) gr, this, level);
            }
        }
    }
}
