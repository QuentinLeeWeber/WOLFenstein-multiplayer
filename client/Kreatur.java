import java.util.ArrayList;

abstract class Kreatur extends Graphikobjekt implements Comparable<Kreatur>{
    private int leben;
    public int angriffsstaerke;

    public float direction = 0;

    public Level level;
    public int killCount = 0;

    public Kreatur(int _x, int _y, Level _level, String texture) {
        super(_x, _y, texture);
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
      moveDirection(speed, direction);
    }

    public abstract void moveHook(int x, int y);

    public void moveTo(int x, int y) {
        setX(x);
        setY(y);
        moveHook(x, y);
    }

    public void moveSideways(int speed){
        moveDirection(speed, direction + 90);
    }

    private void moveDirection(int speed, float moveDirection) {
        int dx = (int) (Math.sin(Math.toRadians(moveDirection))*speed);
        int dy = (int) (-Math.cos(Math.toRadians(moveDirection))*speed);

        ArrayList<Wall> walls = getCollidingWalls(x + dx, y + dy);
        if (walls.size() != 0) {
            if (walls.size() == 1) {
                Wall wall = (Wall)walls.toArray()[0];
                double newDirection = wall.getDirection();
                float collisionAngle = Math.abs(moveDirection - wall.getDirection());
            
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
        moveTo(x + dx, y + dy);
    }

    public void isKiller() {
        killCount++;
    }

    public int getKillCount(){
        return killCount;
    }

    public abstract void wurdeGetroffen(Kreatur damager);

    public ArrayList<Wall> getCollidingWalls(int newX, int newY) {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        for (Wall wall : level.walls) {
            if (Collision.KreaturWallCollision(this, newX, newY, wall)) {
                walls.add(wall);
            }
        }
        return walls;
    }

    public String getName() {
        return name;
    }

    public String setName(String _name) {
        name = _name;
        if (this instanceof Player || this instanceof RemotePlayer) {
            System.out.println("name set: " + name);
        }
        return name;
    }

    @Override
    public int compareTo(Kreatur other) {
        if (this.killCount == other.killCount){
            return 0;
        }
        else if (this.killCount > other.killCount){
            return 1;
        }
        else {
            return -1;
        }
    }
}
