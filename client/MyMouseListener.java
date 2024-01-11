import java.awt.event.*;

public class MyMouseListener implements MouseListener{

  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
    if(e.getButton() == 1) {
      Game.getGame().leftClick();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

}
