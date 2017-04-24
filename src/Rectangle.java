/**This class represents a rectangle.
 * It holds all the corners of the rectangle.
 * It also controls the movement of rectangles.
 * @author shimismith
 * @since April 7, 2016
 */
public class Rectangle{

	/**Bottom left corner.*/
	private Point bLeft;
	/**Bottom right corner.*/
	private Point bRight;
	/**Top left corner.*/
	private Point tLeft;
	/**Top right corner.*/
	private Point tRight;
	/**Original bottom left corner.*/
	private final Point originalBLeft;
	/**Original bottom right corner.*/
	private final Point originalBRight;
	/**Original top left corner.*/
	private final Point originalTLeft;
	/**Original top right corner.*/
	private final Point originalTRight;
	/**The texture being applied to the rectangle.*/
	private Texture tex;
	/**True if the rectangle represents the enemy.*/
    public boolean isEnemy;
    
    /**
     * @param bL The bottom left corner of the rectangle
     * @param bR The bottom right corner of the rectangle
     * @param tL The top left corner of the rectangle
     * @param tR The top right corner of the rectangle
     * @param texture The texture being applied to the rectangle
     */
	public Rectangle(Point bL, Point bR, Point tL, Point tR, Texture texture) {

		originalBLeft = new Point(bL.getX(), bL.getY(), bL.getZ());
		originalBRight = new Point(bR.getX(), bR.getY(), bR.getZ());
		originalTLeft = new Point(tL.getX(), tL.getY(), tL.getZ());
		originalTRight = new Point(tR.getX(), tR.getY(), tR.getZ());

		bLeft = new Point(bL.getX(), bL.getY(), bL.getZ());
		bRight = new Point(bR.getX(), bR.getY(), bR.getZ());
		tLeft = new Point(tL.getX(), tL.getY(), tL.getZ());
		tRight = new Point(tR.getX(), tR.getY(), tR.getZ());

		tex = texture;

	}
     
	/**
	 * @param bL The bottom left corner of the rectangle
     * @param bR The bottom right corner of the rectangle
     * @param tL The top left corner of the rectangle
     * @param tR The top right corner of the rectangle
     * @param texture The texture being applied to the rectangle
	 * @param isAnEnemy A boolean that represents if the rectangle represents an enemy
	 */
	public Rectangle(Point bL, Point bR, Point tL, Point tR, Texture texture, boolean isAnEnemy) {

		originalBLeft = new Point(bL.getX(), bL.getY(), bL.getZ());
		originalBRight = new Point(bR.getX(), bR.getY(), bR.getZ());
		originalTLeft = new Point(tL.getX(), tL.getY(), tL.getZ());
		originalTRight = new Point(tR.getX(), tR.getY(), tR.getZ());

		bLeft = new Point(bL.getX(), bL.getY(), bL.getZ());
		bRight = new Point(bR.getX(), bR.getY(), bR.getZ());
		tLeft = new Point(tL.getX(), tL.getY(), tL.getZ());
		tRight = new Point(tR.getX(), tR.getY(), tR.getZ());

		tex = texture;

        isEnemy = isAnEnemy;
                
	}
	
	/** Sets the bottom left point
	 * @param p the new Point
	 */
	protected void setBLeft(Point p) {
		bLeft = p;
	}

	/** Sets the bottom right point
	 * @param p the new Point
	 */
	protected void setBRight(Point p) {
		bRight = p;
	}

	/** Sets the top left point
	 * @param p the new Point
	 */
	protected void setTLeft(Point p) {
		tLeft = p;
	}

	/** Sets the top right point
	 * @param p the new Point
	 */
	protected void setTRight(Point p) {
		tRight = p;
	}

	/** @return bottom left point
	 */
	public Point getBLeft() {
		return bLeft;
	}

	/** @return bottom right point
	 */
	public Point getBRight() {
		return bRight;
	}

	/** @return top left point
	 */
	public Point getTLeft() {
		return tLeft;
	}
	
	/** @return top right point
	 */
	public Point getTRight() {
		return tRight;
	}
	
	/** @return original bottom left point
	 */
	public Point getOriginalBLeft() {
		return originalBLeft;
	}

	/** @return original bottom right point
	 */
	public Point getOriginalBRight() {
		return originalBRight;
	}

	/** @return original top left point
	 */
	public Point getOriginalTLeft() {
		return originalTLeft;
	}

	/** @return original top right point
	 */
	public Point getOriginalTRight() {
		return originalTRight;
	}

	/** @return texture
	 */
	public Texture getTexture() {
		return tex;
	}

	/** @return the normal vector to the rectangle
	 */
	public Point returnNormal() {

		Point tempVector1 = bRight.sub(tLeft);
		Point tempVector2 = tRight.sub(bLeft);

		Point normal = tempVector1.cross(tempVector2);

		normal = normal.makeUnitLength();

		return normal;
	}

	/** Moves the rectangle relative to the player. For example if the player moves right the rectangle will move left.
	 */
	public void adjustLocationRelativeToPlayer() {

		//rotate rectangle
		bLeft.rotateY(-MainRun.player.getRotation());
		bRight.rotateY(-MainRun.player.getRotation());
		tLeft.rotateY(-MainRun.player.getRotation());
		tRight.rotateY(-MainRun.player.getRotation());

		//move rectangle
		bLeft.setX(bLeft.getX() - MainRun.player.getVelH());
		bRight.setX(bRight.getX() - MainRun.player.getVelH());
		tLeft.setX(tLeft.getX() - MainRun.player.getVelH());
		tRight.setX(tRight.getX() - MainRun.player.getVelH());

		bLeft.setZ(bLeft.getZ() - MainRun.player.getVelF());
		bRight.setZ(bRight.getZ() - MainRun.player.getVelF());
		tLeft.setZ(tLeft.getZ() - MainRun.player.getVelF());
		tRight.setZ(tRight.getZ() - MainRun.player.getVelF());

	}
	
	/** Rotates the rectangle to always face the player.
	 */
	public void rotateToFacePlayer(){  //full credit to russell with coming up with this math
		Point n= MainRun.player.getLocation().sub(bRight).makeUnitLength();
		Point u= new Point(0, originalTLeft.getY()-originalBLeft.getY(), 0);
		Point r= n.cross(u).makeUnitLength().mul(originalTLeft.getX()-originalTRight.getX());
		//bRight stays the same
		bLeft= bRight.sub(r);
		tLeft= bRight.add(u).sub(r);
		tRight= bRight.add(u);
	}

	/** Changes the location of a rectangle based on the location of the player being changed.
	 * This method is used for changes in location not related to the velocities.
	 * For collision detection this method is used to bump back rectangles to their original locations when a collision is detected.
	 * @param originalLocationOfPlayer the original location of the player before the location was changed
	 */
	public void resetLocation(Point originalLocationOfPlayer) {

	  Point changeInLocation= MainRun.player.getLocation().sub(originalLocationOfPlayer);

	  setBLeft(new Point(bLeft.getX() - changeInLocation.getX(), bLeft.getY(), bLeft.getZ()- changeInLocation.getZ())); 
	  setBRight(new Point(bRight.getX()- changeInLocation.getX(), bRight.getY(), bRight.getZ()- changeInLocation.getZ()));
	  setTLeft(new Point(tLeft.getX()- changeInLocation.getX(), tLeft.getY(), tLeft.getZ()- changeInLocation.getZ())); 
	  setTRight(new Point(tRight.getX()- changeInLocation.getX(), tRight.getY(), tRight.getZ()- changeInLocation.getZ()));
	}

}

