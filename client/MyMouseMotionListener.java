import java.awt.event.*;

public class MyMouseMotionListener implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        //NWT.mouseDragged(e.getX(), e.getY());
        Game.getGame().mouseY = e.getY();
        Game.getGame().mouseX = e.getX();
    }
}
