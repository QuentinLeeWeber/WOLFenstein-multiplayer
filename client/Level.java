import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

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


    //Vektor-Wand-Generation, Intersection-Generation
    public void createWall(int[] a, int[] b) {
        Wall wall = new Wall(a, b);
        walls.add(wall);
    System.out.println("test");
    }

    public void generateWallsFromVectors() {
        ArrayList<Wall> newWalls = new ArrayList<>();
    System.out.println("test  " + vector.size());

        for (int i = 0; i < vector.size(); i++) {
            Vectordata vectorA = vector.get(i);
            for (int j = 0; j < vector.size(); j++) {
                Vectordata vectorB = vector.get(j);

                Point intersection = calculateIntersection(vectorA, vectorB);

                if (intersection != null) {
                    Wall wallLeft = new Wall(new int[]{intersection.x - 5, intersection.y - 5}, new int[]{intersection.x - 5, intersection.y + 5});
                    Wall wallRight = new Wall(new int[]{intersection.x + 5, intersection.y - 5}, new int[]{intersection.x + 5, intersection.y + 5});
                    newWalls.add(wallLeft);
                    newWalls.add(wallRight);
                    System.out.println("test");
                }
            }
        }

        walls.addAll(newWalls);
    }

    private Point calculateIntersection(Vectordata vectorA, Vectordata vectorB) {
    System.out.println("test3");
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
        System.out.println("test2");
          return null;
          
      }
      }catch(Exception e){
        
      }
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
    System.out.println("added vector");
    }
  
    public void paint(Graphics graphics){
        for (Vectordata a : vector ) {
          graphics.drawLine(a.x, a.y, a.x2, a.y2);
        }
    }
}
