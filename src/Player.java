
import java.io.Serializable;

/**This class represents a Player.
 * It keeps track of the health and location of the player.
 * @author shimismith
 * @since April 7, 2016
 */
public class Player implements Serializable{

  /**Forward velocity.*/
  private double velF;
  /**Horizontal velocity.*/
  private double velH;
  /**Rotation per tick.*/
  private double angularVel;
  /**Location of the player.*/
  private Point location;
  /**The rotation that needs to be applied to the rectangles that make up the world.*/
  private double rotation;
  /**The total rotation of the player.*/
  private double totalRotation;
 /**True if this player is the server and false if this player is a client.*/
  private boolean server;
  /**True if the reticle is toggled on.*/
  private boolean aiming;
  /**The health of the player out of 100.*/
  private int health;
  /**The damage that this player did to the other player.*/
  private int damageDone;
  /**True if the game is multiplayer.*/
  private boolean multiplayer;
  
  /** Sets the default location to (0,0,0), the health to 100 and multiplayer defaults to true.
   */
  public Player(){
    location= new Point(0, 0, 0);
    health= 100;
    multiplayer= true;
  }
  
  /**  Sets multiplayer
   * @param b boolean to set multiplayer to.
   */
  public void setMultiplayer(boolean b){
   multiplayer= b;
  }
  
  /** @return multiplayer
   */
  public boolean isMultiplayer(){
   return multiplayer;
  }
  
  /** Sets the x value of the player's location.
   * @param x- the new x value of the player's location
   */
  public void setX(double x){
    location.setX(x);
  }
  
  /** @return the x value of the player's location
   */
  public double getX(){
   return location.getX();
  }
  
  /** @return the y value of the player's location
   */
  public double getY(){
   return location.getY();
  }
  
  /** Sets the z value of the player's location.
   * @param z- the new z value of the player's location
   */
  public void setZ(double z){
    location.setZ(z);
  }
  
  /** @return the z value of the player's location
   */
  public double getZ(){
    return location.getZ();
  }
  
  /** @return the point representing the location of the player
   */
  public Point getLocation(){
   return location;
  }
  
  /** Sets the forward velocity of the player
   * @param v the new forward velocity
   */
  public void setVelF(double v){
    velF= v;
  }
  
  /** Sets the horizontal velocity of the player
   * @param v the new horizontal velocity
   */
  public void setVelH(double v){
    velH= v;
  }
  
  /** Sets the angular velocity of the player
   * @param v the new angular velocity
   */
  public void setAngularVel(double v){
    angularVel= v;
  }
  
  /** Sets the health of the player
   * @param h the new health of the player
   */
  public void setHealth(int h){
   health= h;
  }
  
  /** Sets the damage done to the other player
   * @param d the new damageDone
   */
  public void setDamageDone(int d){
   damageDone= d;
  }
  
  /** @return the boolean representing if the reticle should be displayed
   */
  public boolean isAiming() {
  return aiming;
 }

  /** Changes aiming. If it is true it will become false and if it is false it will become true. 
   */
  public void switchAiming() {
  aiming= !aiming;
  }
  
  /** @return the forward velocity
   */
  protected double getVelF(){
    return velF;
  }
  
  /** @return the horizontal velocity
   */
  protected double getVelH(){
    return velH;
  }
  
  /** @return the angular velocity
   */
  protected double getAngularVel(){
    return angularVel;
  }
  
  /** @return the rotation
   */
  protected double getRotation(){
    return rotation;
  }
  
  protected double getTotalRotation(){
	  return totalRotation;
  }
  
  /** @return the health of the player out of 100
   */
  protected int getHealth(){
   return health;
  }
  
  /** @return the damage done to the other player
   */
  protected int getDamageDone(){
   return damageDone;
  }
  
  /** Sets the boolean that represents if this player is the server
   * @param b the new boolean to set server
   */
  public void setServer(boolean b){
   server= b;
  }
  
  /** @return the boolean that represents if this player is the server
   */
  protected boolean isServer(){
   return server;
  }
  
  /** Resets the rotation to zero and adds the temporary rotation to the total rotation
   */
  protected void resetRotation(){
   totalRotation+= rotation;
   rotation= 0;
  }
  
  /** Moves the player based on the forward and horizontal velocities.
   * The velocities are decomposed into their x and z components.
   */
  public void move(){
   setX(getX() + (velF*Math.sin(totalRotation)) + (velH*Math.cos(totalRotation)));
   setZ(getZ() + (velF*Math.cos(totalRotation) - (velH*Math.sin(totalRotation))));
    rotation+= angularVel;
  }

  
}