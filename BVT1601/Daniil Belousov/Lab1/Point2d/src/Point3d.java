public class Point3d {

    private double xCoord;
    private double yCoord;
    private double zCoord;
    public Point3d(double x,double y,double z){
        setX(x);
        setY(y);
        setZ(z);

    }
    public Point3d(){this(0.0,0.0,0.0);}
    public double getX(){
        return xCoord;
    }
    public double getY(){
        return yCoord;

    }
    public double getZ(){
        return zCoord;

    }
    public void setX(double val){
        xCoord = val;
    }
    public void setY(double val){
        yCoord =val;
    }
    public void setZ(double val){
        zCoord =val;
    }

    public boolean comparePoint(Point3d point){
        return (this.getX() == point.getX() && this.getY() == point.getY() && this.getZ() == point.getZ());
    }


    public double distanceTo(Point3d name1)
    {
        double distance = Math.sqrt(Math.pow(this.getX()-name1.getX(),2)+Math.pow(this.getY()-name1.getY(),2)+ Math.pow(this.getZ()-name1.getZ(),2));
        return distance;
    }


}
