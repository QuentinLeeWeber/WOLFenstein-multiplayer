public class Collision {
    static double distancePointToLineSegment(int pointX, int pointY, int lineStartX, int lineStartY, int lineEndX, int lineEndY){

        int dx = lineEndX - lineStartX;
        int dy = lineEndY - lineStartY;

        double wallLenght = Math.sqrt(dx*dx + dy*dy);

        int startPointdx =  pointX - lineStartX;
        int startPointdy =  pointY - lineStartY;
        int dot1 = dx * startPointdx + dy * startPointdy;
        double startPointDist = Math.sqrt(startPointdx*startPointdx + startPointdy*startPointdy);
        if (dot1/(wallLenght*startPointDist) < 0) { //wahr, wenn der Winkel größer als 90 Grad ist
            dx = lineStartX - pointX;
            dy = lineStartY - pointY;
            return Math.sqrt(dx*dx + dy*dy);
        }

        int endPointdx =  pointX - lineEndX;
        int endPointdy =  pointY - lineEndY;
        int dot2 = -dx * endPointdx - dy * endPointdy;
        double endPointDist = Math.sqrt(endPointdx*endPointdx + endPointdy*endPointdy);
        if (dot2/(wallLenght*endPointDist) < 0) { //wahr, wenn der Winkel größer als 90 Grad ist
            dx = lineEndX - pointX;
            dy = lineEndY - pointY;
            return Math.sqrt(dx*dx + dy*dy);
        }

        int zaehler = Math.abs(dx * (lineStartY - pointY) - (lineStartX - pointX) * dy);
        return zaehler/wallLenght;
    }

    public static boolean KreaturWallCollision(Kreatur kreatur, int x, int y, Wall wall)
    {
        double distance = distancePointToLineSegment(x + kreatur.size/2, y + kreatur.size/2, wall.a[0], wall.a[1], wall.b[0], wall.b[1]);
        return distance < kreatur.hitBoxRadius;
    }

    public static void EnemyShotCollision(Enemy enemy, Player player, Level level)
    {
        int maxShotDistance = Game.getGame().getWidth();
        int shotEndX = player.x-(int) (Math.sin(Math.toRadians(-player.direction))*maxShotDistance);
        int shotEndY = player.y-(int) (Math.cos(Math.toRadians(-player.direction))*maxShotDistance);
        
        for (Wall wall : level.walls) {
            if (checkLineSegmentCollision(enemy.x, enemy.y, player.x, player.y, wall.a[0], wall.a[1], wall.b[0], wall.b[1])) {
                return;
            }
        }

        double distance = distancePointToLineSegment(enemy.x, enemy.y, player.x, player.y, shotEndX, shotEndY);
        if (distance < player.shotWidth) {
            enemy.wurdeGetroffen();
        }
    }

    private static boolean checkLineSegmentCollision(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        float slope1 = getSlope(x1, y1, x2, y2);
        float slope2 = getSlope(x3, y3, x4, y4);

        if (slope1 == slope2) {
            return false;
        }

        float offset1 = getOffset(x1, y1, slope1);
        float offset2 = getOffset(x3, y3, slope2);

        float x = (offset2 - offset1)/(slope1 - slope2);
        boolean onSegment1 = false;
        boolean onSegment2 = false;
        if ((x <= x1 && x >= x2)||(x >= x1 && x <= x2)) {
            onSegment1 = true;
        }
        if ((x <= x3 && x >= x4)||(x >= x3 && x <= x4)) {
            onSegment2 = true;
        }
        return onSegment1 && onSegment2;
    }

    public static boolean GraphikobjektCollision(Graphikobjekt a, Graphikobjekt b) {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        return dist < a.hitBoxRadius + b.hitBoxRadius;
    }

    private static float getSlope(int x1, int y1, int x2, int y2){
        if (x1 == x2) {
            return Float.MAX_VALUE;
        }
        return (y2 - y1)/(x2-x1);
    }

    private static float getOffset(int x1, int y1, float slope) {
        return y1 - x1*slope;
    }
}
