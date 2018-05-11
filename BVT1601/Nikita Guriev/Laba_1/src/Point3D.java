public class Point3D {
    private double xCoord;
    private double yCoord;
    private double zCoord;

    public Point3D(double x, double y, double z){
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    public Point3D(){
        this(0.0,0.0, 0.0);
    }
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
        yCoord = val;
    }
    public void setZ(double val){
        zCoord = val;
    }

    public double distanceTo(Point3D P3D){
        return Math.sqrt(Math.pow(this.getX() - P3D.getX(),2)
                + Math.pow(this.getY() - P3D.getY(),2)
                + Math.pow(this.getZ() - P3D.getZ(),2));
    }

    public boolean comparePoint(Point3D P3D){
        return (this.getX() == P3D.getX() && this.getY() == P3D.getY() && this.getZ() == P3D.getZ());
    }
}
