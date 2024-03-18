import commands.Hit;
import commands.Kill;

class RemotePlayer extends Kreatur {
    private final int id;


    public RemotePlayer(Integer x, Integer y, Level level, int id) {
        super(x, y, level, "resources/cursor.png");
        this.id = id;
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
    public void wurdeGetroffen(Kreatur damager) {
        Game.getGame().remote.sendCommand(new Hit(id));
    }

    @Override
    public void isKiller() {
        Game.getGame().remote.sendCommand(new Kill(id));
    }
}
