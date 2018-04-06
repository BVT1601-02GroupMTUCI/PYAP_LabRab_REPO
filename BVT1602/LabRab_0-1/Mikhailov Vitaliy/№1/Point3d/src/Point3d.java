//трехмерный класс точки.

public class Point3d {
    //координаты точки X,Y и Z
    private double xCoord;
    private double yCoord;
    private double zCoord;
    //Конструктор, чтобы инициализировать точку к (х, у, z) значение
    public Point3d(double x, double y, double z){
        xCoord = x;
        yCoord = y; 
        zCoord = z;
    }
    //Конструктор без параметров: значения по умолчанию к точке в источнике
    public Point3d() {
        this(0, 0, 0);
    }
    //Вернуть координату Х точки
    public double getX()
    {
        return xCoord;
    }
    //Вернуть координату Y точки
    public double getY()
    {
        return yCoord;
    }
    //Вернуть координату Z точки
    public double getZ()
    {
        return zCoord;
    }
    //Набор Х координат точки
    public void setX(double val)
    {
        xCoord=val;
    }
    //Набор Y координат точки
    public void setY(double val)
    {
        yCoord=val;
    }
    //Набор Z координат точки
    public void setZ(double val)
    {
        zCoord=val;
    }
    
    //Определение равенства двух точек
    public boolean equality(Point3d b)
    {
        if (getX()==b.getX()&&getY()==b.getY()&&getY()==b.getY())
            return true;
        else return false;
    }
    
    //Расчет расстояния между двумя точками
    public double distanceTo(Point3d b)
    {
        return Math.pow(Math.pow((getX()-b.getX()),2)+Math.pow((getY()-b.getY()),2)+Math.pow((getZ()-b.getZ()),2),0.5);
        
    }
    
}
 

