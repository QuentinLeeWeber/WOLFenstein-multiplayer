import java.awt.*;
import commands.Move;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
         Game.getGame().leben -= 20;
         if (Game.getGame().leben <= 0) {
             respawn();
         }
    }

    private void respawn() {
        Game.getGame().leben = 100;
        moveTo(ThreadLocalRandom.current().nextInt(0, Game.windowWidth + 1), ThreadLocalRandom.current().nextInt(0, Game.windowHeight + 1));
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
        Game.getGame().schiessen = !Game.getGame().schiessen;
        ArrayList<Graphikobjekt> grs = new ArrayList<>(level.graphikobjekte);
        grs.addAll(Game.getGame().remotePlayers.values());
        for (Graphikobjekt gr : grs){
            if (gr.getClass() == Enemy.class || gr.getClass() == RemotePlayer.class){
                Collision.ShotCollision((Kreatur) gr, this, level);
            }
        }
    }
}
