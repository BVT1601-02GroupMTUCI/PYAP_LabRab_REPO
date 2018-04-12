public class Point3d {

    private double xCoord;
    private double yCoord;
    private double zCoord;

    public Point3d (double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
        }
    public Point3d () {
        this (0, 0,0);
        }

    public double getX () {
        return xCoord;
        }
    public double getY () {
        return yCoord;
        }
    public double getZ () {
        return zCoord;
    }

    public void setX (double val) {
        xCoord = val;
        }
    public void setY (double val) {
        yCoord = val;
        }
    public void setZ (double val) {
        zCoord = val;
    }

    public double distanceTo(Point3d point){
        return Math.sqrt(Math.pow(getX()-point.getX(),2)+Math.pow(getY()-point.getY(),2)+Math.pow(getZ()-point.getZ(),2));
    }

    public boolean equals(Point3d point){
        return (getX()==point.getX()&&getY()==point.getY()&&getZ()==point.getZ());
    }

    @Override
    public String toString(){
        return "Point is in (" + getX() +","+getY()+","+getZ()+")";
    }
        }
