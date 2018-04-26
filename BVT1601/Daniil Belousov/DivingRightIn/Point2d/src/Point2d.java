public class Point2d {
    private double xCoord;
    private double yCoord;

    Point2d(double x, double y) {
        xCoord = x;
        yCoord = y;
    }

    Point2d() {
        this(0,0);
    }

    public double getxCoord() {
        return xCoord;
    }

    public void SetxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double GetyCoord() {
        return yCoord;
    }

    public void SetyCoord(double yCoord) {
        this.yCoord = yCoord;
    }


    public void setX(double val){
        xCoord = val;
    }
    public void setY(double val){
        yCoord =val;
    }
}