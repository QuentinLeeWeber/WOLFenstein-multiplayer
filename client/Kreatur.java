package client;

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
        int dx = (int) (Math.sin(Math.toRadians(direction))*speed);
        int dy = (int) (-Math.cos(Math.toRadians(direction))*speed);

        Wall wall = getCollidingWall(x + dx, y + dy);
        if (wall != null) {
            double newDirection = wall.getDirection();
            System.out.println(Math.abs(direction - wall.getDirection()));
            System.out.println(newDirection);
            if (Math.abs(direction - wall.getDirection()) > 90) {
                newDirection *= -1;
            }

            dx = (int) (Math.sin(Math.toRadians(newDirection))*speed);
            dy = (int) (-Math.cos(Math.toRadians(newDirection))*speed);
        }
        setX(x + dx);
        setY(y + dy);
    }

    public void moveSideways(int speed){
        
    }

    public abstract void wurdeGetroffen();

    public Wall getCollidingWall(int newX, int newY) {
        for (Wall wall : level.walls) {
            if (Collision.KreaturWallCollision(this, newX, newY, wall)) {
                return wall;
            }
        }
        return null;
    }
}