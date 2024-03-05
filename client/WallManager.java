import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class WallManager {
   
    // Auflistung aller Eckpunkte einer Wand mit anderen Waenden
    public List<Integer[]> listAllCornerPoints(List<Wall> walls) {
        List<Integer[]> cornerPoints = new ArrayList<>();
       
        for (int i = 0; i < walls.size(); i++) {
            Wall currentWall = walls.get(i);
          //  cornerPoints.addAll(currentWall.getCornerPoints());
            for (int j = i + 1; j < walls.size(); j++) {
                Wall otherWall = walls.get(j);
                Point p = Level.calculateWallCorner(currentWall, otherWall);
                cornerPoints.add(new Integer[]{p.x, p.y});
            }
        }
       
        return cornerPoints;
    }
   
    // Erzeugung von Waenden zwischen allen Eckpunkten
    public List<Wall> createWallsBetweenCornerPoints(List<int[]> cornerPoints) {
        List<Wall> newWalls = new ArrayList<>();
       
        for (int i = 0; i < cornerPoints.size(); i++) {
            int[] point1 = cornerPoints.get(i);
            for (int j = i + 1; j < cornerPoints.size(); j++) {
                int[] point2 = cornerPoints.get(j);
                double distance = Math.sqrt(Math.pow(point2[0] - point1[0], 2) + Math.pow(point2[1] - point1[1], 2));
                if (distance > 10) {
                    newWalls.add(new Wall(point1, point2));
                }
            }
        }
       
        return newWalls;
    }
   
    // loescht Waende, die einen anderen Eckpunkt auf ihrer Strecke haben
    public void removeWallsWithInternalCornerPoints(List<Wall> walls, List<int[]> cornerPoints) {
        walls.removeIf(wall -> {
            for (int[] point : cornerPoints) {
                if (wall.hasInternalPoint(point)) {
                    return true;
                }
            }
            return false;
        });
    }
   
    // loescht Waende, die eine Laenge von 10 haben
    public void removeWallsWithLength(List<Wall> walls) {
        walls.removeIf(wall -> wall.getLength() == 10);
    }
}
