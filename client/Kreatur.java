package client;

import java.util.ArrayList;

abstract class Kreatur extends Graphikobjekt {
    private int leben;
    public int angriffsstaerke;

    public float direction = 0;

    public Level level;

    public Kreatur(int x, int y, Level _level, String texture) {
        super(x, y, texture);
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

        ArrayList<Wall> walls = getCollidingWalls(x + dx, y + dy);
        if (walls.size() != 0) {
            if (walls.size() == 1) {
                Wall wall = (Wall)walls.toArray()[0];
                double newDirection = wall.getDirection();
                int collisionAngle = Math.abs(direction - wall.getDirection());
            
                if (collisionAngle > 90) {
                    newDirection += 180;
                    collisionAngle = 180 - collisionAngle;
                }

                double newSpeed = Math.cos(Math.toRadians(collisionAngle))*speed;

                dx = (int) (Math.sin(Math.toRadians(newDirection))*newSpeed);
                dy = (int) (-Math.cos(Math.toRadians(newDirection))*newSpeed);
            }
            else {
                return;
            }
        }
        setX(x + dx);
        setY(y + dy);
    }

    public void moveSideways(int speed){
        int newX = getX()+(int) (Math.sin(Math.toRadians(direction + 90))*speed);
        int newY = getY()+(int) (-Math.cos(Math.toRadians(direction + 90))*speed);
        if (getCollidingWall(newX, newY) != null) {
            return;
        }
        setX(newX);
        setY(newY);
    }

    public abstract void wurdeGetroffen();

    public ArrayList<Wall> getCollidingWalls(int newX, int newY) {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        for (Wall wall : level.walls) {
            if (Collision.KreaturWallCollision(this, newX, newY, wall)) {
                walls.add(wall);
            }
        }
        return walls;
    }
}