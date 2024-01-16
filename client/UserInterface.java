import java.awt.*;

class UserInterface {
  
  private int mouseX;
  private int mouseY;
  private int losButtonWidth = 150;
  private int losButtonHeight = 60;
  private int losButtonX = 400 - losButtonWidth/2;
  private int losButtonY = 300 - losButtonHeight/2;
  
  UserInterface(){
    //game = game.getGame();
  }
  
  public void update(Graphics g, int _mouseX, int _mouseY){
    mouseX = _mouseX;
    mouseY = _mouseY;
    if(Game.getGame().getRunning() == false) {
      g.setColor(new Color(255, 0, 0));
      g.fillRect(losButtonX, losButtonY, losButtonWidth, losButtonHeight);
      g.setColor(new Color(0, 0, 0));
      g.drawRect(losButtonX, losButtonY, losButtonWidth, losButtonHeight);
      g.drawString("LOS", losButtonX + losButtonWidth / 2, losButtonY + losButtonHeight / 2);
    }
  }
  
  public void mouseClicked(){
    Game game = Game.getGame();
    if(mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
            && mouseY <= losButtonY + losButtonHeight && game.getRunning() == false) {
      System.out.println("Los Button clicked!");
      game.startGame();
    }
  }
}
