abstract class Kreatur extends Graphikobjekt {
    private int leben;
    public int angriffsstaerke;

    public int direction = 0;

    public Level level;

    public Kreatur(int x, int y, Level _level) {
        super(x, y);
        level = _level;
    }

    public int getLeben() {
        return leben;
    }

    public void turn(float angle) {
        direction += angle;
        while (direction >= 360) {
            direction -= 360;
        }
        while (direction < 0) {
            direction += 360;
        }
    }

    public void move(int speed) {
        int newX = getX()+(int) (Math.sin(Math.toRadians(direction))*speed);
        int newY = getY()+(int) (-Math.cos(Math.toRadians(direction))*speed);
        if (getCollidingWall(newX, newY) != null) {
            return;
        }
        setX(newX);
        setY(newY);
    }

    public void moveSideways(int speed){
        
    }

    public Wall getCollidingWall(int newX, int newY) {
        BoundingBox bb = new BoundingBox(newX, newY, boundingBox.width, boundingBox.height);
        for (Wall wall : level.walls) {
            if (BoundingBox.isColliding(bb, wall.boundingBox)) {
                return wall;
            }
        }
        return null;
    }
}