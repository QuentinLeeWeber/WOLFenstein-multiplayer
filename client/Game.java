import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.*;
import java.util.ArrayList;

class Game extends JPanel {
    public Game() {
    }
  
    private int fps = 3;
    
    private static Game game;
    private BlenderRender renderer = new BlenderRender();
    private JFrame frame = new JFrame();
    private Level level = new Level1();
    private UserInterface UI = new UserInterface();
    private boolean gameRunning = true;

    private Player player = new Player(500, 500);

    public int mouseX;
    public int mouseY;

    public static void main(String[] args) {
        game = new Game();
        game.start();
    }

    // kann eigentlich ignoriert werden
    private void start() {
        System.out.println("start...");
        frame.add(this);
        frame.setSize(1000, 1000);
        frame.setTitle("WOLFenstein");
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
          if(timeToNextFrame <= 0){
            double renderedTime = System.nanoTime();  
            System.out.println("frameTime:  " + -(lastRenderedTime - renderedTime) / 1000000 + "ms");
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

    //getGame ist dafür da das man von überall aus durch Game.getGame() zuggriff auf die öffentlichen Attribute von Game hat
    public static Game getGame() {
        return game;
    }

    //Wird immer dann aufgerufen wenn die linke Maustaste einmal gedrückt wird
    public void leftClick() {
        System.out.println(mouseX + "  " + mouseY);
        UI.mouseClicked();
    }

    // Funktion wird immer dann aufgerufen, wenn gerade eine Taste gedrückt wird, diese wird dann als char übegeben
    public void keyPressed(char c) {
        if (c == 'w') {
            player.move(10);
        } else if (c == 'e') {
            player.turn(90);
        } else if (c == 'q') {
            player.turn(-90);
        }
    }

    //Funktion wird dann aufgerufen, wenn eine neue Taste gedrückt wurde, diese wird dann als char übegeben
    public void keyTyped(char c) {

    }

    //Funktion wird dann a aufgerufen wenn eine neue Taste losgelassen wurde, diese wird dann als char übegeben
    public void keyReleased(char c) {

    }

    //Funktion ist dafür vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
    public void startGame() {
        gameRunning = true;
    }

    //Funktion ist dafür vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
    public void stopGame() {
        gameRunning = false;
    }

    //Diese Funktion wird jeden Frame aufgrufen, Graphics g ist der Canvas des Fensters des Spieles
    //Beispielhafte Funktionen von Graphics sind: g.drawLine(x1, y2, x2, y2); // g.drawImage(image, x, y, null);
    @Override
    protected void paintComponent(Graphics g) {
    for (int i = 0;i < 1000000 ;i++ ) {
      for (int a = 0;a < 100008 ;a++ ) {
        
          int thomas = i;
          
      }
    }
        if (gameRunning) {
            renderer.draw(g, player);
        }
        UI.update(g, mouseX, mouseY);
    }

    public void example() {
        //System.out.println("example");
    }
}
