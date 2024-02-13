class Enemy extends Kreatur {
    public Enemy(int x, int y, Level level) {
        super(x, y, level);
        size = 5;
        super.boundingBox = new BoundingBox(x, y, size, size);
    }

    public void update() {
    }
}
