import java.awt.Color;
import java.io.Serializable;

/**
 * @author Russell Brown
 * @since March 30, 2016
 * 
 * Holds point values and performs various mathimatical operations.
 * 
 */

public class Point implements Serializable, Comparable <Point>{
  
  /**
   * Player field of view. 
   */
  
  private double fov = 1;
  int pixelsPerUnitLength = 500;
  /**
   * X point position. 
   */
  private double x;
  /**
   * Y point position.
   */
  private double y;
  /**
   * Z point position. 
   */
  private double z;
  
  /**
   * Rectangle the point is associated with. 
   */
  private Rectangle rec;
  /**
   * Pixel's color. 
   */
  private Color color;
  
  /**
     * One of points constructors, used to store various values associated with a point.
     * 
     * @param xx stores point's x position. 
     * @param yy stores point's y position. 
     * @param zz stores point's z position. 
     * 
     * @return has no return type because its a constructor.
     * 
     */
  
  public Point(Point p){
	  /*this constructor is used for avoiding weird alias stuff*/
	  x= p.getX();
	  y= p.getY();
	  z= p.getZ();
	  rec= p.getRectangle();
	  color= p.getColor();
  }
  
  public Point(double xx, double yy, double zz){
    
    x = xx;
    y = yy;
    z = zz;
    
  }
  
  /**
     * One of points constructors, used to store various values associated with a point.
     * 
     * @param xx stores point's x position. 
     * @param yy stores point's y position. 
     * 
     * @return has no return type because its a constructor.
     * 
     */
  
  public Point(double xx, double yy){
    
    x = xx;
    y = yy;
   // z = 1;
    
  }
  
  /**
     * One of points constructors, used to store various values associated with a point.
     * 
     * @param xx stores point's x position. 
     * @param yy stores point's y position. 
     * @param zz stores point's z position. 
     * @param col stores point's color position. 
     * 
     * @return has no return type because its a constructor.
     * 
     */
  
  public Point(double xx, double yy, double zz, Color col){
    
    x = xx;
    y = yy;
    z = zz;
    color = col;
    
  }
  
  /**
     * One of points constructors, used to store various values associated with a point.
     * 
     * @param xx stores point's x position. 
     * @param yy stores point's y position. 
     * @param zz stores point's z position. 
     * @param col stores point's color position. 
     * @param r stores rectangle the point will be a part of (used for shooting the other player -> is the point an enemy or a wall?). 
     * 
     * @return has no return type because its a constructor.
     * 
     */
  
  public Point(double xx, double yy, double zz, Color col, Rectangle r){
    
    x = xx;
    y = yy;
    z = zz;
    color = col;
    rec = r;
    
  }

  /**
     * One of points constructors, used to store various values associated with a point.
     * 
     * @param xx stores point's x position. 
     * @param yy stores point's y position. 
     * @param col stores point's color position. 
     * 
     * @return has no return type because its a constructor.
     * 
     */
  
  public Point(double xx, double yy, Color col){
    
    x = xx;
    y = yy;
   // z = 1;
    color = col;
    
  }
  
  /**
     * Returns a points x value.
     * 
     * @return returns double value which is the players x location.
     * 
     */
  
  public double getX(){
    return x;
  }
  
  /**
     * Returns a points y value.
     * 
     * @return returns double value which is the players y location.
     * 
     */
  
  public double getY(){
    return y;
  }
  
  /**
     * Returns a points z value.
     * 
     * @return returns double value which is the players z location.
     * 
     */
  
  public double getZ(){
    return z;
  }
  
  /**
     * Returns the rectangle a point is associated with.
     * 
     * @return returns a rectangle, a custom object we created.
     * 
     */
  
  public Rectangle getRectangle(){
    return rec;
  }
  
  /**
     * sets a points x value.
     * 
     * @return void type because it changes internal variables
     * 
     */
  
  public void setX(double xx){
    x = xx;
  }
  
  /**
     * sets a points y value.
     * 
     * @return void type because it changes internal variables
     * 
     */
  
  public void setY(double yy){
    y = yy;
  }
  
  /**
     * sets a points z value.
     * 
     * @return void type because it changes internal variables
     * 
     */
  
  public void setZ(double zz){
    z = zz;
  }
  
  /**
     * Gets a points color value.
     * 
     * @return returns a color value that is associated with the point. 
     * 
     */
  
  public Color getColor(){
    return color;
  }
  
  /**
     * Returns a 2d point that is the 3d projection of a point onto the screen. 
     * 
     * @return the newly projected 2d point. 
     * 
     */
  
  public Point return2DPoint(){
        
    double scale = fov / (fov + z);
         
    return new Point((MainRun.width/2) + (pixelsPerUnitLength * (x * scale)), (MainRun.height/2) - (pixelsPerUnitLength * (y * scale)), z);
  }
  
  /**
     * Divides a point by a scalar. 
     * 
     * @param divNum is the scalar to divide the point by.
     * 
     * @return the newly divided point. 
     * 
     */
  
  public Point div(double divNum){
    return new Point(x/divNum, y/divNum, z/divNum);
  }
  
  /**
     * Divides a 2d point by a scalar. 
     * 
     * @param divNum is the scalar to divide the point by.
     * 
     * @return the newly divided point. 
     * 
     */
  
  public Point divNoZ(double divNum){
    return new Point(x/divNum, y/divNum, z);
  }
  
  /**
     * Multiplies a point by a scalar. 
     * 
     * @param mulNum is the scalar to multiply the point by.
     * 
     * @return the newly multiplied point. 
     * 
     */
  
  public Point mul(double mulNum){
    return new Point(x * mulNum, y * mulNum, z * mulNum);
  }
  
  /**
     * Multiplies a 2d point by a scalar. 
     * 
     * @param mulNum is the scalar to multiply the point by.
     * 
     * @return the newly multiplied point. 
     * 
     */
  
  public Point mulNoZ(double mulNum){
    return new Point(x * mulNum, y * mulNum, z);
  }
  
  /**
     * Adds two points. 
     * 
     * @param p2 is the second point being added.
     * 
     * @return the newly added point. 
     * 
     */
  
  public Point add(Point p2){
    return new Point(x + p2.getX(), y + p2.getY(), z + p2.getZ()); 
  }
  
  /**
     * Adds two 2d points. 
     * 
     * @param p2 is the second point being added.
     * 
     * @return the newly added point. 
     * 
     */
  
  public Point addNoZ(Point p2){
    return new Point(x + p2.getX(), y + p2.getY(), z); 
  }
  
  /**
     * Subtracts two points. 
     * 
     * @param p2 is the second point being subtracted.
     * 
     * @return the newly subtracted point. 
     * 
     */
  
  public Point sub(Point p2){
    return new Point(x - p2.getX(), y - p2.getY(), z - p2.getZ());
  }
  
  /**
     * Subtracts two 2d points. 
     * 
     * @param p2 is the second point being subtracted.
     * 
     * @return the newly subtracted point. 
     * 
     */
  
  public Point subNoZ(Point p2){
    return new Point(x - p2.getX(), y - p2.getY(), z);
  }

  /**
     * Rotates a point around the y axis.
     * 
     * @param angle to rotate by.
     * 
     * @return void because the method transforms the point. 
     * 
     */
  
  public void rotateY(double theta){
    
    double sin = Math.sin(theta);
    double cos = Math.cos(theta);
    
    double zOriginal= z;
    
    z = (z * cos) - (x * sin);
    x = (zOriginal * sin) + (x * cos);
   
  }
  
  public void rotateX(double theta){
	  double sin = Math.sin(theta);
	  double cos = Math.cos(theta);
	  
	  double yOriginal = y;
	  
	  y = yOriginal*cos - z*sin;
	  z = yOriginal*sin + z*cos;
  }
  
  public void rotateZ(double theta){
	  double sin = Math.sin(theta);
	  double cos = Math.cos(theta);
	  
	  double xOriginal = x;

	  x = xOriginal*cos - y*sin;
	  y = xOriginal*sin + y*cos;
  }
  
  /**
     * Crosses 2 points. 
     * 
     * @param p2 is the second point being crossed. 
     * 
     * @return the newly crossed point. 
     * 
     */

  public Point cross(Point p2){
    return new Point((y * p2.getZ()) - (z * p2.getY()), (z * p2.getX()) - (x * p2.getZ()), (x * p2.getY()) - (y * p2.getX()));
  }
  
  /**
     * Makes a vector a length of 1 AKA unit. 
     * 
     * @return the unit point. 
     * 
     */
  
  public Point makeUnitLength(){
      return this.mul(1/this.lengthWithZ());
  }
  
  /**
     * Dotsses 2 points. 
     * 
     * @param p2 is the second point being dotted. 
     * 
     * @return the dotted scalar. 
     * 
     */
  
  public double dot(Point p2){
      return ((x * p2.getX()) + (y * p2.getY()) + (z * p2.getZ()));
  }
  
  /**
     * Returns a vector's 2d length
     * 
     * @return a double, representing the length of the vector. 
     * 
     */
  
  public double length(){
      
      return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  }
  
  /**
     * Returns a vector's 2d length
     * 
     * @return a double, representing the length of the vector. 
     * 
     */
  
  public double lengthWithZ(){
      
      return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
  }
  
  @Override
  public String toString(){
	return "(" + x + ", " + y + ", " + z + ")";
	  
  }

@Override
public int compareTo(Point p) {  //sorts points in clockwise order
	//(x2 − x1)(y2 + y1)...
	 double twiceSignedArea= (p.getX()-x)*(p.getY()+y);
	 return (int)twiceSignedArea;
}
  
  
}