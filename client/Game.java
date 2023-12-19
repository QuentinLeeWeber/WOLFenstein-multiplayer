import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

class Game extends JPanel{
	
	public Game(){
		
	}
	
	
	private BlenderRender renderer = new BlenderRender();
	private JFrame frame = new JFrame();
	
	public static void main(String[] args){
		System.out.println("start...");
		frame.add(this);
        frame.setSize(1000, 1000);
        frame.setTitle("WOLFenstein");
        frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
    protected void paintComponent(Graphics g){
		g.draLine(0, 0, 100, 100);
		renderer.draw(g);
	}
}