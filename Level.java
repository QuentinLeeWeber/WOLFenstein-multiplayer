import java.util.ArrayList;

class Level {
  
  public Level() {
    float[] a = {200, 200}; 
    float[] b = {300, 300};
    createWall(a, b);
  }
  
  public ArrayList<Wall> walls = new ArrayList<Wall>();
  
  public void createWall(float[] a, float[] b) {
    Wall wall = new Wall(a, b);
    walls.add(wall);
  }
}
