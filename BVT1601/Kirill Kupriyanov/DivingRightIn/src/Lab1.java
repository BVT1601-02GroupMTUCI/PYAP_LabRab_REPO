import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
public class Lab1 {
    static ArrayList<Point3D> PointList = new ArrayList<>();


    public static void main(String[] args) {
        /*
          ПРИМЕР:
          Найти площадь треугольника, координаты вершин которого известны:
          A(1, 2, 3)
          B(-4, 6, 3)
          C(7, -4, -5)
          Площадь равна: 25,787
         */
        Scanner reader = new Scanner(System.in);
        double x=0, y=0, z=0;
        char Point = 'D';
        for(int i=0; i<3;i++){
            switch(i)
            {
                case 0:System.out.println("Введите координаты точки A");
                    Point = 'A';
                    break;
                case 1:System.out.println("Введите координаты точки B");
                    Point = 'B';
                    break;
                case 2:System.out.println("Введите координаты точки C");
                    Point = 'C';
                    break;
            }
            System.out.print("Точка " + Point + "-X: ");
            x = reader.nextDouble();
            System.out.print("Точка " + Point + "-Y: ");
            y = reader.nextDouble();
            System.out.print("Точка " + Point + "-Z: ");
            z = reader.nextDouble();
            PointList.add(new Point3D(x,y,z));
        }
        reader.close();

        if (!(PointList.get(0).comparePoint(PointList.get(1)) || PointList.get(1).comparePoint(PointList.get(2)) ||PointList.get(0).comparePoint(PointList.get(2))))
        {
            double a=PointList.get(0).distanceTo(PointList.get(1));
            double b=PointList.get(1).distanceTo(PointList.get(2));
            double c=PointList.get(0).distanceTo(PointList.get(2));
            System.out.println("Длина сторон треугольника, построенного по этим точкам равна: \na:"+a+"\nb:"+b+"\nc:"+c);
            System.out.println("Площадь треугольника равна: "+computeArea(a,b,c));

        }
        else System.out.println("Введены одинаковые точки, программа будет завершена!");
    }


    public static double computeArea(double a,double b,double c){
        double  s = (a + b + c)/2.0d,
                x = (s * (s-a) * (s-b) * (s-c));
        return Math.sqrt(x);
    }
}