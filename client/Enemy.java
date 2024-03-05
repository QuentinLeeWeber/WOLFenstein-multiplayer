class Enemy extends Kreatur {
    private int size = 5;

    public Enemy(int x, int y, Level level, String texture) {
        super(x, y, level, texture);
        super.boundingBox = new BoundingBox(x, y, size, size);
    }

    @Override
    public void update() {
    }

    @Override
    public void moveHook(int x, int y) {
    }
}
