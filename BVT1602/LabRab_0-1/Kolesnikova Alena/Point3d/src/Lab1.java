import java.io.*;
import java.util.Scanner;

public class Lab1{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите координаты первой точки(A) через пробел (x y z): ");
        Point3d a3d = new Point3d(in.nextDouble(), in.nextDouble(), in.nextDouble());
        System.out.print("Введите координаты второй точки(B) через пробел (x y z): ");
        Point3d b3d = new Point3d(in.nextDouble(), in.nextDouble(), in.nextDouble());
        System.out.print("Введите координаты третьей точки(C) через пробел (x y z): ");
        Point3d c3d = new Point3d(in.nextDouble(), in.nextDouble(), in.nextDouble());
    //    System.out.println(a3d.toString());
        System.out.println("a3d.distanceTo(b3d): " + a3d.distanceTo(b3d));
        double area = computeArea(a3d, b3d, c3d);
        if (area != 0) System.out.println("area: "+area);
    }

    public static double computeArea(Point3d aPoint, Point3d bPoint, Point3d cPoint){
        if (aPoint.equ(bPoint) || aPoint.equ(cPoint) || bPoint.equ(cPoint)){
            System.out.println("Некоторые точки совпадают");
            return 0;
        }
        double a = aPoint.distanceTo(bPoint);
        double b = bPoint.distanceTo(cPoint);
        double c = cPoint.distanceTo(aPoint);
        double p = (a+b+c)/2;
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));

    }



}