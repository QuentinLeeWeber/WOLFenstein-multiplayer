abstract class Kreatur extends Graphikobjekt {
    private int leben;
    public int angriffsstaerke;

    public int direction = 0;

    public Level level;
    public int hitBoxRadius;

    public int size;

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
        if (checkWallCollision(newX, newY)) {
            return;
        }
        setX(newX);
        setY(newY);
    }

    public void moveSideways(int speed){
        
    }

    public boolean checkWallCollision(int newX, int newY) {
        for (Wall wall : level.walls) {
            if (Collision.KreaturWallCollision(this, newX, newY, wall)) {
                return true;
            }
        }
        return false;
    }
}