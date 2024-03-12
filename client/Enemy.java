class Enemy extends Kreatur {
    public Enemy(int x, int y, Level level, String texture) {
        super(x, y, level, texture);
        size = 5;
        hitBoxRadius = size/2;
    }

    @Override
    public void update() {
    }

    @Override
    public void moveHook(int x, int y) {}
  
    public void wurdeGetroffen(Kreatur damager) {
        System.out.println("Oof");
    }
}
