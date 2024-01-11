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
        switch (direction) {
            case 0:
                y -= speed;
                break;
            case 90:
                x += speed;
                break;
            case 180:
                y += speed;
                break;
            case 270:
                x -= speed;
                break;
            default:

        }
    }
}
