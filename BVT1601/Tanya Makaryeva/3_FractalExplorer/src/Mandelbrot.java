import java.awt.geom.Rectangle2D;

class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    public int numIterations(double x, double y) {
        int i = 1;

        for (double Rez = 0, Imz = 0; (i < MAX_ITERATIONS) && (Rez * Rez + Imz * Imz < 4.0D); ++i) {
            double RezUpdated = Rez * Rez - Imz * Imz + x;
            double ImzUpdated = 2 * Rez * Imz + y;
            Rez = RezUpdated;
            Imz = ImzUpdated;
        }
        /*
         * возвращаем кол-во итераций или -1, если достигли лимита
         * */
        return i == MAX_ITERATIONS ? -1 : i;
    }
}