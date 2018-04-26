/**
* двумерный класс точки.
**/
    public class Point2d {
    /** X координат точки **/
    private double xCoord;
    /** Y координата точки **/
    private double yCoord;
    /** Конструктор, чтобы инициализировать точку к (x, y) значение. **/

    public Point2d (double x, double y) {
        xCoord = x;
        yCoord = y;
    }

    /** Конструктор без параметров: значения по умолчанию к точке в источнике. **/

    public Point2d () {
        //Вызовите конструктор с двумя параметрами и определите источник.
        this(0, 0);
    }

    /** Верните X координат точки. **/

    public double getX () {
        return xCoord;
    }

    /** Возвратите координату Y точки. **/
    public double getY () {
        return yCoord;
    }

    /** Набор X координат точки. **/
    public void setX (double val) {
        xCoord = val;
    }

    /** Набор координата Y точки. **/
    public void setY (double val) {
        yCoord = val;
    }
}