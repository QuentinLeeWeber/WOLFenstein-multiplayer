import java.awt.*;
import java.util.ArrayList;

class BlenderRender {
  Level level;
  
  public BlenderRender(){

  }
  
  public void draw(Graphics g, Player p){
    for(Wall wall : level.walls){
      g.drawLine((int) wall.a[0], (int) wall.a[1], (int) wall.b[0], (int) wall.b[1]);
    }
    
    p.draw(g);
  }
  
  public void setLevel(Level _level) {
    level = _level;
  }
}
