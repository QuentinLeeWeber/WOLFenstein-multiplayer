import javax.swing.*;
import java.awt.*;

class Game extends JPanel{
    public Game(){}

    private int fps = 30;

    public static final int stepWidth = 10;
    public static final int turnAngle = 10;

    private static Game game;
    private BlenderRender renderer = new BlenderRender();
    private JFrame frame = new JFrame();
    private Level level = new Level1();
    private UserInterface UI = new UserInterface();
    private boolean gameRunning = false;

    private Player player = new Player(400, 400, level);

    public int mouseX;
    public int mouseY;

    public static void main(String[] args){
        game = new Game();
        game.start();
    }

    // kann eigentlich ignoriert werden
    private void start(){
        System.out.println("start...");
        frame.add(this);
        frame.setSize(800, 600);
        frame.setTitle("WOLFenstein");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        MyKeyEventDispatcher dispatcher = new MyKeyEventDispatcher();
        m.addKeyEventDispatcher(dispatcher);
        renderer.level = level;
        MyMouseMotionListener MML = new MyMouseMotionListener();
        MyMouseListener ML = new MyMouseListener();
        frame.addMouseMotionListener(MML);
        frame.addMouseListener(ML);

        double timeToNextFrame = 0;
        double lastTime = System.nanoTime();
        double time = 0;
        double lastRenderedTime = 0;
        while (true) {
            lastTime = time;
            if (timeToNextFrame <= 0) {
                double renderedTime = System.nanoTime();
                //System.out.println("frameTime:  " + -(lastRenderedTime - renderedTime) / 1000000 + "ms");
                player.update();
                for (Graphikobjekt gr : level.graphikobjekte) {
                    gr.update();
                }
                checkGraphikobjektCollision();
                frame.repaint();
                timeToNextFrame += (1000000000 / (float) fps);
                //System.out.println(lastTime + "   " + System.nanoTime());
                //System.out.println("frameTime:  " + -(lastTime - System.nanoTime()));
                lastRenderedTime = renderedTime;
            }
            time = System.nanoTime();
            double frametime = -(lastTime - time);
            timeToNextFrame -= frametime;
        }
    }

    //getGame ist dafuer da das man von ueberall aus durch Game.getGame() zuggriff auf die oeffentlichen Attribute von Game hat
    public static Game getGame(){
        return game;
    }

    //Wird immer dann aufgerufen wenn die linke Maustaste einmal gedr�ckt wird
    public void leftClick(){
        System.out.println(mouseX + "  " +  mouseY);
        UI.mouseClicked();
    }

    private void checkGraphikobjektCollision() {
        for (Graphikobjekt g : level.graphikobjekte) {
            if (g.boundingBox != null && BoundingBox.isColliding(player.boundingBox, g.boundingBox)) {
                if (g.getClass() == Enemy.class) {
                    player.wurdeGetroffen();
                }
            }
        }
    }

    // Funktion wird immer dann aufgerufen, wenn gerade eine Taste gedrueckt wird, diese wird dann als char uebegeben
    public void keyPressed(char c){
        if (c == 'w') {
            player.move(stepWidth);
        } else if (c == 'e') {
            player.turn(turnAngle);
        } else if (c == 'q') {
            player.turn(-turnAngle);
        } else if (c == 'p') {
            if (gameRunning) {
                game.stopGame();
            }
        }
    }

    //Funktion wird dann aufgerufen, wenn eine neue Taste gedrueckt wurde, diese wird dann als char uebergeben
    public void keyTyped(char c){

    }

    //Funktion wird dann aufgerufen wenn eine neue Taste losgelassen wurde, diese wird dann als char uebergeben
    public void keyReleased(char c){

    }

    public void mouseMoved() {
        UI.mouseMoved();
    }

    //Funktion ist dafuer vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
    public void startGame(){
        gameRunning = true;
    }

    //Funktion ist dafuer vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
    public void stopGame(){
        gameRunning = false;
    }

    //Diese Funktion wird jeden Frame aufgrufen, Graphics g ist der Canvas des Fensters des Spieles
    //Beispielhafte Funktionen von Graphics sind: g.drawLine(x1, y2, x2, y2); // g.drawImage(image, x, y, null);
    @Override
    protected void paintComponent(Graphics g){
        if(gameRunning){
            renderer.draw(g, player);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 600);
        }
        UI.update(g, mouseX, mouseY);
    }

    public boolean getRunning() {
        return gameRunning;
    }
}