import java.util.ArrayList;

abstract class Level {
    public ArrayList<Wall> walls = new ArrayList<Wall>();
    public ArrayList<Graphikobjekt> graphikobjekte = new ArrayList<>();

    public Level() {

    }

    public void createWall(float[] a, float[] b){
        walls.add(new Wall(a, b));
    }

    public void createEnemy(int x, int y) {
        graphikobjekte.add(new Enemy(x, y, this, "felix"));
    }

}
