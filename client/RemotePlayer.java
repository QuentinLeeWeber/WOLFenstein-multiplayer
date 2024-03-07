class RemotePlayer extends Kreatur {
    public RemotePlayer(Integer x, Integer y, Level level) {
        super(x, y, level, "resources/cursor.png");
        size = 5;
        hitBoxRadius = size/2;
    }

    @Override
    public void update() {
    }

    @Override
    public void moveHook(int x, int y) {

    }
  
    @Override
    public void wurdeGetroffen() {
           // TODO 
    }
}
