import java.util.ArrayList;

abstract class Level {
    public ArrayList<Wall> walls = new ArrayList<Wall>();
    public ArrayList<Graphikobjekt> graphikobjekte = new ArrayList<Graphikobjekt>();
    public ArrayList<Vectordata> vector = new ArrayList<Vectordata>();

    public Level() {
        createWall(new int[]{0, 0}, new int[]{Game.windowWidth, 0});
        createWall(new int[]{0, 0}, new int[]{0, Game.windowHeight});
        createWall(new int[]{Game.windowWidth-Player.size, 0}, new int[]{Game.windowWidth-Player.size, Game.windowHeight});
        createWall(new int[]{0, Game.windowHeight-Player.size-24}, new int[]{Game.windowWidth, Game.windowHeight-Player.size-24});
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

    public void createVector( int x, int y, int x2, int y2) {
       GM2D04.Vector vecA = new GM2D04.Vector(
                            new GM2D04.ColMatrix(x,y));
       GM2D04.Vector vecB = new GM2D04.Vector(
                            new GM2D04.ColMatrix(x2,y2));
    
       GM2D04.Vector sumOf2 = vecA.add(vecB);
       vector.add (new Vectordata(x, y, x2, y2));
    }
    public void paint(Graphics g){
        for (Vectordata a : vector ) {
          g.drawLine(a.x, a.y, a.x2, a.y2);
        }
    }
}
