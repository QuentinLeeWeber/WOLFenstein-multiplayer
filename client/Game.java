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

  public static final int stepWidth = 7;
  public static final float mouseSensitivity = 1;
  public static final int frameHeight = 600;
  public static final int frameWidth = 800;

  public static final int windowHeight = 600;
  public static final int windowWidth = 800;


  private static Game game;
  public BlenderRender renderer = new BlenderRender(true);
  private JFrame frame = new JFrame();
  public Level level = new Level1();
  private UserInterface UI = new UserInterface();
  private boolean gameRunning = false;
  private boolean gamePaused = false;
  private Robot robot;

  final int maxLeben = 100;
  public int leben = maxLeben;

  public boolean schiessen = false;

  private boolean wPressed = false;
  private boolean pPressed = false;
  private boolean sPressed = false;
  private boolean aPressed = false;
  private boolean dPressed = false;
  private int textInput = 0;
  private boolean backspacePressed = false;
  private boolean WindowActive = false;
  private boolean tabPressed = false;

  private BufferedImage cursorImg;
  
  public Player player = new Player(level);
  
  public HashMap<Integer, RemotePlayer> remotePlayers = new HashMap<>();
  public Remote remote = new Remote(new Executor() {
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
        remotePlayers.put(c.sender, new RemotePlayer(((Register) c.command).x, ((Register) c.command).y, level, c.sender));
      } else if (c.command instanceof Unregister) {
        remotePlayers.remove(c.sender);
      } else if (c.command instanceof Users) {
        for (Integer id: ((Users) c.command).users.keySet()) {
          if (id != ownID) {
            Integer[] coords = ((Users) c.command).users.get(id);
            remotePlayers.put(id, new RemotePlayer(coords[0], coords[1], level, id));
          }
        }
      } else if (c.command instanceof Hit) {
        if (((Hit) c.command).id == ownID) {
          player.wurdeGetroffen(remotePlayers.get(c.sender));
        }
      } else if (c.command instanceof Kill) {
        remotePlayers.get(c.sender).killCount++;
      } else if (c.command instanceof Name) {
        remotePlayers.get(c.sender).setName(((Name)c.command).name);
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
    try {
      cursorImg = ImageIO.read(new File("resources/cursor.png"));
    }
    catch (IOException e) {
      cursorImg = ImageIO.read(new File("client/resources/cursor.png"));
    }
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
              player.wurdeGetroffen((Kreatur)gr);
            }
        }
      }
    }

  
  //Wird immer dann aufgerufen wenn die linke Maustaste einmal gedr�ckt wird
  public void leftClick(){
    
  }

  public void mousePressed() {
    //UI.mousePressed();
    lastClickX = mouseX;
    lastClickY = mouseY;
    UI.mouseClicked();
    if (gameRunning) {
      player.shoot();
    }
  }

  public void mouseReleased() {
    UI.mouseReleased();
  }
  
  // Funktion wird immer dann aufgerufen, wenn gerade eine Taste gedr�ckt wird, diese wird dann als char �begeben
  public void handleKeys(){
    final float sqrt2 = 1.41f;
    if (wPressed && gameRunning) {
      float step = stepWidth;
      if (aPressed || dPressed) {
        step /= sqrt2;
      }
      player.move((int)step);
    }
    if (sPressed && gameRunning) {
      float step = stepWidth;
      if (aPressed || dPressed) {
        step /= sqrt2;
      }
      player.move((int)-step);
    }
    if(aPressed && gameRunning){
      float step = stepWidth;
      if (wPressed || sPressed) {
        step /= sqrt2;
      }
      player.moveSideways((int)-step);
    }
    if(dPressed && gameRunning){
      float step = stepWidth;
      if (wPressed || sPressed) {
        step /= sqrt2;
      }
      player.moveSideways((int)step);
    }
    if (pPressed && gameRunning) {
      game.stopGame();
    }
    if (backspacePressed && !gameRunning) {
      UI.deleteChar();
    }
    if (textInput == 0 && !gameRunning) {
      //UI.textInput(typedChar);
    }
    UI.displayLeaderboard = tabPressed;
    textInput++;
  }
  
  //Funktion wird dann aufgerufen, wenn eine neue Taste gedr�ckt wurde, diese wird dann als char �begeben
  public void keyTyped(char c){
    if (c == 'w') {
      wPressed = true;
    } else if (c == 'p') {
      //pPressed = true;
    } else if (c == 's') {
      sPressed = true;
    } else if (c == 'a'){
      aPressed = true;
    } else if (c == 'd'){
      dPressed = true;
    } else if (c == KeyEvent.VK_BACK_SPACE) {
      backspacePressed = true;
    }
    else if (c == KeyEvent.VK_TAB) {
      tabPressed = true;
    }
    textInput = 0;
  }
  
  //Funktion wird dann a aufgerufen wenn eine neue Taste losgelassen wurde, diese wird dann als char �begeben
  public void keyReleased(char c){
    if (c == 'w') {
      wPressed = false;
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
    } else if (c == KeyEvent.VK_TAB) {
      tabPressed = false;
    }
    //textInput = false;
  }

  public void keyPressed(char c){
    UI.textInput(c);
  }
  
  public void mouseMoved() {
    UI.mouseMoved();
  }
  
  //Funktion ist daf�r vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void startGame(){
    player.setName(UI.getName());
    new Thread(() -> {
      try {
        remote.connect(UI.getUserInput());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
    gameRunning = true; 
    BufferedImage blankCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImage, new Point(0, 0), "blank cursor");
    frame.getContentPane().setCursor(blankCursor);
    player.spawn();
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
  

  int frameCounter = 0;
  //Diese Funktion wird jeden Frame aufgrufen, Graphics g ist der Canvas des Fensters des Spieles
  //Beispielhafte Funktionen von Graphics sind: g.drawLine(x1, y2, x2, y2); // g.drawImage(image, x, y, null);
  @Override
  protected void paintComponent(Graphics g){
    frameCounter++;
    if((frameCounter % 30) == 0){
      frameCounter = 0;
      Game.getGame().remote.sendCommand(new Name(player.name));
    }
    
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