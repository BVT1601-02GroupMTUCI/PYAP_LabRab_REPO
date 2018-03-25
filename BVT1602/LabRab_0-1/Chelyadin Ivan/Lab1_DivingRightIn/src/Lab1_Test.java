import java.io.*;
import java.util.Scanner;

public class Lab1_Test {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите координаты точки A через пробел (x y z): ");
        Point3d first3d = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.print("Введите координаты точки B через пробел (x y z): ");
        Point3d second3d = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.print("Введите координаты точки C через пробел (x y z): ");
        Point3d third3d = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.println(first3d.toString());
        System.out.println(first3d.distanceTo(second3d));
        double area = computeArea(first3d,second3d,third3d);
        if (area!=0) System.out.println(area);

    }

    public static double computeArea(Point3d A, Point3d B, Point3d C){
        if (A.equals(B) || A.equals(C) || B.equals(C)){
            System.out.println("Некоторые точки совпадают");
            return 0;
        }
        double a = A.distanceTo(B);
        double b = B.distanceTo(C);
        double c = C.distanceTo(A);
        double p = (a+b+c)/2;
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));

    }
}
