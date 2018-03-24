public class point3D {
    private double _x, _y, _z;

    public point3D(double x, double y, double z){
        _x = x;
        _y = y;
        _z = z;
    }
    public point3D()
    {
        _x = 0;
        _y = 0;
        _z = 0;
    }
    public double getX()
    {
        return _x;
    }
    public double getY()
    {
        return _y;
    }
    public double getZ()
    {
        return _z;
    }

    public void setX(double x)
    {
        _x = x;
    }
    public void setY(double y)
    {
        _y = y;
    }
    public void setZ(double z)
    {
        _z = z;
    }

    public static double Distanse(point3D p1, point3D p2)
    {
        return Math.sqrt(Math.pow((p2.getX() - p1.getX()),2) +Math.pow((p2.getY() - p1.getY()),2) +Math.pow((p2.getZ() - p1.getZ()),2) );
    }

    public static boolean compare(point3D p1, point3D p2, point3D p3)
    {
        if (p1._x == p2._x && p1._x == p3._x && p1._y == p2._y && p1._y== p3._y && p1._z == p2._z && p1._z == p3._z)
            return true;
        else return  false;
    }

    public static double heron(point3D p1, point3D p2, point3D p3)
    {
        if(compare(p1, p2, p3) != true) {
            double a = Distanse(p1, p2);
            double b = Distanse(p2, p3);
            double c = Distanse(p3, p1);
            double p = (a + b + c) / 2;
            return Math.sqrt(p * (p - a) * (p - b) * (p - c));
        }
        else return 0;
    }
}
