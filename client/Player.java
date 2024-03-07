import java.awt.*;
import commands.Move;

class Player extends Kreatur {

    public int shotWidth = 20;

    public Player(int x, int y, Level level) {
        super(x, y, level, "ignore");
        size = 14;
        hitBoxRadius = size/2;
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

    @Override
    public void moveHook(int x, int y) {
        Game.getGame().remote.sendCommand(new Move(x, y));
    }

    public void shoot(){
        for (Graphikobjekt gr : level.graphikobjekte){
            if (gr.getClass() == Enemy.class || gr.getClass() == RemotePlayer.class){
                Collision.ShotCollision((Kreatur) gr, this, level);
            }
        }
    }
}
