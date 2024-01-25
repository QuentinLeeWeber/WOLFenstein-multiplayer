import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class MyKeyEventDispatcher implements KeyEventDispatcher {
    public static void update() {}

    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            Game.getGame().keyReleased(e.getKeyChar());
            return false;
        }
        if (e.getID() == KeyEvent.KEY_TYPED) {
            Game.getGame().keyTyped(e.getKeyChar());
        }
        return false;
    }
}
