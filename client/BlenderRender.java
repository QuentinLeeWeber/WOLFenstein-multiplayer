import java.awt.*;
import javax.*;
import java.util.ArrayList;

class BlenderRender {
  Level level;
  
  public BlenderRender(){

  }
  
  public void draw(Graphics g){
  for(Wall wall : level.walls){
    g.drawLine(wall.a[0], wall.a[1], wall.b[0], wall.b[1]);
  }
  }
  
  public void setLevel(Level _level) {
    level = _level;
  }
}
