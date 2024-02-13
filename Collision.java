import java.lang.Math;

public class Collision {
    static int distancePointToLineSegment(int pointX, int pointY, int lineStartX, int lineStartY, int lineEndX, int lineEndY){
        int A = pointX - lineStartX;
        int B = pointY - lineStartY;
        int C = lineEndX - lineStartX;
        int D = lineEndY - lineStartY;
        
    }

    public static boolean KreaturWallCollision(Kreatur kreatur, int x, int y, Wall wall)
    {
        int distance = distancePointToLineSegment(x, y, wall.a[0], wall.a[1], wall.b[0], wall.b[1]);
        return distance < kreatur.hitBoxRadius;
    }
}
