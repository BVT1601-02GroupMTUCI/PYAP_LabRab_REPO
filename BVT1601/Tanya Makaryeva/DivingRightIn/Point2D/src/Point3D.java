public class Point3D extends Point2D {
    private double zCoord;

    Point3D() {
        this(0, 0, 0);
    }

    Point3D(double x, double y, double z) {
        super(x, y);
        zCoord = z;
    }

    public double getzCoord() {
        return zCoord;
    }

    public void setzCoord(double zCoord) {
        this.zCoord = zCoord;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() == obj.getClass()) {
            Point3D point = (Point3D) obj;
            if (point.zCoord == this.zCoord &&
                    point.getyCoord() == this.getyCoord() &&
                    point.getzCoord() == this.getzCoord())
                return true;
            return false;
        } else
            return false;
    }

    public double distanceTo(Point3D point) {
        return Math.sqrt(Math.pow(this.getxCoord() * this.getxCoord() - point.getxCoord() * point.getxCoord(), 2) +
                Math.pow(this.getyCoord() * this.getyCoord() - point.getyCoord() * point.getyCoord(), 2) +
                Math.pow(this.zCoord * this.zCoord - point.zCoord * point.zCoord, 2));
    }
}
