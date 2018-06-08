
        import java.io.*;
        import java.util.Scanner;
        import java.util.ArrayList;

public class Lab1 {
    static ArrayList<Point3d> Points_List = new ArrayList<>();


    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        double x=0,y=0,z=0;
        for(int i=0;i<3;i++){
            System.out.println("Введите координаты точки №"+ i );
            System.out.print("координата x= ");
            x = reader.nextDouble();
            System.out.print("координата y= ");
            y = reader.nextDouble();
            System.out.print("координата z= ");
            z = reader.nextDouble();
            Points_List.add(new Point3d(x,y,z));
        }
        reader.close();

        if (!(Points_List.get(0).comparePoint(Points_List.get(1)) || Points_List.get(1).comparePoint(Points_List.get(2)) || Points_List.get(0).comparePoint(Points_List.get(2))))
        {
            double a= Points_List.get(0).distanceTo(Points_List.get(1));
            double b= Points_List.get(1).distanceTo(Points_List.get(2));
            double c= Points_List.get(0).distanceTo(Points_List.get(2));

            System.out.println("Длины сторон треугольника: \na:"+a+"\nb:"+b+"\nc:"+c);
            System.out.println("Площадь треугольника: "+ Find_Area(a,b,c));

        }
        else System.out.println("Введённые точки совпадают!");


    }
    public static double Find_Area(double a, double b, double c){


        double s = (a + b + c)/2.0d;
        double x = (s * (s-a) * (s-b) * (s-c));
        double Triangle_Area = Math.sqrt(x);
        return Triangle_Area;}
}