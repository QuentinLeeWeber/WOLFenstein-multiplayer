import java.awt.*;
import java.util.ArrayList;

class BlenderRender {
  public Level level;
  public ArrayList<Graphikobjekt> graphikobjekte = new ArrayList<Graphikobjekt>();
  
  public BlenderRender(){

  }
  
  public void draw(Graphics g){
    
    for(Wall wall : level.walls){
      g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
    }
    for (Graphikobjekt gr : graphikobjekte) {
         gr.drawOval(g.x, g.y, 5, 5);
    }
  }
}
