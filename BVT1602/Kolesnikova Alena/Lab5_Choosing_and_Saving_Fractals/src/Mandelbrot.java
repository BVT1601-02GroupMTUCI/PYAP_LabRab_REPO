import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{
    private static final int MAX_ITERATIONS = 2000;
    @Override
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }
    @Override
    public int numIterations(double x, double y) {
        int count = 0;
        double re = x;
        double im = y;
        double nextRe;
        double nextIm;
        while (count < MAX_ITERATIONS)
        {
            nextRe = x + (re * re - im * im);
            nextIm = y + (2 * im * re);
            re = nextRe;
            im = nextIm;
            count++;
            if (re * re + im * im > 4)
                return count;
        }
        return -1;
    }
    @Override
    public String toString(){
        return "Mandelbrot";
    }

}
