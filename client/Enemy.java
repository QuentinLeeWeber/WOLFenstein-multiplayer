class Enemy extends Kreatur {
    private int size = 5;

    public Enemy(int x, int y, Level level) {
        super(x, y, level);
        super.boundingBox = new BoundingBox(x, y, size, size);
    }

    public void update() {
    }
}
