/*GM2D04.java 
Copyright 2008, R.G.Baldwin
Revised 02/08/08

The name GM2Dnn is an abbreviation for GameMath2Dnn.

See the file named GM2D01.java for a general description 
of this game-math library file. This file is an update of 
GM2D03.

This update added the following new capabilities:

Vector addition - Adds this Vector object to a Vector
object received as an incoming parameter and returns the
sum as a resultant Vector object.

Added a method named getLength that returns the length
of a vector as type double.

Added a method named addVectorToPoint to add a Vector to 
a Point producing a new Point.

Tested using JDK 1.6 under WinXP.
*********************************************************/
import java.awt.geom.*;
import java.awt.*;

public class GM2D04{

  //An object of this class represents a 2D column matrix.
  // An object of this class is the fundamental building
  // block for several of the other classes in the
  // library.
  public static class ColMatrix{
    double[] data = new double[2];

    public ColMatrix(double data0,double data1){
      data[0] = data0;
      data[1] = data1;
    }//end constructor
    //--------------------------------------------------//

    public String toString(){
      return data[0] + "," + data[1];
    }//end overridden toString method
    //--------------------------------------------------//

    public double getData(int index){
      if((index < 0) || (index > 1)){ 
        throw new IndexOutOfBoundsException();
      }else{
        return data[index];
      }//end else
    }//end getData method
    //--------------------------------------------------//

    public void setData(int index,double data){
      if((index < 0) || (index > 1)){ 
        throw new IndexOutOfBoundsException();
      }else{
        this.data[index] = data;
      }//end else
    }//end setData method
    //--------------------------------------------------//

    //This method overrides the equals method inherited
    // from the class named Object. It compares the values
    // stored in two matrices and returns true if the
    // values are equal or almost equal and returns false
    // otherwise. 
    public boolean equals(Object obj){
      if(obj instanceof GM2D04.ColMatrix &&
         Math.abs(((GM2D04.ColMatrix)obj).getData(0) - 
                                 getData(0)) <= 0.00001 &&
         Math.abs(((GM2D04.ColMatrix)obj).getData(1) - 
                                  getData(1)) <= 0.00001){
        return true;
      }else{
        return false;
      }//end else

    }//end overridden equals method
    //--------------------------------------------------//

    //Adds one ColMatrix object to another ColMatrix
    // object, returning a ColMatrix object.
    public GM2D04.ColMatrix add(GM2D04.ColMatrix matrix){
      return new GM2D04.ColMatrix(
                            getData(0)+matrix.getData(0),
                            getData(1)+matrix.getData(1));
    }//end subtract
    //--------------------------------------------------//

    //Subtracts one ColMatrix object from another
    // ColMatrix object, returning a ColMatrix object. The
    // object that is received as an incoming parameter 
    // is subtracted from the object on which the method
    // is called.
    public GM2D04.ColMatrix subtract(
                                 GM2D04.ColMatrix matrix){
      return new GM2D04.ColMatrix(
                            getData(0)-matrix.getData(0),
                            getData(1)-matrix.getData(1));
    }//end subtract
    //--------------------------------------------------//
  }//end class ColMatrix
  //====================================================//

  public static class Point{
    GM2D04.ColMatrix point;

    public Point(GM2D04.ColMatrix point){//constructor
      //Create and save a clone of the ColMatrix object
      // used to define the point to prevent the point
      // from being corrupted by a later change in the
      // values stored in the original ColMatrix object
      // through use of its set method.
      this.point = 
         new ColMatrix(point.getData(0),point.getData(1));
    }//end constructor
    //--------------------------------------------------//

    public String toString(){
      return point.getData(0) + "," + point.getData(1);
    }//end toString
    //--------------------------------------------------//

    public double getData(int index){
      if((index < 0) || (index > 1)){ 
        throw new IndexOutOfBoundsException();
      }else{
        return point.getData(index);
      }//end else
    }//end getData
    //--------------------------------------------------//

    public void setData(int index,double data){
      if((index < 0) || (index > 1)){ 
        throw new IndexOutOfBoundsException();
      }else{
        point.setData(index,data);
      }//end else
    }//end setData
    //--------------------------------------------------//

    //This method draws a small circle around the location
    // of the point on the specified graphics context.
    public void draw(Graphics2D g2D){
      Ellipse2D.Double circle = 
                        new Ellipse2D.Double(getData(0)-3,
                                             getData(1)-3,
                                             6,
                                             6);
      g2D.draw(circle);
    }//end draw
    //--------------------------------------------------//

    //Returns a reference to the ColMatrix object that
    // defines this Point object.
    public GM2D04.ColMatrix getColMatrix(){
      return point;
    }//end getColMatrix
    //--------------------------------------------------//

    //This method overrides the equals method inherited
    // from the class named Object. It compares the values
    // stored in the ColMatrix objects that define two
    // Point objects and returns true if they are equal
    // and false otherwise. 
    public boolean equals(Object obj){
      if(point.equals(((GM2D04.Point)obj).
                                         getColMatrix())){
        return true;
      }else{
        return false;
      }//end else
     
    }//end overridden equals method
    //--------------------------------------------------//

    //Gets a displacement vector from one Point object to
    // a second Point object. The vector points from the
    // object on which the method is called to the object
    // passed as a parameter to the method. Kjell
    // describes this as the distance you would have to
    // walk along the x and then the y axes to get from
    // the first point to the second point.
    public GM2D04.Vector getDisplacementVector(
                                      GM2D04.Point point){
      return new GM2D04.Vector(new GM2D04.ColMatrix(
                            point.getData(0)-getData(0),
                            point.getData(1)-getData(1)));
    }//end getDisplacementVector
    //--------------------------------------------------//

    //Adds a Vector to a Point producing a new Point.
    public GM2D04.Point addVectorToPoint(
                                      GM2D04.Vector vec){
      return new GM2D04.Point(new GM2D04.ColMatrix(
                          getData(0) + vec.getData(0),
                          getData(1) + vec.getData(1)));
    }//end addVectorToPoint
    //--------------------------------------------------//
  }//end class Point
  //====================================================//

  public static class Vector{
    GM2D04.ColMatrix vector;

    public Vector(GM2D04.ColMatrix vector){//constructor
      //Create and save a clone of the ColMatrix object
      // used to define the vector to prevent the vector
      // from being corrupted by a later change in the
      // values stored in the original ColVector object.
      this.vector = new ColMatrix(
                     vector.getData(0),vector.getData(1));
    }//end constructor
    //--------------------------------------------------//

    public String toString(){
      return vector.getData(0) + "," + vector.getData(1);
    }//end toString
    //--------------------------------------------------//

    public double getData(int index){
      if((index < 0) || (index > 1)){
        throw new IndexOutOfBoundsException();
      }else{
        return vector.getData(index);
      }//end else
    }//end getData
    //--------------------------------------------------//

    public void setData(int index,double data){
      if((index < 0) || (index > 1)){ 
        throw new IndexOutOfBoundsException();
      }else{
        vector.setData(index,data);
      }//end else
    }//end setData
    //--------------------------------------------------//

    //This method draws a vector on the specified graphics
    // context, with the tail of the vector located at a
    // specified point, and with a small circle at the
    // head.
    public void draw(Graphics2D g2D,GM2D04.Point tail){
      Line2D.Double line = new Line2D.Double(
                       tail.getData(0),
                       tail.getData(1),
                       tail.getData(0)+vector.getData(0),
                       tail.getData(1)+vector.getData(1));

    //Draw a small circle to identify the head.
      Ellipse2D.Double circle = new Ellipse2D.Double(
                      tail.getData(0)+vector.getData(0)-2,
                      tail.getData(1)+vector.getData(1)-2,
                      4,
                      4);
      g2D.draw(circle);
      g2D.draw(line);
    }//end draw
    //--------------------------------------------------//

    //Returns a reference to the ColMatrix object that
    // defines this Vector object.
    public GM2D04.ColMatrix getColMatrix(){
      return vector;
    }//end getColMatrix
    //--------------------------------------------------//

    //This method overrides the equals method inherited
    // from the class named Object. It compares the values
    // stored in the ColMatrix objects that define two
    // Vector objects and returns true if they are equal
    // and false otherwise. 
    public boolean equals(Object obj){
      if(vector.equals((
                     (GM2D04.Vector)obj).getColMatrix())){
        return true;
      }else{
        return false;
      }//end else
     
    }//end overridden equals method
    //--------------------------------------------------//

    //Adds this vector to a vector received as an incoming
    // parameter and returns the sum as a vector.
    public GM2D04.Vector add(GM2D04.Vector vec){
      return new GM2D04.Vector(new ColMatrix(
                       vec.getData(0)+vector.getData(0),
                       vec.getData(1)+vector.getData(1)));
    }//end add
    //--------------------------------------------------//

    //Returns the length of a Vector object.
    public double getLength(){
      return Math.sqrt(
           getData(0)*getData(0) + getData(1)*getData(1));
    }//end getLength
    //--------------------------------------------------//
  }//end class Vector
  //====================================================//


  //A line is defined by two points. One is called the
  // tail and the other is called the head.
  public static class Line{
    GM2D04.Point[] line = new GM2D04.Point[2];

    public Line(GM2D04.Point tail,GM2D04.Point head){
      //Create and save clones of the points used to
      // define the line to prevent the line from being 
      // corrupted by a later change in the coordinate
      // values of the points.
      this.line[0] = new Point(new GM2D04.ColMatrix(
                        tail.getData(0),tail.getData(1)));
      this.line[1] = new Point(new GM2D04.ColMatrix(
                        head.getData(0),head.getData(1)));
    }//end constructor
    //--------------------------------------------------//

    public String toString(){
      return "Tail = " + line[0].getData(0) + "," 
             + line[0].getData(1) + "nHead = " 
             + line[1].getData(0) + "," 
             + line[1].getData(1);
    }//end toString
    //--------------------------------------------------//

    public GM2D04.Point getTail(){
      return line[0];
    }//end getTail
    //--------------------------------------------------//

    public GM2D04.Point getHead(){
      return line[1];
    }//end getHead
    //--------------------------------------------------//

    public void setTail(GM2D04.Point newPoint){
      //Create and save a clone of the new point to
      // prevent the line from being corrupted by a
      // later change in the coordinate values of the
      // point.
      this.line[0] = new Point(new GM2D04.ColMatrix(
              newPoint.getData(0),newPoint.getData(1)));
    }//end setTail
    //--------------------------------------------------//

    public void setHead(GM2D04.Point newPoint){
      //Create and save a clone of the new point to
      // prevent the line from being corrupted by a
      // later change in the coordinate values of the
      // point.
      this.line[1] = new Point(new GM2D04.ColMatrix(
              newPoint.getData(0),newPoint.getData(1)));
    }//end setHead
    //--------------------------------------------------//

    public void draw(Graphics2D g2D){
      Line2D.Double line = new Line2D.Double(
                                    getTail().getData(0),
                                    getTail().getData(1),
                                    getHead().getData(0),
                                    getHead().getData(1));
      g2D.draw(line);
    }//end draw
    //--------------------------------------------------//
  }//end class Line
  //====================================================//

}//end class GM2D04
//======================================================//
