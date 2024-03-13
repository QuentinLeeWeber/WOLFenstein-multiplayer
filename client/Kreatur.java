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

/*
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class Kreatur extends Graphikobjekt {
    private int leben;
    public int angriffsstaerke;
    public int direction = 0;
    public Level level;
    private Vectordata currentVector;
    private boolean reachedIntersection = true;
    private Random random = new Random();

    public Kreatur(int x, int y, Level _level) {
        super(x, y);
        level = _level;
        spawn();
    }

   /*  public void spawnAtRandomIntersection() {
        int randomIndex = random.nextInt(level.vector.size());
        currentVector = level.vector.get(randomIndex);
        setX(currentVector.x);
        setY(currentVector.y);
    } */

    public Point getRandomIntersection(List<Vectordata> vectors) {
        ArrayList<Point> intersections = new ArrayList<>();
        for (int i = 0; i < vectors.size(); i++) {
            for (int j = i + 1; j < vectors.size(); j++) {
                Vectordata vectorA = vectors.get(i);
                Vectordata vectorB = vectors.get(j);
                Point intersection = calculateIntersection(vectorA, vectorB);
                if (intersection != null) {
                    intersections.add(intersection);
                }
            }
        }
        if (!intersections.isEmpty()) {
            Random random = new Random();
            return intersections.get(random.nextInt(intersections.size()));
        } else {
            return null;
        }
    }

    public void spawn() {
        Point randomIntersection = getRandomIntersection(level.vector);
        if (randomIntersection != null) {
            setX(randomIntersection.x);
            setY(randomIntersection.y);
        } else {
            // möglicher Default-Spawnpoint
            setX(300);
            setY(300);
        }
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
        if (reachedIntersection) {
            chooseRandomDirection();
            reachedIntersection = false;
        }

        int newX = getX() + (int) (Math.sin(Math.toRadians(direction)) * speed);
        int newY = getY() + (int) (-Math.cos(Math.toRadians(direction)) * speed);

        if (!isOnCurrentVector(newX, newY)) {
            reachedIntersection = true;
        }

        setX(newX);
        setY(newY);
    }

    private void chooseRandomDirection() {
        // Zufällige Auswahl zwischen aktuellem Vektor und anderem Vektor
        int randomIndex = random.nextInt(2);
        if (randomIndex == 0) {
            // Bewegung entlang des aktuellen Vektors
            direction = calculateDirectionTowardsPlayer(currentVector.x2, currentVector.y2);
        } else {
            // Bewegung entlang zufällig gewählten anderen Vektors
            int randomVectorIndex = random.nextInt(level.vector.size());
            Vectordata randomVector = level.vector.get(randomVectorIndex);
            direction = calculateDirectionTowardsPlayer(randomVector.x2, randomVector.y2);
        }
    }

    private boolean isOnCurrentVector(int x, int y) {
        int minX = Math.min(currentVector.x, currentVector.x2);
        int maxX = Math.max(currentVector.x, currentVector.x2);
        int minY = Math.min(currentVector.y, currentVector.y2);
        int maxY = Math.max(currentVector.y, currentVector.y2);
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    private int calculateDirectionTowardsPlayer(int targetX, int targetY) {
        double angle = Math.toDegrees(Math.atan2(targetY - getY(), targetX - getX()));
        return (int) angle;
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
*/
