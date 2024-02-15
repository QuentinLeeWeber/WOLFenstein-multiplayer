package client;

class Enemy extends Kreatur {
    public Enemy(int x, int y, Level level) {
        super(x, y, level);
        size = 5;
        hitBoxRadius = size/2;
    }

    public void update() {
    }

    public void wurdeGetroffen() {
        System.out.println("Oof");
    }
}
