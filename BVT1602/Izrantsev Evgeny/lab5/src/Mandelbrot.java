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
	public int numIterations(double x, double y)// подсчитывает количество итераций и фрактал
	{
		int countIteration = 0;
		double Re = x;
		double Im = y;
		double newRe;
		double newIm;
		while (countIteration<MAX_ITERATIONS)
		{
			newRe = x+(Re*Re-Im*Im);
			newIm = y +2*Re*Im;
			Re = newRe;
			Im = newIm;
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
