public class Point3d {

    private double x;
    private double y;
    private double z;

    public Point3d(){
        x = 0;
        y = 0;
        z = 0;
    }
    public Point3d(double _x, double _y, double _z){
        x = _x;
        y = _y;
        z = _z;
    }
    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getZ(){ return z; }
    public void setX(double _x){ x = _x; }
    public void setY(double _y){ y = _y; }
    public void setZ(double _z){ z = _z; }

    public boolean equals(Point3d point){
        if(x != point.getX())return false;
        if(y != point.getY())return false;
        if(z != point.getZ())return false;

        return true;
    }
    public double distanceTo(Point3d point){
        return Math.sqrt(Math.pow(point.getX()-x, 2) +
                            Math.pow(point.getY()-y, 2) +
                            Math.pow(point.getZ()-z, 2));
    }
}
