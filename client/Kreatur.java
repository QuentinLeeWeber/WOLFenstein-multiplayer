import java.lang.Math;
abstract class Kreatur extends Graphikobjekt {
    private int leben;
    public int angriffsstaerke;

    public int direction = 0; //nur 0, 90, 180, 270

    public Kreatur(int x, int y) {
        super(x, y);
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

    public void move(float speed) {
        x += calcXSteps(speed);
        y += calcYSteps(speed);
    }
  
  private int calcYSteps(float speed) {
    return (int) (-Math.cos(Math.toRadians(direction))*speed);
  }
  
  private int calcXSteps(float speed) {
      return (int) (Math.sin(Math.toRadians(direction))*speed);
    }
}
