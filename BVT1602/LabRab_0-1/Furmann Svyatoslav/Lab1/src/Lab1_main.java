import java.io.*;
import java.util.Scanner;

public class Lab1_main {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите коорд точки A : x y z ");
        Point3d A3d = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.print("Введите коорд точки B : x y z ");
        Point3d B3d = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.print("Введите коорд точки C : x y z ");
        Point3d C3d = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.println(A3d.toString());
        System.out.println(A3d.distanceTo(B3d));
        double area = computeArea(A3d,B3d,C3d);
        if (area!=0) System.out.println(area);

    }
//System.in поток ввода с клавы в java
//System.out поток вывода в консоль java
//public statictic double тк в функции main ругается без него
    public static double computeArea(Point3d corA, Point3d corB, Point3d corC) {
        if(corA.equals(corB) || corA.equals(corC) || corB.equals(corC))
        //у треугольника точки А.В.С не могут быть равный и длина одной стороны не может быть больше суммы 2 других
        {
            System.out.println("Ошибка!Точки не могут совпадать");
            return 0;

        }


        double ab,bc,ca,p;
         ab = corA.distanceTo(corB);
         bc = corB.distanceTo(corC);
         ca = corC.distanceTo(corA);
         p = (ab+bc+ca)/2;
        //Heron`s formula
        return Math.sqrt(p*(p-ab)*(p-bc)*(p-ca));
    }
}
