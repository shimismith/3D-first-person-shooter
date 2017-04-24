import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
/** This class builds levels based on a top down representation of the level.
 * Each pixel in the image represents 4 walls and walls are made around the boundaries of the image.
 * @author shimismith
 * @since May 2, 2016
 */
public class LevelBuilder {
 
/**Image of a top down representation of the level.*/
 private BufferedImage img;
 /**Array of all the rectangles that make up the level.*/
 private Rectangle[] world;
 /**For collision detection*/
 public static CollisionBox box;
 
 /** Creates a LevelBuilder from the path to a top down image of the level being created. 
  * @param file the path to a top down image of the level being created
  */
 public LevelBuilder(String file) {
  try {
   img = ImageIO.read(LevelBuilder.class.getResource(file));  //loads the image
  } catch (Exception e) {
  }
 }
 
 /** Builds the level map by creating rectangles based on the top down image.
  */
 public void buildLevel(){
   Texture tex = new Texture("bricks.png", 2);  //loads the texture
   final int scale= 10;  //scale used to convert image measurements to 3d world measurements
   final int floorY= -10;  //bottom y value of walls
   final int ceilingY= 10;  //top  value of walls
   final int startX= -50, startZ= 60;  //far left corner of the room
   
   //builds room
   Rectangle backWall= new Rectangle(new Point(startX, floorY, startZ), new Point(startX + img.getWidth()*scale, floorY, startZ), new Point(startX, ceilingY, startZ), new Point(startX + img.getWidth()*scale, ceilingY, startZ), tex,false);
   Rectangle frontWall= new Rectangle(new Point(startX, floorY, startZ-img.getHeight()*scale), new Point(startX + img.getWidth()*scale, floorY, startZ-img.getHeight()*scale), new Point(startX, ceilingY, startZ-img.getHeight()*scale), new Point(startX + img.getWidth()*scale, ceilingY, startZ-img.getHeight()*scale), tex, false);
   Rectangle leftWall= new Rectangle(new Point(startX, floorY, startZ), new Point(startX, floorY, startZ-img.getHeight()*scale), new Point(startX, ceilingY, startZ), new Point(startX, ceilingY, startZ-img.getHeight()*scale), tex, false);
   Rectangle rightWall= new Rectangle(new Point(startX + img.getWidth()*scale, floorY, startZ), new Point(startX + img.getWidth()*scale, floorY, startZ-img.getHeight()*scale), new Point(startX + img.getWidth()*scale, ceilingY, startZ), new Point(startX + img.getWidth()*scale, ceilingY, startZ-img.getHeight()*scale), tex, false);
   
   box = new CollisionBox( (int) leftWall.getOriginalBLeft().getX(), (int) rightWall.getOriginalBLeft().getX(), (int) frontWall.getOriginalBLeft().getZ(), (int) backWall.getOriginalBLeft().getZ());
   
   //find length of array- accounts for the rectangles needed to build blocks inside the room
   int length= 4;
   for (int y = 0; y < img.getHeight(); y++) {
    for (int x = 0; x < img.getWidth(); x++) {
     if(img.getRGB(x, y)==Color.BLACK.getRGB()){
      length+= 4;
     }
    }
   }
   
   world= new Rectangle[length];
   int i= 0;
   world[i++]= backWall;
   world[i++]= frontWall;
   world[i++]= leftWall;
   world[i++]= rightWall;
   
   // builds blocks in room
   for (int y = 0; y < img.getHeight(); y++) {
    for (int x = 0; x < img.getWidth(); x++) {
     if(img.getRGB(x, y)==Color.BLACK.getRGB()){
      backWall= new Rectangle(new Point(startX + x*scale, floorY, startZ - y*scale), new Point(startX + x*scale + scale, floorY, startZ - y*scale), new Point(startX + x*scale, ceilingY, startZ - y*scale), new Point(startX + x*scale + scale, ceilingY, startZ - y*scale), tex, false);
      frontWall= new Rectangle(new Point(startX + x*scale, floorY, startZ - y*scale - scale), new Point(startX + x*scale + scale, floorY, startZ - y*scale - scale), new Point(startX + x*scale, ceilingY, startZ - y*scale - scale), new Point(startX + x*scale + scale, ceilingY, startZ - y*scale - scale), tex, false);
      leftWall= new Rectangle(new Point(startX + x*scale, floorY, startZ - y*scale), new Point(startX + x*scale, floorY, startZ - y*scale - scale), new Point(startX + x*scale, ceilingY, startZ - y*scale), new Point(startX + x*scale, ceilingY, startZ - y*scale - scale), tex, false);
      rightWall= new Rectangle(new Point(startX + x*scale + scale, floorY, startZ - y*scale), new Point(startX + x*scale + scale, floorY, startZ - y*scale - scale), new Point(startX + x*scale + scale, ceilingY, startZ - y*scale), new Point(startX + x*scale + scale, ceilingY, startZ - y*scale - scale), tex, false);
      world[i++]= backWall;
      world[i++]= frontWall;
      world[i++]= leftWall;
      world[i++]= rightWall;
     }
    }
   }

 }
 
 /** @return the array holding all the rectangles that build up the level
  */
 public Rectangle[] getWorld(){
  return world;
 }
 
 /** @param i index in the array of Rectangle
  * @return returns the Rectangle at the index i
  */
 public Rectangle getRectangle(int i){
   return world[i];
 }
 
 /** @return the length of the Rectangle array
  */
 public int getWorldLength(){
   return world.length;
 }

}
