public class Point2D {
    private double xCoord;
    private double yCoord;

    Point2D(double x, double y) {
        xCoord = x;
        yCoord = y;
    }

    Point2D() {
        this(0,0);
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public boolean isEqual(Point2D point) {
        if(point.xCoord == xCoord && point.yCoord == yCoord)
            return true;
        return false;
    }
}
