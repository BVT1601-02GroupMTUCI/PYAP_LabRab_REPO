
import java.io.*;
        import java.util.Scanner;
        import java.util.ArrayList;
public class Lab1 {
    static ArrayList<Point3d> PointList = new ArrayList<>();


    public static void main(String[] args) {

        /*ПРИМЕР: A(-2, 1, 2); B(3, -3, 4); C(1, 0, 9). Площадь :19,787*/
        Scanner reader = new Scanner(System.in);
        double x=0,y=0,z=0;
        for(int i=0;i<3;i++){
            System.out.println("Введите координаты "+ i +"-ой точки: ");
            System.out.print("X: ");
            x = reader.nextDouble();
            System.out.print("Y: ");
            y = reader.nextDouble();
            System.out.print("Z: ");
            z = reader.nextDouble();
            PointList.add(new Point3d(x,y,z));
        }
        reader.close();

        if (!(PointList.get(0).comparePoint(PointList.get(1)) || PointList.get(1).comparePoint(PointList.get(2)) ||PointList.get(0).comparePoint(PointList.get(2))))
        {
            double a=PointList.get(0).distanceTo(PointList.get(1));
            double b=PointList.get(1).distanceTo(PointList.get(2));
            double c=PointList.get(0).distanceTo(PointList.get(2));
            System.out.println("Длины сторон треугольника: \na:"+a+"\nb:"+b+"\nc:"+c);
            System.out.println("Площадь треугольника равна: "+computeArea(a,b,c));

        }
        else System.out.println("Введены одинаковые точки!");



    }
    public static double computeArea(double a,double b,double c){


        double s = (a + b + c)/2.0d;
        double x = (s * (s-a) * (s-b) * (s-c));
        double Area= Math.sqrt(x);
        return Area;}
}