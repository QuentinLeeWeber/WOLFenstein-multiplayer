import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class WallManager {
   
    // Auflistung aller Eckpunkte einer Wand mit anderen Waenden
    public ArrayList<Integer[]> listAllCornerPoints(ArrayList<Wall> walls) {
        ArrayList<Integer[]> cornerPoints = new ArrayList<>();
       
        for (int i = 0; i < walls.size(); i++) {
            Wall currentWall = walls.get(i);
          //  cornerPoints.addAll(currentWall.getCornerPoints());
            for (int j = i + 1; j < walls.size(); j++) {
                Wall otherWall = walls.get(j);
                Point p = Level.calculateWallCorner(currentWall, otherWall);
                if(p == null){
          
                } else {
                  cornerPoints.add(new Integer[]{p.x, p.y});
                } // end of if-else
                
            }
        }
       
        return cornerPoints;
    }
   
    // Erzeugung von Waenden zwischen allen Eckpunkten
    public ArrayList<Wall> createWallsBetweenCornerPoints(ArrayList<Integer[]> cornerPoints) {
        ArrayList<Wall> newWalls = new ArrayList<>();
       
        for (int i = 0; i < cornerPoints.size(); i++) {
            Integer[] point1 = cornerPoints.get(i);
            for (int j = i + 1; j < cornerPoints.size(); j++) {
                Integer[] point2 = cornerPoints.get(j);
                double distance = Math.sqrt(Math.pow(point2[0] - point1[0], 2) + Math.pow(point2[1] - point1[1], 2));
                if (distance > 10) {
                    newWalls.add(new Wall(new int[]{point1[0], point1[1]}, new int[]{point2[0], point2[1]}));
                }
            }
        }
       
        return newWalls;
    }
   
    // loescht Waende, die einen anderen Eckpunkt auf ihrer Strecke haben
    public void removeWallsWithInternalCornerPoints(ArrayList<Wall> walls, ArrayList<Integer[]> cornerPoints) {
        walls.removeIf(wall -> {
            for (Integer[] point : cornerPoints) {
                if (wall.hasInternalPoint(new int[]{point[0], point[1]})) {
                    return true;
                }
            }
            return false;
        });
    }
   
    // loescht Waende, die eine Laenge von 10 haben
   /* public void removeWallsWithLength(List<Wall> walls) {
        walls.removeIf(wall -> wall.getLength() == 10);
    }    */
}
