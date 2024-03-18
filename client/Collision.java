public class Collision {
    static double distancePointToLineSegment(float pointX, float pointY, float lineStartX, float lineStartY, float lineEndX, float lineEndY){

        float dx = lineEndX - lineStartX;
        float dy = lineEndY - lineStartY;

        double wallLenght = Math.sqrt(dx*dx + dy*dy);

        float startPointdx =  pointX - lineStartX;
        float startPointdy =  pointY - lineStartY;
        float dot1 = dx * startPointdx + dy * startPointdy;
        double startPointDist = Math.sqrt(startPointdx*startPointdx + startPointdy*startPointdy);
        if (dot1/(wallLenght*startPointDist) < 0) { //wahr, wenn der Winkel größer als 90 Grad ist
            dx = lineStartX - pointX;
            dy = lineStartY - pointY;
            return Math.sqrt(dx*dx + dy*dy);
        }

        float endPointdx =  pointX - lineEndX;
        float endPointdy =  pointY - lineEndY;
        float dot2 = -dx * endPointdx - dy * endPointdy;
        double endPointDist = Math.sqrt(endPointdx*endPointdx + endPointdy*endPointdy);
        if (dot2/(wallLenght*endPointDist) < 0) { //wahr, wenn der Winkel größer als 90 Grad ist
            dx = lineEndX - pointX;
            dy = lineEndY - pointY;
            return Math.sqrt(dx*dx + dy*dy);
        }

        float zaehler = Math.abs(dx * (lineStartY - pointY) - (lineStartX - pointX) * dy);
        return zaehler/wallLenght;
    }

    public static boolean KreaturWallCollision(Kreatur kreatur, float x, float y, Wall wall)
    {
        double distance = distancePointToLineSegment(x, y, wall.a[0], wall.a[1], wall.b[0], wall.b[1]);
        return distance < kreatur.hitBoxRadius;
    }

    public static void ShotCollision(Kreatur kreatur, Player player, Level level)
    {
        float maxShotDistance = Game.getGame().getWidth();
        float shotEndX = player.x-(float) (Math.sin(Math.toRadians(-player.direction))*maxShotDistance);
        float shotEndY = player.y-(float) (Math.cos(Math.toRadians(-player.direction))*maxShotDistance);
        
        for (Wall wall : level.walls) {
            if (checkLineSegmentCollision(kreatur.x, kreatur.y, player.x, player.y, wall.a[0], wall.a[1], wall.b[0], wall.b[1])) {
                return;
            }
        }

        double distance = distancePointToLineSegment(kreatur.x, kreatur.y, player.x, player.y, shotEndX, shotEndY);
        if (distance < player.shotWidth) {
            kreatur.wurdeGetroffen(player);
        }
    }

    private static boolean checkLineSegmentCollision(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
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
        float dx = a.x - b.x;
        float dy = a.y - b.y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        return dist < a.hitBoxRadius + b.hitBoxRadius;
    }

    private static float getSlope(float x1, float y1, float x2, float y2){
        if (x1 == x2) {
            return Float.MAX_VALUE;
        }
        return (y2 - y1)/(x2-x1);
    }

    private static float getOffset(float x1, float y1, float slope) {
        return y1 - x1*slope;
    }
}
