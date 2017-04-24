public class Shooting {
	public static boolean shoot(){
		Point normal = MainRun.er.returnNormal();
		double x = 0.0;
	    double z = 1.0;
	    double d = - normal.getX() * MainRun.er.getTLeft().getX() - normal.getY() * MainRun.er.getTLeft().getY() - normal.getZ() * MainRun.er.getTLeft().getZ();
	    double t = (- d) / (normal.getX() * x + normal.getZ() * z);
	    double xHit = t * x;
	    double zHit = t * z;
	    double yHit = 5.0;
	    if ((xHit > MainRun.er.getTLeft().getX() && xHit < MainRun.er.getTRight().getX() || xHit < MainRun.er.getTLeft().getX() && xHit > MainRun.er.getTRight().getX()) && zHit <= Render.zBuffer[(int)xHit + (int)yHit * 1080]) {
	        return true;
	    }
	    return false;
	}
		
}
