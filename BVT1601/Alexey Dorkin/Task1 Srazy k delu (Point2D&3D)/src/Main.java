import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static ArrayList<Point3d> PointList = new ArrayList<>();

    public static void main(String[] args) {
        /**ПРИМЕР: Найти площадь треугольника, координаты вершин которого известны: A(-2, 8, 2); B(6, 8, 0); C(7, 0, 3). Площадь равна:35.38*/
        Scanner reader = new Scanner(System.in);
        double x, y, z;
        for(int i=0;i<3;i++){
            System.out.println("Введите координаты "+ (i+1) +"-ой точки: ");
            System.out.print("X: ");
            x = reader.nextDouble();
            System.out.print("Y: ");
            y = reader.nextDouble();
            System.out.print("Z: ");
            z = reader.nextDouble();
            PointList.add(new Point3d(x,y,z));
        }
        reader.close();

        if (!(PointList.get(0).comparePoint(PointList.get(1)) || PointList.get(1).comparePoint(PointList.get(2)) || PointList.get(0).comparePoint(PointList.get(2)))) {
            double a=PointList.get(0).distanceTo(PointList.get(1));
            double b=PointList.get(1).distanceTo(PointList.get(2));
            double c=PointList.get(0).distanceTo(PointList.get(2));
            System.out.println("Длина сторон треугольника: \nAB:"+a+"\nBC:"+b+"\nAC:"+c);
            System.out.println("Sтр равна: "+computeArea(a,b,c));
        }
        else
            System.out.println("Введены одинаковые точки, программа будет завершена!");
    }
    public static double computeArea(double a, double b, double c) {
        double p = (a + b + c) / 2;
        double s = (p * (p-a) * (p-b) * (p-c));
        return Math.sqrt(s);
    }
}