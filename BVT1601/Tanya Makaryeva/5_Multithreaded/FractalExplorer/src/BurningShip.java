import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    public int numIterations(double x, double y) {
        int i = 1;

        for (double Rez = 0, Imz = 0; (i < MAX_ITERATIONS) && (Rez * Rez + Imz * Imz < 4.0D); ++i) {
            double RezUpdated = Rez * Rez - Imz * Imz + x;
            double ImzUpdated = 2 * Math.abs(Rez)* Math.abs(Imz) + y;
            Rez = RezUpdated;
            Imz = ImzUpdated;
        }
        /*
         * возвращаем кол-во итераций или -1, если достигли лимита
         * */
        return i == MAX_ITERATIONS ? -1 : i;
    }

    /*
    * возвращаем название фрактала для ComboBox
    * */
    @Override
    public String toString() {
        return "Burning Ship";
    }
}
