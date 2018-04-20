import java.util.Scanner;

public class lab1 {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);

        Point3d[] points = new Point3d[3];
        for(int i = 0; i < 3; i++){
            System.out.println("Enter coords " + i + " point \\/ \\/ \\/ (via Enter)");
            points[i] = new Point3d(in.nextDouble(), in.nextDouble(), in.nextDouble());
        }

        System.out.println("res: " + coumputeArea(points[0], points[1], points[2]));
    }
    public static double coumputeArea(Point3d ... points ){
        if(points.length != 3) return -1;
        for(int i = 0; i < 3-1; i++){
            for(int ii = i+1; ii < 3; ii++){
                if(points[i].equals(points[ii])) return -2;
            }
        }
        double a = points[0].distanceTo(points[1]); // 1 side triangle
        double b = points[0].distanceTo(points[2]); // 2 side triangle
        double c = points[1].distanceTo(points[2]); // 3 side triangle

        double p = (a+b+c)/2; //semiperimeter

        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }

}
