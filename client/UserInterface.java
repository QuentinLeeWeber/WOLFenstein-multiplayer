import javax.imageio.ImageIO;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import java.util.Comparator;
import java.awt.event.KeyEvent;
import java.util.Random;

class UserInterface {
    private int mouseX = 0;
    private int mouseY = 0;
    private int charWidth = 18;

    private boolean nameBoxSelected = true;

    private int bufferSize = 0;
    private int bufferCapacity = 18;
    private char[] input = {'q', 'u', 'e', 'n', 't', 'm', 'a', 'n', '.', 'h', 'o', 'p', 't', 'o', '.', 'o', 'r', 'g'};
    private int inputBoxWidth = 30 + bufferCapacity * charWidth;
    private int inputBoxHeight = 80;
    private int inputBoxX = 800/2 - inputBoxWidth/2;
    private int inputBoxY = 400;

    private int nameLength = 0;
    private int nameMax = 10;
    private char[] name = {'S', 'p', 'i', 'e', 'l', 'e', 'r', ' ', '1', '!'};
    private int nameBoxWidth = 30 + nameMax * charWidth;
    private int nameBoxHeight = 80;
    private int nameBoxX = 800/2 - nameBoxWidth/2;
    private int nameBoxY = 300;

    private int cursorWidth = 2;
    private int cursorHeight = 25;
    private int cursorX = nameBoxX + 16;
    private int cursorY = nameBoxY + nameBoxHeight / 2 - cursorHeight / 2;
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
    private Image frame5;

    public boolean displayLeaderboard = false;

    public UserInterface(){
        try {
            frame1 = ImageIO.read(new File("resources/pistol_frame_1.png"));
            frame2 = ImageIO.read(new File("resources/pistol_frame_2.png"));
            frame5 = ImageIO.read(new File("resources/Fadenkreuz.png"));
        } catch(Exception e){
            System.out.println("failed to load UI-textures");
            e.printStackTrace();
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
            g.setColor(new Color(66, 62, 62));
            g.fillRect(nameBoxX, nameBoxY, nameBoxWidth, nameBoxHeight);
            g.setColor(new Color(127, 127, 127));
            g.fillRect(nameBoxX + 5, nameBoxY + 5, nameBoxWidth - 10, nameBoxHeight - 10);
            g.setColor(new Color(0, 0, 0));
            g.fillRect(cursorX, cursorY, cursorWidth, cursorHeight);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            if (bufferSize == 0 && input.length > 0) {
                g.setColor(new Color(66, 62, 62));
            } else {
                g.setColor(new Color(217, 105, 28));
            }
            g.drawString(new String(input), inputBoxX + 16, inputBoxY + inputBoxHeight/2 + 6);
            if (nameLength == 0 && name.length > 0) {
                g.setColor(new Color(66, 62, 62));
            } else {
                g.setColor(new Color(217, 105, 28));
            }
            g.drawString(new String(name), nameBoxX + 16, nameBoxY + nameBoxHeight/2 + 8);
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
            g.drawImage(frame5, 316, 216, null);
            if(Game.getGame().schiessen){
                g.drawImage(frame2, 450, 370, null);
                //g.drawImage(frame3, 450, 370, null);
                //g.drawImage(frame4, 450, 370, null);
                Game.getGame().schiessen = !Game.getGame().schiessen;
            }
            else{
                g.drawImage(frame1, 450, 370, null);
            }
        }
        if (displayLeaderboard) {
            ArrayList<Kreatur> remotePlayers = new ArrayList<Kreatur>(Game.getGame().remotePlayers.values());
            remotePlayers.add(Game.getGame().player);
            remotePlayers.sort(Comparator.reverseOrder());

            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(Game.windowWidth - 250, 0, 250, 20*remotePlayers.size() + 10);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

            int i = 0;
            for (Kreatur player : remotePlayers) {
                if (player == Game.getGame().player)
                {
                    g.setColor(Color.green);
                }
                g.drawString(player.getName(), Game.windowWidth - 240, 21 + 20*i);
                g.setColor(Color.white);
                g.drawString(String.valueOf(player.killCount), Game.windowWidth - 30, 21 + 20*i);
                i++;
            }
        }
        ticks++;
    }

    public void clear(char[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    public void textInput(char c) {
        if(nameBoxSelected) {
            if (nameLength == 0 && name.length > 0 && c != KeyEvent.VK_TAB) {
                clear(name);
            }
            if (nameLength == nameMax || ticks % 2 == 0 || c < 32 || c > 126) {
                return;
            }
            name[nameLength] = c;
            nameLength++;
            cursorX += charWidth;
        } else {
            if (bufferSize == 0 && input.length > 0 && c != KeyEvent.VK_TAB) {
                clear(input);
            }
            if (bufferSize == bufferCapacity || ticks % 2 == 0 || c < 32 || c > 126) {
                return;
            }
            input[bufferSize] = c;
            bufferSize++;
            cursorX += charWidth;
        }
    }

    public void deleteChar() {
        if(nameBoxSelected) {
            if (nameLength == 0 && name.length > 0) {
                clear(name);
            }
            if (nameLength == 0 || ticks % 2 == 0) {
                return;
            }
            nameLength--;
            name[nameLength] = 0;
            cursorX -= charWidth;
        } else {
            if (bufferSize == 0 && input.length > 0) {
                clear(input);
            }
            if (bufferSize == 0 || ticks % 2 == 0) {
                return;
            }
            bufferSize--;
            input[bufferSize] = 0;
            cursorX -= charWidth;
        }
    }

    public void mouseClicked() {
        Game game = Game.getGame();
        if (mouseX >= losButtonX && mouseX <= losButtonX + losButtonWidth && mouseY >= losButtonY
                && mouseY <= losButtonY + losButtonHeight && !game.getRunning()) {
            game.startGame();
        } else if(mouseX >= nameBoxX && mouseX <= nameBoxX + nameBoxWidth && mouseY >= nameBoxY
                && mouseY <= nameBoxY + nameBoxHeight && !game.getRunning()) {
            nameBoxSelected = true;
            cursorX = nameBoxX + 16 + nameLength * charWidth;
            cursorY = nameBoxY + nameBoxHeight / 2 - cursorHeight / 2;
        } else if(mouseX >= inputBoxX && mouseX <= inputBoxX + inputBoxWidth && mouseY >= inputBoxY
                && mouseY <= inputBoxY + inputBoxHeight && !game.getRunning()) {
            nameBoxSelected = false;
            cursorX = inputBoxX + 16 + bufferSize * charWidth;
            cursorY = inputBoxY + inputBoxHeight / 2 - cursorHeight / 2;
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

    public String getName() {
        return new String(name);
    }

    public String getUserInput() {
        return new String(input);
    }
}
