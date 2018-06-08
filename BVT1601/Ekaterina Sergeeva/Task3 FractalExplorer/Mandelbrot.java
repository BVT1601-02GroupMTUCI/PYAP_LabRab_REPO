import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{
   
    public static final int MAX_ITERATIONS = 2000;
    
    public void getInitialRange (Rectangle2D.Double range){
	//метод позволяет определять, какая часть комплексной плоскости
	//является самой "интересной" для определенного фрактала
        range.x = -2;
        range.y = -1.5;
        range.width = range.height = 3;
    }

    //метод реализует итеративную функцию для фрактала Мальдерброта
    
    public int numIterations(double x, double y){
	
        double real = 0;
        double imaginary = 0;
        for (int i = 0; i < MAX_ITERATIONS; i++){
            double re1 = real * real - imaginary * imaginary + x;
            double im1 = 2 * real * imaginary + y;

            if ((imaginary * imaginary + real * real) > 4)
                return i;
            real = re1;
            imaginary = im1;
        }
        return -1;
    }

}