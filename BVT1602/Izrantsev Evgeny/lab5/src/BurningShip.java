import java.awt.*;
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
	public int numIterations(double x, double y) // подсчитывает количество итераций и фрактал
	{
		int countIteration = 0;
		double Re = x;
		double Im = y;
		double newRe;
		double newIm;
		while (countIteration<MAX_ITERATIONS)
		{
			newRe = x+(Re*Re-Im*Im);
			newIm = y +2* Math.abs(Re)*Math.abs(Im);
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
		return "Burning Ship";
	}
}
