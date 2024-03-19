import java.awt.*;
import commands.Move;

import java.util.ArrayList;
import java.util.Random;

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
   
    public void wurdeGetroffen(Kreatur damager) {
         Game.getGame().leben -= 2.5;
         if (Game.getGame().leben <= 0) {
            damager.isKiller();
            spawn();
            Game.getGame().leben = Game.getGame().maxLeben;
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

    public void spawn() {
        Random random = new Random();
        int[][] spawnpoints = {{792,182}, {858, -17}, {1031, -444}, {894, -816}, {279, -376}, {-88, -81}, {482, 293}};
        int spawnId = random.nextInt(spawnpoints.length); 
        
        x = spawnpoints[spawnId][0];
        y = spawnpoints[spawnId][1];
    }
}
