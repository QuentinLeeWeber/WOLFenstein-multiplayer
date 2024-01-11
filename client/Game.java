import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
//import javax.*;

class Game extends JPanel{
  public Game(){}
  
  private static Game game;
  private BlenderRender renderer = new BlenderRender();
  private JFrame frame = new JFrame();
  private Level level = new Level1();
  private UserInterface UI = new UserInterface();
  private boolean gameRunning = false;
  
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
    MyMouseMotionListener MML = new MyMouseMotionListener();
    MyMouseListener ML = new MyMouseListener();
    frame.addMouseMotionListener(MML);
    frame.addMouseListener(ML);
    renderer.setLevel(level);
    while(true){
      frame.repaint();  
    }
  }
  
  //getGame ist daf�r da das man von �berall aus durch Game.getGame() zuggriff auf die �ffentlichen Attribute von Game hat
  public static Game getGame(){
    return game; 
  }
  
  //Wird immer dann aufgerufen wenn die linke Maustaste einmal gedr�ckt wird
  public void leftClick(){
    System.out.println(mouseX + "  " +  mouseY);
    UI.mouseClicked();  
  } 
  
  // Funktion wird immer dann aufgerufen, wenn gerade eine Taste gedr�ckt wird, diese wird dann als char �begeben
  public void keyPressed(char c){
    
  }
  
  //Funktion wird dann aufgerufen, wenn eine neue Taste gedr�ckt wurde, diese wird dann als char �begeben
  public void keyTyped(char c){
    
  }
  
  //Funktion wird dann a aufgerufen wenn eine neue Taste losgelassen wurde, diese wird dann als char �begeben
  public void keyReleased(char c){
    
  }
  
  //Funktion ist daf�r vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void startGame(){
    gameRunning = true; 
  }
  
  //Funktion ist daf�r vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void stopGame(){
     gameRunning = false;
  }
  
  //Diese Funktion wird jeden Frame aufgrufen, Graphics g ist der Canvas des Fensters des Spieles
  //Beispielhafte Funktionen von Graphics sind: g.drawLine(x1, y2, x2, y2); // g.drawImage(image, x, y, null);
  @Override
    protected void paintComponent(Graphics g){
    if(gameRunning){
      renderer.draw(g); 
    }
    UI.update(g, mouseX, mouseY);
  }
  
  public void example(){
    //System.out.println("example");
  }

  public boolean getRunning() {
    return gameRunning;
  }
}
