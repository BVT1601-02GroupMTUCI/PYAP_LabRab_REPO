public class Aplication {
    public void run() {
    point3D p1 = new point3D(1,2,3);
    point3D p2 = new point3D(2,7,9);
    point3D p3 = new point3D(4,5,8);
    double p = point3D.heron(p1,p2,p3);
    System.out.println(p);
    }
    public static void main(String[] args){
    Aplication aplication = new Aplication();
    aplication.run();
    }
}