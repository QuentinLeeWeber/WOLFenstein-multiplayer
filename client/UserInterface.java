import java.awt.*;

class UserInterface {
    private int mouseX = 0;
    private int mouseY = 0;
    private char[] input = new char[15];
    private int inputBoxX = 248;
    private int inputBoxY = 285;
    private int inputBoxWidth = 300;
    private int inputBoxHeight = 80;
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
    private int char_width = 18;
    private int ticks = 0;
    private Color red = new Color(255, 0, 0);
    private Color grey = new Color(66, 62, 62);
    private Color buttonColor = red;
    private Color textColor = grey;

    public void update(Graphics g, int _mouseX, int _mouseY) {
        mouseX = _mouseX;
        mouseY = _mouseY - 32;
        if (!Game.getGame().getRunning()) {
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
        }
        ticks++;
    }

    public void textInput(char c) {
        if (bufferSize == 15 || ticks % 2 != 0) {
            return;
        }
        input[bufferSize] = c;
        bufferSize++;
        cursorX += char_width;
    }

    public void deleteChar() {
        if (bufferSize == 0 || ticks % 2 != 0) {
            return;
        }
        input[bufferSize - 1] = 0;
        bufferSize--;
        cursorX -= char_width;
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
