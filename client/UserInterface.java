import javax.imageio.ImageIO;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

class UserInterface {
    private int mouseX = 0;
    private int mouseY = 0;
    private int bufferCapacity = 18;
    private char[] input = new char[bufferCapacity];
    private int charWidth = 18;
    private int inputBoxWidth = 30 + bufferCapacity * charWidth;
    private int inputBoxHeight = 80;
    private int inputBoxX = 800/2 - inputBoxWidth/2;
    private int inputBoxY = 285;
    private int cursorWidth = 2;
    private int cursorHeight = 25;
    private int cursorX = inputBoxX + 16;
    private int cursorY = inputBoxY + inputBoxHeight / 2 - cursorHeight / 2;
    private int bufferSize = 0;
    private int losButtonWidth = 150;
    private int losButtonHeight = 60;
    private int losButtonX = Game.windowWidth / 2 - losButtonWidth / 2;
    private int losButtonY = 210;
    private int losShadowX = losButtonX + 3;
    private int losShadowY = losButtonY + 3;
    private int gesundheitsBalkenX = 0;
    private int gesundheitsBalkenY = 0;
    private int gesundheitsBalkenWidth = 230;
    private int gesundheitsBalkenHeigth = 35;
    private Color red = new Color(255, 0, 0);
    private Color grey = new Color(66, 62, 62);
    private Color buttonColor = red;
    private Color textColor = grey;
    private int ticks = 0;
    private Image frame1;
    private Image frame2;
    private Image frame3;
    private Image frame4;

    public boolean displayLeaderboard = false;


    public UserInterface(){
        try {
            frame1 = ImageIO.read(new File("resources/EPFrame1.png"));
            frame2 = ImageIO.read(new File("resources/EPFrame1.png"));
            frame3 = ImageIO.read(new File("resources/EPFrame1.png"));
            frame4 = ImageIO.read(new File("resources/EPFrame1.png"));
        } catch(Exception e){
            try {
                frame1 = ImageIO.read(new File("client/resources/EPFrame1.png"));
                frame2 = ImageIO.read(new File("client/resources/EPFrame1.png"));
                frame3 = ImageIO.read(new File("client/resources/EPFrame1.png"));
                frame4 = ImageIO.read(new File("client/resources/EPFrame1.png"));
            }
            catch (IOException e1) {
                e.printStackTrace();
            }
        }
    }

    public void update(Graphics g, int _mouseX, int _mouseY) {
        mouseX = _mouseX;
        mouseY = _mouseY - 32;
        if (!Game.getGame().getRunning() && !Game.getGame().getPaused()) {
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
            g.fillRect(inputBoxX, inputBoxY, inputBoxWidth, inputBoxHeight);
            
            g.setColor(new Color(127, 127, 127));
            g.fillRect(inputBoxX + 5, inputBoxY + 5, inputBoxWidth - 10, inputBoxHeight - 10);
            g.setColor(new Color(0, 0, 0));
            g.fillRect(cursorX, cursorY, cursorWidth, cursorHeight);
            g.setColor(new Color(217, 105, 28));
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            g.drawString(new String(input), inputBoxX + 16, cursorY + cursorHeight - 4);
        } else if(!Game.getGame().getRunning() && Game.getGame().getPaused()) {
            g.fillRect(0, 0, 800, 600);
            g.setColor(red);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("Pause", 250, 100);
        } else if(Game.getGame().getRunning()) {
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
            g.drawImage(frame1, 450, 370, null);
        }

        if (displayLeaderboard) {
            ArrayList<Kreatur> remotePlayers = new ArrayList<Kreatur>(Game.getGame().remotePlayers.values());
            remotePlayers.add(Game.getGame().player);
            remotePlayers.sort(Comparator.reverseOrder());

            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(Game.windowWidth - 200, 0, 200, 20*remotePlayers.size() + 10);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

            int i = 0;
            for (Kreatur player : remotePlayers) {
                if (player == Game.getGame().player)
                {
                    g.setColor(Color.green);
                }
                g.drawString(player.name, Game.windowWidth - 190, 21 + 20*i);
                g.setColor(Color.white);
                g.drawString(String.valueOf(player.killCount), Game.windowWidth - 30, 21 + 20*i);
                i++;
            }
        }

        ticks++;
    }

    public void textInput(char c) {
        if (bufferSize == bufferCapacity || ticks % 2 == 0 || c < 32 || c > 126) {
            return;
        }
        input[bufferSize] = c;
        bufferSize++;
        cursorX += charWidth;
    }

    public void deleteChar() {
        if (bufferSize == 0 || ticks % 2 == 0) {
            return;
        }
        bufferSize--;
        input[bufferSize] = 0;
        cursorX -= charWidth;
    }

    public void mouseClicked() {
        Game game = Game.getGame();
        if (mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
                && mouseY <= losButtonY + losButtonHeight && !game.getRunning()) {
            game.startGame();
        }
    }

    public void mouseMoved() {
        Game game = Game.getGame();
        if (mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
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
        if (mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
                && mouseY <= losButtonY + losButtonHeight && !game.getRunning() && losButtonX != losShadowX) {
            losButtonX = losShadowX;
            losButtonY = losShadowY;
        }
    }

    public void mouseReleased() {
        if (losButtonX == losShadowX) {
            losButtonX -= 3;
            losButtonY -= 3;
        }
    }

    public String getUserInput() {
        return new String(input);
    }
}
