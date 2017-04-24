import java.awt.Color;

public class LightSource {
	  int x, y, z;  //put this in a point- location of light source

	  //NOTE THIS DOESN'T WORK IT IS FOR 2D IT WOULD NEED THE ORIGNAL ADJUST BRIGHTNESS
	  public LightSource(int x, int y){  //add z
	    this.x = x;
	    this.y = y;
	  }
	  
	  public LightSource(int x, int y, int z){
		  this.x = x;
		  this.y = y;
		  this.z = z;
	  }
	  
	  public Color adjustBrightness(Point p){  //distance away from light source   use point instead and calculate distance
		  Color color = p.getColor();
		  double distance = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));  //maybe divie my 50 to make it look nicer
		  float hsb[] = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
		  
		  return Color.getHSBColor(hsb[0], hsb[1], (float)Math.pow(0.9, distance) * hsb[2]);
	  }
}
