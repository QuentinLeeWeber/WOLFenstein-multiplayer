import java.awt.*;
import java.util.ArrayList;

class BlenderRender {
  public Level level;
  
  public BlenderRender(){

  }
  
  public void draw(Graphics g, Player p){
    
    for(Wall wall : level.walls){
      g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
    }
    for (Graphikobjekt gr : level.graphikobjekte) {
         g.drawOval(gr.x, gr.y, 5, 5);
    }
  
    p.draw(g);
  }
}
