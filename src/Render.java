import java.awt.Color;
import java.util.Arrays;

/**
 * @author Not just Russell Brown
 * @since April 4, 2016
 * 
 * This is the class that actually makes anything on screen appear.
 * It is what renders all the polygons on screen and textures them. 
 * 
 */

public class Render {
 
  /**
   * The distance at which to stop rendering pixels
   */
  
 public static double renderDistance = 2;
 
 /**
   * Holds a texture's pixels
   */
 
 private Color[] colors;
 /**
   * Texture width
   */
 private int width;
 
 /**
   * Texture height
   */
 
 private int height;
 
 //Various variables used in the rendering process
 protected static double[] zBuffer= new double[MainRun.width*MainRun.height];  //holds all the z values of the points on the screen
 //Stores location of pixel color in reference to a texture
 private int val;
 private Point topVector;
 private double sizeT;
 int county;
 int tempNum;
 int tempYNum;
 double imgX;
 double imgY;
 Point bLeft;
 Point bRight;
 Point tLeft;
 Point tRight;
 
 //Generates a texture
 Texture tex;
 Point origTL;
 Point origTR;
 Point pToDraw;
 Point leftVector;
 double sizeL;
 int rectArea;
 Point rightVector;
 double sizeR;
 
 /**
     * Finds the intersection between 2 lines
     * 
     * @param p1a point one on first line;
     * @param p1b point two on first line;
     * 
     * @param p2a point one on second line;
     * @param p2b point two on second line;
     * @return a point representing the point of intersection. 
     * 
 private double sizeT;
     */
 
 public Point find2DIntersection(Point p1a, Point p1b, Point p2a, Point p2b) {
	 //this method uses crazy matrix stuff
	 double x1 = p1a.getX();
	 double y1 = p1a.getY();
	 double x2 = p1b.getX();
	 double y2 = p1b.getY();
	 double x3 = p2a.getX();
	 double y3 = p2a.getY();
	 double x4 = p2b.getX();
	 double y4 = p2b.getY();

	 double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
	 if ((int)d == 0){
	  return null;
	 }
	 double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
	 double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
	 return new Point(xi,yi);
  
 }
 
 /**
     * Finds the intersection between a line and a plane
     * 
     * @param pa point one on the line;
     * @param pb point two on the line;
     * 
     * @return a point representing the point of intersection between the line and plane. 
     * 
     */
 
 public Point find3DIntersection(Point pa, Point pb) { // takes in two points on the line

  Point m = pa.sub(pb).makeUnitLength(); // direction vector of line the t value in the vector equation of the line

  double t = (renderDistance - pa.getZ()) / m.getZ();
  return new Point(pa.getX() + (t * m.getX()), pa.getY() + (t * m.getY()), renderDistance).return2DPoint();
 }

 /**
     * Renders a given rectangle on screen, after being projected into screen space and clipped. 
     * 
     * @param rectangle is the rectangle to be rendered.
     * 
     * @return void because the bufferedImage is automatically filled. 
     * 
     */
 
 public void resetZBuffer(){
	 double maxDistance= 80000;  //max distance of rendering- far clipping plane
	 Arrays.fill(zBuffer, maxDistance);
 }

 public void renderRectangle(Rectangle rectangle){

    bLeft= rectangle.getBLeft();
    bRight= rectangle.getBRight();
    tLeft= rectangle.getTLeft();
    tRight= rectangle.getTRight();
    tex= rectangle.getTexture();
    
    //Number of times a texture should repeat, starting with 0
    
    double numberOfDivisions= tex.getNumberOfDivisions();
    
    colors = tex.getCol();
    width = tex.getWidth();
    height = tex.getHeight();
    
    //Projects all the points from abstract 3d space to visible 2d space, as pixels
    
    bLeft = bLeft.return2DPoint();
    bRight = bRight.return2DPoint();
    tLeft = tLeft.return2DPoint();
    tRight = tRight.return2DPoint();
    
   
    //Sorts four points properly
    if(bLeft.getY() < tLeft.getY()){
        Point temp = bLeft;
        bLeft = tLeft;
        tLeft = temp;        
    }
    if(bRight.getY() < tRight.getY()){
        Point temp = bRight;
        bRight = tRight;
        tRight = temp;      
    }
    
    if(tLeft.getX() > tRight.getX()){
        Point temp = tLeft; 
        tLeft = tRight;
        tRight = temp;   
    }
    if(bLeft.getX() > bRight.getX()){
        Point temp = bLeft;
        bLeft = bRight;
        bRight = temp;
    }
 
    //clipping
       //complete clipping
       if(tLeft.getZ()<renderDistance && tRight.getZ()<renderDistance && bLeft.getZ()<renderDistance && bRight.getZ() < renderDistance){
         return;
       }
       if((tLeft.getX()<0|| tLeft.getY()<0 || tLeft.getZ()<renderDistance)
         && (tRight.getX()<0 || tRight.getY()<0 || tRight.getZ()<renderDistance)
         && (bLeft.getX()<0 || bLeft.getY()<0 || bLeft.getZ()<renderDistance)
         && (bRight.getX()<0 || bRight.getY()<0 || bRight.getZ()<renderDistance)){
           return;
       }
       if((tLeft.getX()<0|| tLeft.getY()>MainRun.height || tLeft.getZ()<renderDistance)
         && (tRight.getX()<0 || tRight.getY()>MainRun.height || tRight.getZ()<renderDistance)
         && (bLeft.getX()<0 || bLeft.getY()>MainRun.height || bLeft.getZ()<renderDistance)
         && (bRight.getX()<0 || bRight.getY()>MainRun.height || bRight.getZ()<renderDistance)){
           return;
       }
       if((tLeft.getX()>MainRun.width || tLeft.getY()<0 || tLeft.getZ()<renderDistance)
         && (tRight.getX()>MainRun.width  || tRight.getY()<0 || tRight.getZ()<renderDistance)
         && (bLeft.getX()>MainRun.width  || bLeft.getY()<0 || bLeft.getZ()<renderDistance)
         && (bRight.getX()>MainRun.width  || bRight.getY()<0 || bRight.getZ()<renderDistance)){
           return;
       }
       if((tLeft.getX()<0|| tLeft.getY()>MainRun.height || tLeft.getZ()<renderDistance)
         && (tRight.getX()>MainRun.width  || tRight.getY()>MainRun.height || tRight.getZ()<renderDistance)
         && (bLeft.getX()>MainRun.width  || bLeft.getY()>MainRun.height || bLeft.getZ()<renderDistance)
         && (bRight.getX()>MainRun.width  || bRight.getY()>MainRun.height || bRight.getZ()<renderDistance)){  
           return;
       }
     
       //partial clipping
       //define the clipping window
       Point windowTL= new Point(0, 0);
       Point windowTR= new Point(MainRun.width, 0);
       Point windowBL= new Point(0, MainRun.height);
       Point windowBR= new Point(MainRun.width, MainRun.height);
         
       //z clipping- top
       if(tLeft.getZ()<renderDistance && tRight.getZ()<renderDistance){
         tLeft= find3DIntersection(rectangle.getTLeft(), rectangle.getBLeft());  //need to use the 3d points instead because the method uses 3d vector math
         tRight= find3DIntersection(rectangle.getTRight(), rectangle.getBRight());
       }
       //z clipping- bottom
       if(bLeft.getZ()<renderDistance && tRight.getZ()<renderDistance){
          bLeft= find3DIntersection(rectangle.getTLeft(), rectangle.getBLeft());
          bRight= find3DIntersection(rectangle.getTRight(), rectangle.getBRight());
       }
       //z clipping- right
       
       if(tRight.getZ()<renderDistance && bRight.getZ()<renderDistance){
         tRight= find3DIntersection(rectangle.getTLeft(), rectangle.getTRight());
         bRight=  find3DIntersection(rectangle.getBLeft(), rectangle.getBRight());
       }
       //z clipping- left
       if(tLeft.getZ()<renderDistance && bLeft.getZ()<renderDistance){
         tLeft= find3DIntersection(rectangle.getTLeft(), rectangle.getTRight());
         bLeft=  find3DIntersection(rectangle.getBLeft(), rectangle.getBRight());
       }
       
       double tlZ= tLeft.getZ(), trZ= tRight.getZ(), blZ= bLeft.getZ(), brZ= bRight.getZ();
       //x clipping- left side
       if(tLeft.getX()<0 && bLeft.getX()<0){
           tLeft= find2DIntersection(tLeft, tRight, windowTL, windowBL);
           bLeft= find2DIntersection(bLeft, bRight, windowTL, windowBL);
       }
       else if(tLeft.getX()>MainRun.width && bLeft.getX()>MainRun.width){
        tLeft= find2DIntersection(tLeft, tRight, windowTR, windowBR);
           bLeft= find2DIntersection(bLeft, bRight, windowTR, windowBR);
       }
       //x clipping- right side
        if(tRight.getX()>MainRun.width && bRight.getX()>MainRun.width){
         tRight= find2DIntersection(tLeft, tRight, windowTR, windowBR);
            bRight= find2DIntersection(bLeft, bRight, windowTR, windowBR);
       }
        else if(tRight.getX()<0 && bRight.getX()<0){
         tRight= find2DIntersection(tLeft, tRight, windowTL, windowBL);
            bRight= find2DIntersection(bLeft, bRight, windowTL, windowBL);
        }
        //y clipping- top
        if(tLeft.getY()<0 && tRight.getY()<0){
         tLeft= find2DIntersection(tLeft, bLeft, windowTL, windowTR);
            tRight= find2DIntersection(tRight, bRight, windowTL, windowTR);
        }
        else if(tLeft.getY()>MainRun.height && tRight.getY()>MainRun.height){
         tLeft= find2DIntersection(tLeft, bLeft, windowBL, windowBR);
            tRight= find2DIntersection(tRight, bRight, windowBL, windowBR);
        }
        //y clipping- bottom 
        if(bLeft.getY()<0 && bRight.getY()<0){
         bLeft= find2DIntersection(tLeft, bLeft, windowTL, windowTR);
            bRight= find2DIntersection(tRight, bRight, windowTL, windowTR);
        }
        else if(bLeft.getY()>MainRun.height && bRight.getY()>MainRun.height){
         bLeft= find2DIntersection(tLeft, bLeft, windowBL, windowBR);
            bRight= find2DIntersection(tRight, bRight, windowBL, windowBR);
        }
        
        //x and y clipping messes up the z values but this fixes it
        tLeft.setZ(tlZ);
        tRight.setZ(trZ);
        bLeft.setZ(blZ);
        bRight.setZ(brZ);     
    
     //Rectangle size is determined for array size purposes
     rectArea= (int)areaOfPolygon(bLeft, bRight, tLeft, tRight);

     //These variables are just where the rendering begins, the top left corner of the polygon
     
    topVector = tRight.subNoZ(tLeft);

    sizeT = Math.sqrt(Math.pow(topVector.getX(), 2) + Math.pow(topVector.getY(), 2));
    topVector = topVector.divNoZ(sizeT);
    origTL = tLeft;

    pToDraw = origTL;
    
    origTR = tRight;

    leftVector = bLeft.subNoZ(tLeft);
    rightVector = bRight.subNoZ(tRight);
     
    sizeL = Math.sqrt(Math.pow(leftVector.getX(), 2) + Math.pow(leftVector.getY(), 2));
    sizeR = Math.sqrt(Math.pow(rightVector.getX(), 2) + Math.pow(rightVector.getY(), 2));
    
    if(sizeL > sizeR){
        
        leftVector = leftVector.divNoZ(sizeR);
        rightVector = rightVector.divNoZ(sizeR);
        
    }
    else{
        
        leftVector = leftVector.divNoZ(sizeL);
        rightVector = rightVector.divNoZ(sizeL);
        
    }
    
    county= 0;
    
    tempNum= 0;
    tempYNum= 0;
    imgX= 0;
    imgY= 0;
    
    //This loop is what iterates through the polygon and lights up each pixel with the proper texture coordinate
    while(origTL.length() < bLeft.length()){
      
      origTL = origTL.addNoZ(leftVector);
      origTR = origTR.addNoZ(rightVector);
            
      topVector = origTR.subNoZ(origTL);
      sizeT = Math.sqrt(Math.pow(topVector.getX(), 2) + Math.pow(topVector.getY(), 2));
      topVector = topVector.divNoZ(sizeT);
      
      pToDraw = origTL;

      for(int x=0; x < sizeT && county < rectArea; x++){

    if(pToDraw.getX()>0 && pToDraw.getX()<MainRun.width && pToDraw.getY()>0 && pToDraw.getY()<MainRun.height && pToDraw.getZ()>=renderDistance && val<colors.length){  //prevents exceptions
    //z buffer
      if((pToDraw.getZ()<zBuffer[(((int) pToDraw.getX()) + (((int) pToDraw.getY()) * MainRun.width))]) &&
		colors[val].getAlpha()!=0){
        	   MainRun.pixels[(((int) pToDraw.getX()) + (((int) pToDraw.getY()) * MainRun.width))]= colors[val].getRGB();
        	   zBuffer[(((int) pToDraw.getX()) + (((int) pToDraw.getY()) * MainRun.width))]= pToDraw.getZ();
           }
    }

        val = (((int) imgX) + (((int) imgY) * width));
        
        county++;
        
        imgX += width / sizeT;
        
        imgX += numberOfDivisions * (width / sizeT); 
        
        if(imgX >= width){
          imgX = 0;
        }

        pToDraw = pToDraw.addNoZ(topVector);
        
      }
      
      imgX = 0;
      if(county < rectArea){
        imgY += height/(rectArea / sizeT);
      }
      
      
      imgY += numberOfDivisions * (height/(rectArea / sizeT));
        
        if(imgY >= height){
          imgY = 0;
        }
      
    }
 
    
  }

 /**
     * Finds the area of a quad polygon
     * 
     * @param bLeft point one on first line;
     * @param bRight point two on first line;
     * @param tLeft point one on second line;
     * @param tRight point two on second line;
     * 
     * @return an int representing the number of pixels in a quad polygon. 
     * 
     */
 
 public int areaOfPolygon(Point bLeft, Point bRight, Point tLeft, Point tRight) {

  int area = 0;
  int j = 3;// numPoints - 1 AKA 4 - 1 AKA %Pirates

  double[] xVals = { bLeft.getX(), bRight.getX(), tRight.getX(), tLeft.getX() };
  double[] yVals = { bLeft.getY(), bRight.getY(), tRight.getY(), tLeft.getY() };

  for (int i = 0; i < 4; i++) {

   area += (xVals[j] + xVals[i]) * (yVals[j] - yVals[i]);

   j = i;

  }

  int a = Math.abs(area / 2);
  return a;

 }


}