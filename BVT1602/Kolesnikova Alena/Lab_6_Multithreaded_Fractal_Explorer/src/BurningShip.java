import java.awt.geom.Rectangle2D;
public class BurningShip extends FractalGenerator {
    private static final int MAX_ITERATIONS = 2000;
    @Override
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
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
            nextIm = y + (2 * Math.abs(im) * Math.abs(re));
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
        return "Burning Ship";
    }
}
