import java.awt.*;

class UserInterface {
  
  private int mouseX = 0;
  private int mouseY = 0;
  private int losButtonWidth = 150;
  private int losButtonHeight = 60;
  private int losButtonX = 315;
  private int losButtonY = 270;
  
  UserInterface(){
    //game = game.getGame();
  }
  
  public void update(Graphics g, int _mouseX, int _mouseY){
    mouseX = _mouseX;
    mouseY = _mouseY - 32;
    if(Game.getGame().getRunning() == false) {
      g.setColor(new Color(0, 0, 0));
      g.fillRect(0, 0, 800, 600);
      g.setColor(new Color(255, 0, 0));
      g.fillRect(losButtonX, losButtonY, losButtonWidth, losButtonHeight);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
      g.drawString("WOLFenstein 3D", 200, 100);
      g.setColor(new Color(0, 0, 0));
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      g.drawString("LOS", losButtonX + losButtonWidth / 2 - 18, losButtonY + losButtonHeight / 2 + 8);
      g.setColor(new Color(66, 62, 62));
      g.drawRect(losButtonX, losButtonY, losButtonWidth, losButtonHeight);
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
