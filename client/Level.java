import java.util.ArrayList;

abstract class Level {
    public ArrayList<Wall> walls = new ArrayList<Wall>();
    public ArrayList<Graphikobjekt> graphikobjekte = new ArrayList<Graphikobjekt>();

    public void createWall(int[] a, int[] b) {
        Wall wall = new Wall(a, b);
        walls.add(wall);
    }

    public void createEnemy(int x, int y) {
        graphikobjekte.add(new Enemy(x, y));
    }
}
