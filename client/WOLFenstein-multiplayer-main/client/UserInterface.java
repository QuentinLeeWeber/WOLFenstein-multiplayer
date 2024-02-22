import java.awt.*;


class UserInterface { 
    private int mouseX = 0;
    private int mouseY = 0;
    private int losButtonWidth = 150;
    private int losButtonHeight = 60;
    private int losButtonX = 315;
    private int losButtonY = 270;
    private int losShadowX = 318;
    private int losShadowY = 273;
    private Color red = new Color(255, 0, 0);
    private Color grey = new Color(66, 62, 62);
    private Color buttonColor = red;
    private Color textColor = grey;
    private int gesundheitsBalkenWidth = 230;
    private int gesundheitsBalkenHeigth =35;
    private int gesundheitsBalkenX = 0;
    private int gesundheitsBalkenY = 0;

    public void update(Graphics g, int _mouseX, int _mouseY){
        mouseX = _mouseX;     
        mouseY = _mouseY - 32;
        if(!Game.getGame().getRunning()) {
            g.setColor(grey);
            g.fillRect(losShadowX, losShadowY, losButtonWidth, losButtonHeight);
            g.setColor(buttonColor);
            g.fillRect(losButtonX, losButtonY, losButtonWidth, losButtonHeight);
            g.setColor(red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("WOLFenstein 3D", 200, 100);
            g.setColor(textColor);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("LOS", losButtonX + losButtonWidth / 2 - 18, losButtonY + losButtonHeight / 2 + 8);
            g.setColor(new Color(66, 62, 62));
            g.drawRect(losButtonX, losButtonY, losButtonWidth, losButtonHeight);
        }
        if (Game.getGame().getRunning()){
            if (Game.getGame().leben >= 75) {
                g.setColor(new Color(81, 201, 27));
            }
            if (Game.getGame().leben < 75 && Game.getGame().leben >= 50) {
                g.setColor(new Color(255, 242, 0));
            }
            if (Game.getGame().leben < 50 && Game.getGame().leben >= 25) {
                g.setColor(new Color(252, 169, 3));
            }
            if (Game.getGame().leben < 25 && Game.getGame().leben >=0) {
                g.setColor(new Color(215, 18, 18));
            }
                g.fillRect(gesundheitsBalkenX, gesundheitsBalkenY, (int) (gesundheitsBalkenWidth * ((float) (Game.getGame().leben)/100)), gesundheitsBalkenHeigth);
            //if ()
        }
        //if(Game.getGame().gameQuit == true){
         //
        //}
    }

    public void mouseClicked() {
        Game game = Game.getGame();
        if(mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
            && mouseY <= losButtonY + losButtonHeight && !game.getRunning()) {
            game.startGame();
        }
    }

    public void mouseMoved() {
        Game game = Game.getGame();
        if(mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
            && mouseY <= losButtonY + losButtonHeight && !game.getRunning()) {
            buttonColor = grey;
            textColor = red;
        } else {
            buttonColor = red;
            textColor = grey;
        }
    }

    public void mousePressed() {
        Game game = Game.getGame();
        if(mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
            && mouseY <= losButtonY + losButtonHeight && !game.getRunning() && losButtonX != losShadowX) {
            losButtonX = losShadowX;
            losButtonY = losShadowY;
        }
    }

    public void mouseReleased() {
        if(losButtonX == losShadowX) {
            losButtonX -= 3;
            losButtonY -= 3;

        }
    }
}
