/**
 * трехмерный класс точки.
 **/
public class Point3d {
    /** X координат точки **/
    private double xCoord;
    /** Y координата точки **/
    private double yCoord;
    /** Z координата точки **/
    private double zCoord;
    /** Конструктор, чтобы инициализировать точку к (x, y, z) значение. **/

    public Point3d (double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    /** Конструктор без параметров: значения по умолчанию к точке в источнике. **/

    public Point3d () {
        //Вызовите конструктор с двумя параметрами и определите источник.
        this(0, 0, 0);
    }

    /** Верните X координат точки. **/

    public double getX () {
        return xCoord;
    }

    /** Возвратите координату Y точки. **/
    public double getY () {
        return yCoord;
    }

    /** Возвратите координату Z точки. **/
    public double getZ () {
        return zCoord;
    }

    /** Набор X координат точки. **/
    public void setX (double val) {
        xCoord = val;
    }

    /** Набор координата Y точки. **/
    public void setY (double val) {
        yCoord = val;
    }
    /** Набор координата Z точки. **/
    public void setZ (double val) {
        zCoord = val;
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