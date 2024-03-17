import java.awt.*;
import commands.Move;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Player extends Kreatur {

    public int shotWidth = 20;

    public Player(Level level) {
        super(0, 0, level, "ignore");
        spawn();
        size = 12;
        hitBoxRadius = size/2;
    }

    public void update() {
    }
    
    public void wurdeGetroffen() {
         Game.getGame().leben -= 0.5;
         if (Game.getGame().leben <= 0) {
            spawn();
            Game.getGame().leben =  Game.getGame().maxLeben;
         }
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

    private void spawn() {
        Random random = new Random();
        int[][] spawnpoints = {{0,0}, {365, -475}, {1010, -240}, {725, -90}, {790, 300}, {230, 250}, {440, -250}, {700, -750}, {850, -750}};
        int spawnId = random.nextInt(spawnpoints.length); 
        
        x = spawnpoints[spawnId][0];
        y = spawnpoints[spawnId][1];
    }
}
