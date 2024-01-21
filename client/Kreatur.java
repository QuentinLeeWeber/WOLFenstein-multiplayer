abstract class Kreatur extends Graphikobjekt {
    private int leben;
    public int angriffsstaerke;

    public int direction = 0; //nur 0, 90, 180, 270

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
        switch (direction) {
            case 0:
                setY(getY() - speed);
                break;
            case 90:
                setX(getX() + speed);
                break;
            case 180:
                setY(getY() + speed);
                break;
            case 270:
                setX(getX() - speed);
                break;
            default:
        }
        if (getCollidingWall() != null) {
            move(-1);
        }
    }

    public Wall getCollidingWall() {
        for (Wall wall : level.walls) {
            if (BoundingBox.isColliding(boundingBox, wall.boundingBox)) {
                return wall;
            }
        }
        return null;
    }
}
