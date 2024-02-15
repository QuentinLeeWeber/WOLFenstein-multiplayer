package client;

import java.awt.event.*;

public class MyMouseMotionListener implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Game.getGame().mouseY = e.getY();
        Game.getGame().mouseX = e.getX();
        Game.getGame().mouseMoved();
    }
}