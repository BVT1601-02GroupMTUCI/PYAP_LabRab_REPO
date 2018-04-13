//двумерный класс точки.

public class Point2d {
    //координаты точки X и У
    private double xCoord;
    private double yCoord;
    //Конструктор, чтобы инициализировать точку к (х, у) значение
    public Point2d(double x, double y)
    {
        xCoord = x;
        yCoord = y; 
    }
    //Конструктор без параметров: значения по умолчанию к точке в источнике
    public Point2d()
    {
        this(0, 0);
    }
    public double getX()
    {
        return xCoord;
    }
    public double getY()
    {
        return yCoord;
    }
    //Набор Х координат точки
    public void setX(double val)
    {
        xCoord=val;
    }
    public void setY(double val)
    {
        yCoord=val;
    }
    
    Point2d myPoint = new Point2d(); //создает точку в (0,0)
    Point2d myOtherPoint = new Point2d(5,3); //создает точку в (5,3)
    Point2d aThirdPoint = new Point2d();
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
 