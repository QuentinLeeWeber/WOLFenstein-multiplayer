import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

class Game extends JPanel{
  public Game(){}
  
  private static Game game;
  private BlenderRender renderer = new BlenderRender();
  private JFrame frame = new JFrame();
  
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
    while(true){
      frame.repaint();  
    }
  }
  
  public static Game getGame(){
    return game; 
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
  
  //Diese Funktion wird jeden Frame aufgrufen, Graphics g ist der Canvas des Fensters des Spieles
  //Beispielhafte Funktionen von Graphics sind: g.drawLine(x1, y2, x2, y2); // g.drawImage(image, x, y, null);
  @Override
    protected void paintComponent(Graphics g){
    g.drawLine(0, 0, 100, 100);
    renderer.draw(g);
    //System.out.println("repaint");
    //System.out.println(pressedKey);
  }
}
