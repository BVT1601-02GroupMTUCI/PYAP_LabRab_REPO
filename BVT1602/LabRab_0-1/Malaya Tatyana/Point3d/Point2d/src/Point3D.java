public class Point3D{
	private double xCoord;
	private double yCoord;
	private double zCoord;
	public Point3D(double x, double y,double z) {
		xCoord=x;
		yCoord=y;
		zCoord=z;
	} 
	//Point3d firstPoint = new Point3d(2,2,2);
	//firstPoint.setX(3)
	public Point3D() {
	this(0.0,0.0,0.0);
	}
	public double getX() {
		return xCoord;
		}
	public double getY() {
		return yCoord;
		}
	public double getZ() {
		return zCoord;
		}
	public void setX(double val) {
		xCoord=val;
		}
	public void setY(double val) {
		yCoord=val;
		}
	public void setZ(double val) {
		zCoord=val;
		}
	public double distanceTo ( Point3D point){
		double distance = Math.sqrt(Math.pow(getX()-point.getX(),2) + Math.pow(getY()-point.getY(),2) + Math.pow(getZ()-point.getZ(),2));
		return distance;
	}
	public boolean equality(Point3D point) {
	 return (getX()==point.getX() && getY()==point.getY() && getZ()==point.getZ());
}}
