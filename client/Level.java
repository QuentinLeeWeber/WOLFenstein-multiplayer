import java.util.ArrayList;

abstract class Level {
    public ArrayList<Wall> walls = new ArrayList<Wall>();
    public ArrayList<Graphikobjekt> graphikobjekte = new ArrayList<Graphikobjekt>();

    public Level() {
        createWall(new int[]{0, 0}, new int[]{Game.windowWidth, 0});
        createWall(new int[]{0, 0}, new int[]{0, Game.windowHeigth});
        createWall(new int[]{Game.windowWidth-Player.size, 0}, new int[]{Game.windowWidth-Player.size, Game.windowHeigth});
        createWall(new int[]{0, Game.windowHeigth-Player.size-24}, new int[]{Game.windowWidth, Game.windowHeigth-Player.size-24});
    }

    public void createWall(int[] a, int[] b) {
        Wall wall = new Wall(a, b);
        walls.add(wall);
    }

    public void createEnemy(int x, int y) {
        graphikobjekte.add(new Enemy(x, y, this));
    }

    public ArrayList<BoundingBox> getBoundingBoxes() {
        ArrayList<BoundingBox> boundingBoxes = new ArrayList<BoundingBox>();
        graphikobjekte.forEach((gr) -> {boundingBoxes.add(gr.boundingBox);});
        walls.forEach((gr) -> {boundingBoxes.add(gr.boundingBox);});
        return boundingBoxes;
    }
}
