import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

abstract class Level {
    public ArrayList<Wall> walls = new ArrayList<Wall>();
    public ArrayList<Graphikobjekt> graphikobjekte = new ArrayList<Graphikobjekt>();
    public ArrayList<Vectordata> vector = new ArrayList<Vectordata>();
    public int wallWidth = 10;

    public Level() {
        createWall(new int[]{0, 0}, new int[]{Game.windowWidth, 0});
        createWall(new int[]{0, 0}, new int[]{0, Game.windowHeight});
        createWall(new int[]{Game.windowWidth-Player.size, 0}, new int[]{Game.windowWidth-Player.size, Game.windowHeight});
        createWall(new int[]{0, Game.windowHeight-Player.size-24}, new int[]{Game.windowWidth, Game.windowHeight-Player.size-24});
    }


    //Vektor-Wand-Generation, Intersection-Generation
    public void createWall(int[] a, int[] b) {
        Wall wall = new Wall(a, b);
        walls.add(wall);    
    }

    public void generateWallsFromVectors() {
        ArrayList<Wall> newWalls = new ArrayList<>();
    
        for (Vectordata vector : this.vector) {
            // Richtung momentaner Vektor
            double direction = Math.atan2(vector.y2 - vector.y, vector.x2 - vector.x);
    
            // Senkrechte Richtung rechter und linker Wand
            double perpendicularDirectionLeft = direction + Math.PI / 2;
            double perpendicularDirectionRight = direction - Math.PI / 2;
    
            // Position rechte linke Wand
            int wallLeftX1 = vector.x + (int) (Math.cos(perpendicularDirectionLeft) * wallWidth);
            int wallLeftY1 = vector.y + (int) (Math.sin(perpendicularDirectionLeft) * wallWidth);
            int wallLeftX2 = vector.x2 + (int) (Math.cos(perpendicularDirectionLeft) * wallWidth);
            int wallLeftY2 = vector.y2 + (int) (Math.sin(perpendicularDirectionLeft) * wallWidth);
    
            int wallRightX1 = vector.x + (int) (Math.cos(perpendicularDirectionRight) * wallWidth);
            int wallRightY1 = vector.y + (int) (Math.sin(perpendicularDirectionRight) * wallWidth);
            int wallRightX2 = vector.x2 + (int) (Math.cos(perpendicularDirectionRight) * wallWidth);
            int wallRightY2 = vector.y2 + (int) (Math.sin(perpendicularDirectionRight) * wallWidth);
    
            // Wände rechts und links vom Vektor
            Wall wallLeft = new Wall(new int[]{wallLeftX1, wallLeftY1}, new int[]{wallLeftX2, wallLeftY2});
            Wall wallRight = new Wall(new int[]{wallRightX1, wallRightY1}, new int[]{wallRightX2, wallRightY2});
    
            // fügt Wände zur Liste hinzu
            newWalls.add(wallLeft);
            newWalls.add(wallRight);
        }
    
        // fügt Wände zur Wand/Oberliste hinzu
        walls.addAll(newWalls);
    }
    private Point calculateIntersection(Vectordata vectorA, Vectordata vectorB) {
      int x1 = vectorA.x;
      int y1 = vectorA.y;
      int x2 = vectorA.x2;
      int y2 = vectorA.y2;
      
      int x3 = vectorB.x;
      int y3 = vectorB.y;
      int x4 = vectorB.x2;
      int y4 = vectorB.y2;
      
      // Berechnung des Schnittpunkts (x, y) mit Line-Line-Intersection-Methode von Wikipedia
    try{
      
      int x = ( (x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4) ) /
              ( (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4) );
      int y = ( (x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4) ) /
              ( (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4) );
      
        // Überprüft, ob Schnittpunkt innerhalb Grenzen der Vektoren liegt
      if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) &&
          x >= Math.min(x3, x4) && x <= Math.max(x3, x4) &&
          y >= Math.min(y1, y2) && y <= Math.max(y1, y2) &&
          y >= Math.min(y3, y4) && y <= Math.max(y3, y4)) {
          return new Point(x, y);
      } else {
          return null;
          
      }
      }catch(Exception e){
        
      }
    return null;
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
  
    public void paint(Graphics graphics){
        for (Vectordata a : vector ) {
          graphics.drawLine(a.x, a.y, a.x2, a.y2);
        }
    }
}
