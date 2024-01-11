import java.awt.*;

class Player extends Kreatur{
  
  public Player(int x, int y) {
    super(x, y);
  }

    public void update() {
      }
    public void wurdeGetroffen() {
      }
  
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
        
    // set the angle of rotation and the center of rotation to the middle of the
    // panel - width and height are equal but I still like to reference them
    // independently (could save debugging time in the future).
    g2d.rotate(Math.toRadians(direction), x, y+2.5);
    g2d.drawPolygon(new int[] {x, x+5, x-5}, new int[] {y, y+5, y+5}, 3);
    
    }
}
