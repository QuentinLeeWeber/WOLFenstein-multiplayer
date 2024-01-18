import java.awt.*;
import java.util.ArrayList;

class BlenderRender {
    //level und player wird von Game aus übergeben
    public Level level;
    public Player player;

    private boolean renderIn3d;
    private boolean debug = true;
    private final int viewDistance = 100;
    private final int resolution = 1;

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
        }
    }

    private void raycast(Graphics g){
        for(int i = 0; i < resolution;i++){
            for(Wall wall : minWalls){
                float dir = 0;

                float mL = 0;
      float nL = 0;
      float mR = 0;
      float nullX = player.x;
      float nullY = player.y;
      float nullSchnittX = 0;
      //float nullSchnittY = 0;
      float X = 0;
      float Y = 0;
      try {
        mL = (lines.get(l).y1 - lines.get(l).y2) / (lines.get(l).x1 - lines.get(l).x2); 
      } catch (Exception e){ 
        mL = 1999999999;
      }
      try {
        mR = sin(dir) / cos(dir);
      } catch(Exception e){
        mR = 1999999999;
      }
      nL = -((lines.get(l).x1 - nullX) * mL - (lines.get(l).y1 - nullY));
      nullSchnittX = (nL / (mR - mL));
      //nullSchnittY = nullSchnittX * mR;
      //println("NullschnnittX:  " + nullSchnittX);
      //println("NullschnnittY:  " + nullSchnittY);
      X = nullSchnittX + nullX;
      //println("X:  " + X);
      Y = /*height - */(nullSchnittX * mR + nullY);
      //println("Y:  " + Y);
      //println("mR:  " + mR);
      if(X <= lines.get(l).x1 && X >= lines.get(l).x2 || X <= lines.get(l).x2 && X >= lines.get(l).x1){
        //println(Y);
        if(nullSchnittX * mR  >= 0 && sin(dir) >= 0 || nullSchnittX * mR  <= 0 && sin(dir) <= 0){
          //noStroke();
          //fill(255, 0, 0);
          //ellipse(X, 500 - Y, 6, 6);
          if(hit == null){
            hit = new Point(X, Y);
          } else if(sqrt(pow(X - player.x, 2) + pow(Y - player.y, 2)) < sqrt(pow(hit.x - player.x, 2) + pow(hit.y - player.y, 2))) {
            hit.x = X;
            hit.y = Y;
          }
          //println(hit.x + "   " + hit.y + "   " + x + "   " + y);
            /*if(sqrt(pow(cross.x - nullX, 2) + pow(cross.y - nullX, 2)) > sqrt(pow(X - nullX, 2) + pow(Y - nullX, 2))){
              cross.x = X;
              cross.y = Y;
            //println("yeet");
            
            }*/
        }
      } 
      //println("Ray:   " + mR + "   Line:  " + mL + "    " + nL); 
    }




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