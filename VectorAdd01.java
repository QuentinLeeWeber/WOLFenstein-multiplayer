/*VectorAdd01.java
Copyright 2008, R.G.Baldwin
Revised 02/10/08

This program illustrates the addition of two or more
vectors. It also illustrates the Head-to-Tail rule
described by Kjell.

Tested using JDK 1.6 under WinXP.
*********************************************************/
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

//class VectorAdd01{
//  public static void main(String[] args){
//    GUI guiObj = new GUI();
//  }//end main
//}//end controlling class VectorAdd01
//======================================================//

class GUI extends JFrame{
  //Specify the horizontal and vertical size of a JFrame
  // object.
  int hSize = 275;
  int vSize = 200;
  Image osi;//an off-screen image
  int osiWidth;//off-screen image width
  int osiHeight;//off-screen image height
  MyCanvas myCanvas;//a subclass of Canvas 

  GUI(){//constructor
    //Set JFrame size, title, and close operation.
    setSize(hSize,vSize);
    setTitle("Copyright 2008,Baldwin");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     

    //Create a new drawing canvas and add it to the
    // center of the JFrame.
    myCanvas = new MyCanvas();
    this.getContentPane().add(myCanvas);

    //This object must be visible before you can get an
    // off-screen image.  It must also be visible before
    // you can compute the size of the canvas.
    setVisible(true);
    osiWidth = myCanvas.getWidth();
    osiHeight = myCanvas.getHeight();

    //Create an off-screen image and get a graphics
    // context on it.
    osi = createImage(osiWidth,osiHeight);
    Graphics2D g2D = (Graphics2D)(osi.getGraphics());

    //Draw some graphical objects on the off-screen
    // image that represent underlying data objects in
    // 2D space.
    drawOffScreen(g2D);

    //Cause the overridden paint method belonging to
    // myCanvas to be executed.
    myCanvas.repaint();         

  }//end constructor
  //----------------------------------------------------//

  //The purpose of this method is to add some Vector
  // objects and to cause visual manifestations of the raw
  // Vector objects and the resultant Vector objects to be
  // drawn onto an off-screen image.
  void drawOffScreen(Graphics2D g2D){

    //Translate the origin on the off-screen
    // image and draw a pair of orthogonal axes on it.
    setCoordinateFrame(g2D);

    //Define two vectors.
    GM2D04.Vector vecA = new GM2D04.Vector(
                            new GM2D04.ColMatrix(50,100));
    GM2D04.Vector vecB = new GM2D04.Vector(
                             new GM2D04.ColMatrix(75,25));

    //Define a third vector as the sum of the first
    // two vectors defined above.
    GM2D04.Vector sumOf2 = vecA.add(vecB);

    //Draw vecA in RED with its tail at the origin
    g2D.setColor(Color.RED);
    vecA.draw(g2D,new GM2D04.Point(
                              new GM2D04.ColMatrix(0,0)));
                              
    //Draw vecB in GREEN with its tail at the head of vecA
    g2D.setColor(Color.GREEN);
    vecB.draw(g2D,new GM2D04.Point(new GM2D04.ColMatrix(
                       vecA.getData(0),vecA.getData(1))));

    //Draw sumOf2 in MAGENTA with its tail at the origin.
    // The head will coincide with the head of vecB.
    g2D.setColor(Color.MAGENTA);
    sumOf2.draw(g2D,new GM2D04.Point(new GM2D04.ColMatrix(
                       0.0,0.0)));

    //Now define another vector and add it to vecA and
    // vecB.
    GM2D04.Vector vecC = new GM2D04.Vector(
                           new GM2D04.ColMatrix(30,-150));

    //Draw vecD in BLUE with its tail positioned at the
    // sum of vecA and vecB
    g2D.setColor(Color.BLUE);
    vecC.draw(g2D,new GM2D04.Point(new GM2D04.ColMatrix(
                   sumOf2.getData(0),sumOf2.getData(1))));

    //Define a vector as the sum of vecA, vecB, and vecC
    GM2D04.Vector sumOf3 = (vecA.add(vecB)).add(vecC);

    //Draw sumOf3 in BLACK with its tail at the origin.
    g2D.setColor(Color.BLACK);
    sumOf3.draw(g2D,new GM2D04.Point(
                          new GM2D04.ColMatrix(0.0,0.0)));

  }//end drawOffScreen
  //----------------------------------------------------//

  //This method is used to set the origin to a point near
  // the upper-left corner of the off-screen image  and
  // draw orthogonal axes on the off-screen image. There
  // is no intention to perform mathematical operations on
  // the axes, so they are drawn independently of the
  // classes and methods in the game-math library using
  // the simplest method available for drawing a line.
  private void setCoordinateFrame(Graphics2D g2D){
    //Translate the origin.
    g2D.translate(0.2*osiWidth,0.2*osiHeight);

    //Draw new X and Y-axes in default BLACK
    g2D.drawLine(-(int)(0.2*osiWidth),0,
                 (int)(0.8*osiWidth),0);

    g2D.drawLine(0,-(int)(0.2*osiHeight),
                 0,(int)(0.8*osiHeight));

  }//end setCoordinateFrame method
  //====================================================//


  //This is an inner class of the GUI class.
  class MyCanvas extends Canvas{
    //Override the paint() method. This method will be
    // called when the JFrame and the Canvas appear on the
    // screen or when the repaint method is called on the
    // Canvas object.
    //The purpose of this method is to display the
    // off-screen image on the screen.
    public void paint(Graphics g){
      g.drawImage(osi,0,0,this);
    }//end overridden paint()

  }//end inner class MyCanvas
    
}//end class GUI
//======================================================//
