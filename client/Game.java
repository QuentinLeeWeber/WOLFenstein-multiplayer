import javax.swing.*;
import java.awt.*;

class Game extends JPanel{
  public Game(){}
  
  private final int fps = 30;
  private double frameTime;

  public static final int stepWidth = 5;
  public static final int turnAngle = 5;
  public static final int frameHeight = 600;
  public static final int frameWidth = 800;

  public static final int windowHeight = 600;
  public static final int windowWidth = 800;

  private static Game game;
  private BlenderRender renderer = new BlenderRender(true);
  private JFrame frame = new JFrame();
  private Level level = new Level1();
  private UserInterface UI = new UserInterface();

  private boolean gameRunning = false;
  private boolean wPressed = false;
  private boolean qPressed = false;
  private boolean ePressed = false;
  private boolean pPressed = false;
  private boolean sPressed = false;
  private boolean aPressed = false;
  private boolean dPressed = false;

  public Player player = new Player(windowWidth/2, windowHeight/2, level);

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
    while (true) {
      lastTime = time;
      if (timeToNextFrame <= 0) {
        player.update();
        for (Graphikobjekt gr : level.graphikobjekte) {
            gr.update();
        }
        checkGraphikobjektCollision();
        
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
        if (BoundingBox.isColliding(player.boundingBox, gr.boundingBox)) {
            if (gr.getClass() == Enemy.class)
            {
              player.wurdeGetroffen();
            }
        }
    }
}
  
  //Wird immer dann aufgerufen wenn die linke Maustaste einmal gedr�ckt wird
  public void leftClick(){
    System.out.println(mouseX + "  " +  mouseY);
    UI.mouseClicked();  
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
      player.turn(turnAngle);
    }
    if (qPressed) {
      player.turn(-turnAngle);
    }
    if (sPressed) {
      player.move(-stepWidth);
    }
    if(aPressed){
      player.moveSideways(stepWidth);
    }
    if(dPressed){
      player.moveSideways(-stepWidth);
    }
    if (pPressed && gameRunning) {
      game.stopGame();
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
    }
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
    }
  }
  
  public void mouseMoved() {
    UI.mouseMoved();
  }
  
  //Funktion ist daf�r vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void startGame(){
    gameRunning = true; 
  }
  
  //Funktion ist daf�r vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void stopGame(){
    gameRunning = false;
  }

  public Player getPlayer(){
    return player;
  }
  
  //Diese Funktion wird jeden Frame aufgrufen, Graphics g ist der Canvas des Fensters des Spieles
  //Beispielhafte Funktionen von Graphics sind: g.drawLine(x1, y2, x2, y2); // g.drawImage(image, x, y, null);
  @Override
  protected void paintComponent(Graphics g){
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

  public boolean getRunning() {
    return gameRunning;
  }
}
