package client;

class Enemy extends Kreatur {
    public Enemy(int x, int y, Level level, String texture) {
        super(x, y, level, texture);
        size = 5;
        hitBoxRadius = size/2;
    }

    public void update() {
    }

    public void wurdeGetroffen() {
        System.out.println("Oof");
    }
}
