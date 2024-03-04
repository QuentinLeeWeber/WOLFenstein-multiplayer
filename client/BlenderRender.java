package client;

import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;

class BlenderRender {
    //level und player wird von Game aus übergeben
    public Level level;
    public Player player;

    private boolean renderIn3d;
    private boolean debug = true;
    private final int viewDistance = 1000;
    private final int resolution = 800;
    private final int fov = 70;
    private final float wallHeight = 3;
    private boolean renderBoundingBoxes = true;

    //liste der objecte welche es sich lohnt zu zeichen (optimierung)
    private ArrayList<Wall> minWalls = new ArrayList<Wall>();

    public BlenderRender(boolean _3d) {
        renderIn3d = _3d;
        //renderIn3d = false;
    }

    public void draw(Graphics g) { 
        if(renderIn3d){
            checkMinCollision(g);
            raycast(g);
        } else { 
            for (Wall wall : level.walls) {
                g.setColor(new Color(0, 0, 0));
                g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
            }
            for (Graphikobjekt gr : level.graphikobjekte) {
                g.drawOval(gr.x, gr.y, 5, 5);
            }
            player.draw(g);    
        }
        if(debug){
            g.setColor(new Color(255, 0, 0, 50));
            g.drawRect(player.x - viewDistance / 2, player.y - viewDistance / 2, viewDistance, viewDistance);
            g.setColor(new Color(0, 0, 0));
            player.draw(g);
            for (Wall wall : level.walls) {
                g.setColor(new Color(0, 0, 0));
                g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
            }
            g.setColor(Color.red);
            int maxShotDistance = Game.getGame().getWidth();
            g.drawLine(player.x, player.y, player.x-(int) (Math.sin(Math.toRadians(-player.direction))*maxShotDistance), player.y-(int) (Math.cos(Math.toRadians(-player.direction))*maxShotDistance));
            g.setColor(new Color(0, 0, 0));
        }
        if (renderBoundingBoxes) {
            Color prevColor = g.getColor();
            g.setColor(Color.green);

            g.drawOval(player.x, player.y, player.hitBoxRadius, player.hitBoxRadius);
            g.setColor(prevColor);
        }
    }

    private void raycast(Graphics g){
        for(int i = 0; i < resolution;i++){
            float renderDistance = 0;
            float hitX = 99999;
            float hitY = 99999;
            float hitDistance = 99999;
            Color renderColor = new Color(0, 0, 0);
            for(Wall wall : minWalls){
                float dirDegree = (player.direction - fov / 2) + ((float) fov / (float) resolution) * (i + 1) - 90;
                if(dirDegree % 90 == 0.0f || dirDegree % 270 == 0.0f){
                    dirDegree = dirDegree + 0.0001f;
                }
                float dir = (float) (dirDegree * (Math.PI / 180));
                float mL = 0;float nL = 0;float mR = 0;float nullX = player.x;float nullY = player.y;float nullSchnittX = 0;float X = 0;float Y = 0;
                try {
                    mL = (wall.a[1] - wall.b[1]) / (wall.a[0] - wall.b[0]); 
                } catch (Exception e){ 
                    mL = 1999999999;
                }
                try {
                    mR = (float) (Math.sin(dir) / Math.cos(dir));
                } catch(Exception e){
                    mR = 1999999999;
                }
                nL = -((wall.a[0] - nullX) * mL - (wall.a[1] - nullY));
                nullSchnittX = (nL / (mR - mL));
                X = nullSchnittX + nullX;
                Y = (nullSchnittX * mR + nullY);
                if(X <= wall.a[0] && X >= wall.b[0] || X <= wall.b[0] && X >= wall.a[0]){
                    if(nullSchnittX * mR  >= 0 && Math.sin(dir) >= 0 || nullSchnittX * mR  <= 0 && Math.sin(dir) <= 0){
                        if(Math.sqrt(Math.pow(X - player.x, 2) + Math.pow(Y - player.y, 2)) < hitDistance) {
                            hitDistance = (float) Math.sqrt(Math.pow(X - player.x, 2) + Math.pow(Y - player.y, 2));
                            hitX = X;
                            hitY = Y;
                            renderColor = wall.renderColor;
                        }
                    }
                }     
                renderDistance = (float) (hitDistance * Math.cos(((i - resolution / 2) * ((float) fov / (float) resolution)) * (Math.PI / 180)));
            }
            int drawX = (int) (((float) i) * (float) (Game.frameWidth) / (float) (resolution));
            int drawY = (int) (((float) (Game.frameHeight) / renderDistance) * 2 * wallHeight);
            if(hitDistance <= 1000){
                g.setColor(renderColor);
                g.drawLine(drawX, Game.frameHeight / 2 - drawY, drawX, Game.frameHeight / 2 + drawY);
            }
            if(debug){
                g.setColor(new Color(255, 0, 0));
                g.fillOval((int) hitX - 2, (int) hitY - 2, 4, 4);
                g.setColor(new Color(0, 100, 100));
                g.drawLine((int) hitX, (int) hitY, player.x, player.y);
            }
        }
    }

    //checkt durch eine versimplete collisionsberechnung, ob der raycast sich für das object lohnt 
    private void checkMinCollision(Graphics g){
        minWalls.clear();
        int checkBoxX = player.x - viewDistance / 2;
        int checkBoxY = player.y - viewDistance / 2; 

        for(Wall wall : level.walls){
            if(debug){
                g.setColor(new Color(200, 200, 200));
                g.fillRect((int) wall.physicA[0], (int) wall.physicA[1], (int) (wall.physicB[0] - wall.physicA[0]), (int) (wall.physicB[1] - wall.physicA[1]));
                g.setColor(new Color(0, 0, 255));
                g.drawOval((int) wall.physicA[0], (int) wall.physicA[1], 4, 4);
                g.drawOval((int) wall.physicB[0], (int) wall.physicB[1], 4, 4);
            }
            if((checkBoxX <= wall.physicB[0] && checkBoxX >= wall.physicA[0] - viewDistance) && (checkBoxY <= wall.physicB[1] && checkBoxY >= wall.physicA[1] - viewDistance)){
                if(debug){
                    g.setColor(new Color(255, 0, 0, 50));
                    g.fillRect((int) wall.physicA[0], (int) wall.physicA[1], (int) (wall.physicB[0] - wall.physicA[0]), (int) (wall.physicB[1] - wall.physicA[1]));
                }
                minWalls.add(wall); 
            }      
        }
    }
}