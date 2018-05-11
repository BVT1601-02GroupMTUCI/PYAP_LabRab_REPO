import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Laba_1 {
    static ArrayList<Point3D> PList = new ArrayList<>();

    public static void main(String[] args) {
        /**
          ПРИМЕР:
          Найти площадь треугольника, координаты вершин которого известны:
          A(5, 4, 1);
          B(-10, 6, 1);
          C(1, -7, -3).
          Площадь равна: 91.64
         */
        Scanner Scan = new Scanner(System.in);
        double x, y, z;
        for(int i = 0; i < 3; i++){
            if(i < 2){
                System.out.println("Введите координаты " + (i + 1) + "-ой точки: ");
            }
            if(i == 2){
                System.out.println("Введите координаты 3-ей точки: ");
            }
            System.out.print("X: ");
            x = Scan.nextDouble();
            System.out.print("Y: ");
            y = Scan.nextDouble();
            System.out.print("Z: ");
            z = Scan.nextDouble();
            PList.add(new Point3D(x,y,z));
        }
        Scan.close();

        if (!(PList.get(0).comparePoint(PList.get(1))
                || PList.get(1).comparePoint(PList.get(2))
                || PList.get(0).comparePoint(PList.get(2)))) {
            double a=PList.get(0).distanceTo(PList.get(1));
            double b=PList.get(1).distanceTo(PList.get(2));
            double c=PList.get(0).distanceTo(PList.get(2));
            System.out.println("Длина сторон треугольника: \nAB:" + a + "\nBC:" + b + "\nAC:" + c);
            System.out.println("Площадь треугольника равна: " + computeArea(a,b,c));
        }
        else{
            System.out.println("Введены одинаковые точки, программа будет завершена!");
        }
    }

    public static double computeArea(double a, double b, double c) {
        double p = (a + b + c) / 2.d,
                space = (p * (p-a) * (p-b) * (p-c));
        return Math.sqrt(space);
    }
}
