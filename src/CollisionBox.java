/**
 * @author Russell Brown
 * @since May 21, 2016
 * 
 * This is the class that checks whether a player is within or outside a given area.
 * 
 */

public class CollisionBox {
    
     /**
     * The first four are the boundaries of the collision box, the last two are the locations of the player before he went outside the box.
     */
  
    private double minX, maxX, minZ, maxZ, properX, properZ;
    
     /**
     * This Method generates a collision box.
     * 
     * It creates the collision box based on 4 parameters based on x and y.
     * This technically makes it a collision rectangle, but the y boundaries are infinite. 
     * 
     * @param minnX is the minimum x value of the box.
     * @param maxxX is the maximum x value of the box.
     * @param minnZ is the minimum z value of the box.
     * @param maxxZ is the maximum z value of the box.
     * 
     * @return has no return type because its a constructor.
     * 
     */
    
    public CollisionBox(double minnX, double maxxX, double minnZ, double maxxZ){
        
        minX = minnX;
        maxX = maxxX;
        minZ = minnZ;
        maxZ = maxxZ;
        
    }
    
    /**
     * Holds the players previous location (Before he went outside the collision box).
     * 
     * @param propX is the proper x value.
     * @param propZ is the proper z value.
     * 
     * @return has a void return type because it is only changing internal class variables.
     * 
     */
    
    public void setProperLocations(int propX, int propZ){
        
        properX = propX;
        properZ = propZ;
        
    }
    
    /**
     * This method actually checks whether the players location is within the collision box, and adjusts it the location based on propX and propZ.
     * 
     * @return has a void return type because it changes the players location in a direct reference to another class.
     * 
     */
    
    public void checkForCollision(){
      
      //If the boxes boundaries are reversed, switches them back
      
      if(maxZ < minZ){
        double temp = maxZ;
        maxZ = minZ;
        minZ = temp;
        
      }
      if(maxX < minX){
        double temp = maxX;
        maxX = minX;
        minX = temp;
        
      }
        //Checks if player is inside the box.
        if(MainRun.player.getX() > minX && MainRun.player.getX() < maxX && MainRun.player.getZ() > minZ && MainRun.player.getZ() < maxZ){
        }
        //If not, return him back to the box.
        else{
          
            //changes player location to before he crossed a wall.
          
            Point originalLocation= new Point(MainRun.player.getLocation().getX(), MainRun.player.getLocation().getY(), MainRun.player.getLocation().getZ());
               
            MainRun.player.setX(properX);
            MainRun.player.setZ(properZ);

            for(int i = 0; i < MainRun.rects.length; i++){
             MainRun.rects[i].resetLocation(originalLocation);
            }        
        }
        
    }
    
}