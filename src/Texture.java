import java.awt.image.*;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * @author Russell Brown
 * @since April 6, 2016
 * 
 * This is the class that holds images as an array of colored pixels. 
 * 
 */

public class Texture {
  
  
  /**
   * colorlist is the array representing the image
   */
  private Color[] colorList;
  private int width;
  private int height;
  private Color col;
  private double numberOfDivisions;
  
  /**
     * Turns an image into an array of colors to be used as a texture in Render.
     * 
     * @param string is the name of the file
     * @param n is the number of divisions a texture should perform, creates a repeating texture effect
     * 
     * @return no return type because its a constructor. 
     * 
     */
  
  public Texture(String string, double n){
    
   numberOfDivisions= n;
   
    BufferedImage image= null;
    try{
      image = ImageIO.read(Texture.class.getResource(string));
    }catch(Exception e){
        System.out.println("Error");
    }
    
    colorList = new Color[image.getWidth() * image.getHeight()];
    width = image.getWidth();
    height = image.getHeight();
    
    int county= 0;
    for(int y=0; y<image.getHeight(); y++){
      for(int x=0; x<image.getWidth(); x++){ 
        col = new Color(image.getRGB(x, y), true);
        colorList[county++]= col;
      }
    }
    
    
  }
  
  /**
     * Returns the number of times a texture should be subdivided
     * 
     * 
     * @return a double which is the number of subdivisions. 
     * 
     */
  
  public double getNumberOfDivisions(){
   return numberOfDivisions;
  }
  
  /**
     * Returns the array of colors
     * 
     * 
     * @return color array. 
     * 
     */
  
  public Color[] getCol(){
      return colorList;
  }
  
  public int getWidth(){
      return width;
  }
  public int getHeight(){
      return height;
  }
  
  
}