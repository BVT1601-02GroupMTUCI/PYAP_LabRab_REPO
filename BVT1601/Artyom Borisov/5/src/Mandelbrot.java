import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {

    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    public int numIterations(double x, double y) {
        int iterator = 0;

        for (double Rez = 0, Imz = 0; iterator < MAX_ITERATIONS && Rez * Rez + Imz * Imz < 4.0D; ++iterator) {
            double RezUpdated = Rez * Rez - Imz * Imz + x;
            double ImzUpdated = 2 * Rez * Imz + y;
            Rez = RezUpdated;
            Imz = ImzUpdated;
        }
        return iterator == MAX_ITERATIONS ? -1 : iterator;
    }

    public String toString () {
        return "Mandelbrot";
    }
}
