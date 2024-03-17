import java.util.ArrayList;

abstract class Level {
    public ArrayList<Wall> walls = new ArrayList<Wall>();
    public ArrayList<Graphikobjekt> graphikobjekte = new ArrayList<Graphikobjekt>();

    public Level() {
        /*createWall(new int[]{0, 0}, new int[]{Game.windowWidth, 0});
        createWall(new int[]{0, 0}, new int[]{0, Game.windowHeight});
        createWall(new int[]{Game.windowWidth, 0}, new int[]{Game.windowWidth, Game.windowHeight});
        createWall(new int[]{0, Game.windowHeight}, new int[]{Game.windowWidth, Game.windowHeight});*/
    }

    public void createWall(int[] a, int[] b) {
        Wall wall = new Wall(a, b);
        walls.add(wall);
    }

    public void createWall(float[] a, float[] b){
        int[] A = {(int) a[0], (int) a[1]};
        int[] B = {(int) b[0], (int) b[1]};
        Wall wall = new Wall(A, B);
        walls.add(wall);
    }

    public void createEnemy(int x, int y) {
        graphikobjekte.add(new Enemy(x, y, this, "felix"));
    }

}
