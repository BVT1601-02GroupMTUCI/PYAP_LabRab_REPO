//import java.io.*;
import java.util.Scanner;


public class Lab1{
	public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Ââåäèòå êîîðäèíàòû ïåðâîé òî÷êè  (x y z): ");
        Point3D firstPoint = new Point3D(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.print("Ââåäèòå êîîðäèíàòû âòîðîé òî÷êè (x y z): ");
        Point3D secondPoint = new Point3D(in.nextDouble(),in.nextDouble(),in.nextDouble());
        System.out.print("Ââåäèòå êîîðäèíàòû òðåòüåé òî÷êè (x y z): ");
        Point3D thirdPoint = new Point3D(in.nextDouble(),in.nextDouble(),in.nextDouble());
        //System.out.println(firstPoint.distanceTo(secondPoint));
        double area = computeArea(firstPoint,secondPoint,thirdPoint);
        if (area!=0) System.out.println(area);

    }

	
	public static double computeArea(Point3D firstPoint,Point3D secondPoint, Point3D thirdPoint ) {
		if (firstPoint.equality(secondPoint)||secondPoint.equality(thirdPoint)||thirdPoint.equality(firstPoint))
		{

            System.out.println("Íåêîòîðûå òî÷êè ðàâíû.");
            return 0;
		}
		
		double f = firstPoint.distanceTo(secondPoint) ;
		double s = secondPoint.distanceTo(thirdPoint) ;
		double t = thirdPoint.distanceTo(firstPoint);
		double p = (f+s+t)/2;
		return Math.sqrt(p*(p-f)*(p-s)*(p-t));
	}}
