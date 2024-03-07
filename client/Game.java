import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.HashMap;
import connect.*;
import commands.*;

class Game extends JPanel{
  public Game(){}
  
  private final int fps = 30;
  private double frameTime;

  public static final int stepWidth = 5;
  public static final float mouseSensitivity = 1;
  public static final int frameHeight = 600;
  public static final int frameWidth = 800;

  public static final int windowHeight = 600;
  public static final int windowWidth = 800;

  

  private static Game game;
  private BlenderRender renderer = new BlenderRender(false);
  private JFrame frame = new JFrame();
  private Level level = new Level1();
  private UserInterface UI = new UserInterface();
  private boolean gameRunning = false;
  private boolean gamePaused = false;
  private char typedChar;
  private Robot robot;

  public int leben = 100;

  private boolean wPressed = false;
  private boolean qPressed = false;
  private boolean ePressed = false;
  private boolean pPressed = false;
  private boolean sPressed = false;
  private boolean aPressed = false;
  private boolean dPressed = false;
  private boolean textInput = false;
  private boolean backspacePressed = false;
  private boolean WindowActive = false;

  private BufferedImage cursorImg;

  public Player player = new Player(windowWidth/2, windowHeight/2, level);
  
  public HashMap<Integer, RemotePlayer> remotePlayers = new HashMap<>();
  public Remote remote = new Remote("quentman.hopto.org", new Executor() {
    private int ownID = -1;

    @Override
    public void execute(CommandWithSender c) {
      if (ownID < 0) {
        player.setX(((Register) c.command).x);
        player.setY(((Register) c.command).y);
        ownID = c.sender;
        return;
      }
      if (c.command instanceof Move) {
        remotePlayers.get(c.sender).moveTo(((Move) c.command).x, ((Move) c.command).y);
      } else if (c.command instanceof Turn) {
        remotePlayers.get(c.sender).turn(((Turn) c.command).angle);
      } else if (c.command instanceof Register) {
        remotePlayers.put(c.sender, new RemotePlayer(((Register) c.command).x, ((Register) c.command).y, level));
      } else if (c.command instanceof Unregister) {
        remotePlayers.remove(c.sender);
      } else if (c.command instanceof Users) {
        for (Integer id: ((Users) c.command).users.keySet()) {
          if (id != ownID) {
            Integer[] coords = ((Users) c.command).users.get(id);
            remotePlayers.put(id, new RemotePlayer(coords[0], coords[1], level));
          }
        }
      }
    }

    @Override
    public void close() {
      stopGame();
      // TODO back to entry
    }
  });

  public int mouseX;
  public int mouseY;

  public int lastClickX;
  public int lastClickY;
  
  public static void main(String[] args) throws IOException, AWTException, InterruptedException { 
    game = new Game();
    game.start();
  }
  
  // kann eigentlich ignoriert werden
  private void start() throws IOException, AWTException, InterruptedException {
    System.out.println("start...");
    cursorImg = ImageIO.read(new File("resources/cursor.png"));
    robot = new Robot();
    Cursor defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "default cursor 2");
    frame.getContentPane().setCursor(defaultCursor);

    frame.add(this);
    frame.setSize(frameWidth, frameHeight);
    frame.setTitle("WOLFenstein");
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    MyKeyEventDispatcher dispatcher = new MyKeyEventDispatcher();
    m.addKeyEventDispatcher(dispatcher);

    renderer.level = level;
    renderer.player = player;
    MyMouseMotionListener MML = new MyMouseMotionListener();
    MyMouseListener ML = new MyMouseListener();
    frame.addMouseMotionListener(MML);
    frame.addMouseListener(ML);
    frame.setResizable(false);
    double timeToNextFrame = 0;
    double lastTime = 0;
    double time = 0;
    double lastRenderedTime = 0;
    frame.addWindowFocusListener(new WindowAdapter() {
      @Override
      public void windowGainedFocus(WindowEvent e) {
        WindowActive = true;
      }
      @Override
      public void windowLostFocus(WindowEvent e) {
        WindowActive = false;
      }
    });
    frame.toFront();
    
    // start server connection
    try {
      remote.connect();
    } catch (IOException e) {
      e.printStackTrace();
      // TODO return err to user
      return;
    }
    
    while (true) {
      lastTime = time;
      if (timeToNextFrame <= 0) {
        if(gameRunning) {
          player.update();
          for (Graphikobjekt gr : level.graphikobjekte) {
            gr.update();
          }
          checkGraphikobjektCollision();
        }
        double renderedTime = System.nanoTime();
        frameTime = -(lastRenderedTime - renderedTime) / 1000000;
        frame.repaint();
        timeToNextFrame = (1000000000 / (float) fps);
        lastRenderedTime = renderedTime;
      }
      time = System.nanoTime();
      timeToNextFrame -= -(lastTime - time);
    }
  }
  
  //getGame ist daf�r da das man von �berall aus durch Game.getGame() zuggriff auf die �ffentlichen Attribute von Game hat
  public static Game getGame(){
    return game; 
  }

  public void checkGraphikobjektCollision() {
    for (Graphikobjekt gr : level.graphikobjekte) {
        if (Collision.GraphikobjektCollision(player, gr)) {
            if (gr.getClass() == Enemy.class) {
              player.wurdeGetroffen();
            }
        }
      }
    }

  
  //Wird immer dann aufgerufen wenn die linke Maustaste einmal gedr�ckt wird
  public void leftClick(){
    System.out.println(mouseX + "  " +  mouseY);
    lastClickX = mouseX;
    lastClickY = mouseY;
    UI.mouseClicked();
    if (gameRunning) {
      player.shoot();
    }
  }

  public void mousePressed() {
    UI.mousePressed();
  }

  public void mouseReleased() {
    UI.mouseReleased();
  }
  
  // Funktion wird immer dann aufgerufen, wenn gerade eine Taste gedr�ckt wird, diese wird dann als char �begeben
  public void handleKeys(){
    if (wPressed) {
      player.move(stepWidth);
    }
    if (ePressed) {
      
    }
    if (qPressed) {
      gamePaused = !gamePaused;
      if(gamePaused) {
        startGame();
      } else {
        stopGame();
      }
    }
    if (sPressed) {
      player.move(-stepWidth);
    }
    if(aPressed){
      player.moveSideways(-stepWidth);
    }
    if(dPressed){
      player.moveSideways(stepWidth);
    }
    if (pPressed && gameRunning) {
      game.stopGame();
    }
    if (backspacePressed && !gameRunning) {
      UI.deleteChar();
    }
    if (textInput && !gameRunning) {
      UI.textInput(typedChar);
    }
  }
  
  //Funktion wird dann aufgerufen, wenn eine neue Taste gedr�ckt wurde, diese wird dann als char �begeben
  public void keyTyped(char c){
    if (c == 'w') {
      wPressed = true;
    } else if (c == 'e') {
      ePressed = true;
    } else if (c == 'q') {
      qPressed = true;
    } else if (c == 'p') {
      pPressed = true;
    } else if (c == 's') {
      sPressed = true;
    } else if (c == 'a'){
      aPressed = true;
    } else if (c == 'd'){
      dPressed = true;
    } else if (c == KeyEvent.VK_BACK_SPACE) {
      backspacePressed = true;
    }
    textInput = true;
    typedChar = c;
  }
  
  //Funktion wird dann a aufgerufen wenn eine neue Taste losgelassen wurde, diese wird dann als char �begeben
  public void keyReleased(char c){
    if (c == 'w') {
      wPressed = false;
    } else if (c == 'e') {
      ePressed = false;
    } else if (c == 'q') {
      qPressed = false;
    } else if (c == 'p') {
      pPressed = false;
    } else if (c == 's') {
      sPressed = false;
    } else if (c == 'a'){
      aPressed = false;
    } else if (c == 'd'){
      dPressed = false;
    } else if (c == KeyEvent.VK_BACK_SPACE) {
      backspacePressed = false;
    }
    textInput = false;
  }
  
  public void mouseMoved() {
    UI.mouseMoved();
  }
  
  //Funktion ist daf�r vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void startGame(){
    gameRunning = true; 
    BufferedImage blankCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImage, new Point(0, 0), "blank cursor");
    frame.getContentPane().setCursor(blankCursor);
  }
  
  //Funktion ist daf�r vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void stopGame(){
    gameRunning = false;
    Cursor defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "default cursor 2");
    frame.getContentPane().setCursor(defaultCursor);
  }

  public Player getPlayer(){
    return player;
  }
  
  //Diese Funktion wird jeden Frame aufgrufen, Graphics g ist der Canvas des Fensters des Spieles
  //Beispielhafte Funktionen von Graphics sind: g.drawLine(x1, y2, x2, y2); // g.drawImage(image, x, y, null);
  @Override
  protected void paintComponent(Graphics g){
    handleMouse();
    handleKeys();
    if(gameRunning){
      renderer.draw(g);
    } else {
      g.setColor(new Color(0, 0, 0));
      g.fillRect(0, 0, 800, 600);  
    }
    UI.update(g, mouseX, mouseY);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
    g.setColor(new Color(0, 255, 0));
    g.drawString(Float.toString((float) ((int) (frameTime * 10)) /10) + "ms", 723, 13);
  }

  private void handleMouse(){
    float mouseMoved = mouseX - 400;
    player.turn(mouseMoved * mouseSensitivity * 0.03f);
    if(WindowActive && gameRunning){
      robot.mouseMove(frame.getX() + 400, frame.getY() + 300);
    }
  }

  public boolean getRunning() {
    return gameRunning;
  }

  public boolean getPaused() {
    return gamePaused;
  }
}
