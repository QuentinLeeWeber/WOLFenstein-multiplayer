import java.util.ArrayList;

abstract class Level {
  public ArrayList<Wall> walls = new ArrayList<Wall>();
  
  public void createWall(float[] a, float[] b) {
    Wall wall = new Wall(a, b);
    walls.add(wall);
  }
}
