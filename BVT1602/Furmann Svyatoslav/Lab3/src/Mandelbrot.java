import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    private static int MAX_ITERATION = 2000;

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = range.height = 3;
    }

    @Override
    public int numIterations(double x, double y) {
        int iters = 0;
        double re = x, im = y;
        double nextRe, nextIm;
        while (iters < MAX_ITERATION){
            nextRe = x + (re*re - im*im);
            nextIm = y + (2*re*im);
            re = nextRe;
            im = nextIm;
            iters++;
            if (re*re+im*im > 4){
                return iters;
            }
        }
        return -1;
    }//нумерация итераций

}