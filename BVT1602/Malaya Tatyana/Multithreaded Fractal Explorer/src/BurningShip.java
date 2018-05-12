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
			nextIm = y +2* Math.abs(Re)*Math.abs(Im);
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
		return "Burning Ship";
	}
}
