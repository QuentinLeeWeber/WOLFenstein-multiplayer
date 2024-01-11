import java.awt.*;

class UserInterface {
  
  private int mouseX;
  private int mouseY;
  
  UserInterface(){
    //game = game.getGame();
  }
  
  public void update(Graphics g, int _mouseX, int _mouseY){
    mouseX = _mouseX;
    mouseY = _mouseY;
    g.setColor(new Color(255, 0, 0));
    g.drawRect(0, 0, 10, 10); 
    Game.getGame().example();
  }
  
  public void mouseClicked(){
    Game.getGame().startGame();
  }
}
