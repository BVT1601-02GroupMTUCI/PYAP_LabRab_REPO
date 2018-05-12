import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
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
	public int numIterations(double x, double y)// просчитывает кол-во итераций и просчитывание фрактала
	{
		int countIteration = 0;
		double Re = x;
		double Im = y;
		double nextRe;
		double nextIm;
		while (countIteration<MAX_ITERATIONS)
		{
			nextRe = x+(Re*Re-Im*Im);
			nextIm = y +2*Re*Im;
			Re = nextRe;
			Im = nextIm;
			countIteration++;
			if ((Re*Re+Im*Im)>4)
				return countIteration;
		}
		return -1;
	}
	@Override
	public String toString() {
		return "Mandelbrot";
	}
}
