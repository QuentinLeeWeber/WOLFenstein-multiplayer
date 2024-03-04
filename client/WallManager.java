import java.util.ArrayList;
import java.util.List;

public class WallManager {
    
    // Auflistung aller Eckpunkte einer Wand mit anderen Wänden
    public List<int[]> listAllCornerPoints(List<Wall> walls) {
        List<int[]> cornerPoints = new ArrayList<>();
        
        for (int i = 0; i < walls.size(); i++) {
            Wall currentWall = walls.get(i);
            cornerPoints.addAll(currentWall.getCornerPoints());
            for (int j = i + 1; j < walls.size(); j++) {
                Wall otherWall = walls.get(j);
                cornerPoints.addAll(currentWall.getCommonCornerPoints(otherWall));
            }
        }
        
        return cornerPoints;
    }
    
    // Erzeugung von Wänden zwischen allen Eckpunkten
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
    
    // löscht Wände, die einen anderen Eckpunkt auf ihrer Strecke haben
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
    
    // löscht Wände, die eine Länge von 10 haben
    public void removeWallsWithLength(List<Wall> walls) {
        walls.removeIf(wall -> wall.getLength() == 10);
    }
}
