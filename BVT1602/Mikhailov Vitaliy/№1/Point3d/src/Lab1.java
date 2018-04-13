import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Point3d a = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble()); 
        Point3d b = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble()); 
        Point3d c = new Point3d(in.nextDouble(),in.nextDouble(),in.nextDouble());
        
        if(a.equality(b)||a.equality(c)||c.equality(b))
        {
            System.out.println("One or more points are the same");
            
        }
        else
        {
            System.out.println(computeArea(a, b, c));
            
        }
        
    
    }
    public static double computeArea(Point3d a, Point3d b, Point3d c)
    {
        double S, p;
        p = (a.distanceTo(b)+a.distanceTo(c)+c.distanceTo(b))/2;
        S = Math.pow(p*(p-a.distanceTo(b))*(p-a.distanceTo(c))*(p-c.distanceTo(b)), 0.5);
        return S;
        
    }
    
    
}
 