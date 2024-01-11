import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.*;

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
    frame.setSize(1000, 1000);
    frame.setTitle("WOLFenstein");
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
  
  //getGame ist dafür da das man von überall aus durch Game.getGame() zuggriff auf die öffentlichen Attribute von Game hat
  public static Game getGame(){
    return game; 
  }
  
  //Wird immer dann aufgerufen wenn die linke Maustaste einmal gedrückt wird
  public void leftClick(){
    System.out.println(mouseX + "  " +  mouseY);
    UI.mouseClicked();  
  } 
  
  // Funktion wird immer dann aufgerufen, wenn gerade eine Taste gedrückt wird, diese wird dann als char übegeben
  public void keyPressed(char c){
    
  }
  
  //Funktion wird dann aufgerufen, wenn eine neue Taste gedrückt wurde, diese wird dann als char übegeben
  public void keyTyped(char c){
    
  }
  
  //Funktion wird dann a aufgerufen wenn eine neue Taste losgelassen wurde, diese wird dann als char übegeben
  public void keyReleased(char c){
    
  }
  
  //Funktion ist dafür vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
  public void startGame(){
    gameRunning = true; 
  }
  
  //Funktion ist dafür vorgesehen, dass das UI einfluss auf das Spiel nehmen kann
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
}
