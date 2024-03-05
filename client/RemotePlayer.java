class RemotePlayer extends Kreatur {
    public RemotePlayer(Integer x, Integer y, Level level) {
        super(x, y, level, "resources/cursor.png");
        boundingBox = new BoundingBox(this.x, this.y, 5, 5);
    }

    @Override
    public void update() {
    }

    @Override
    public void moveHook(int x, int y) {

    }
}