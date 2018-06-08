import java.awt.geom.Rectangle2D;

//Ётот класс €вл€етс€ подклассом FractalGenerator.
//  »спользуетс€ дл€ вычислени€ фрактала тетрикорна.

public class Tricorn extends FractalGenerator
{
 
    public static final int MAX_ITERATIONS = 2000;
    
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }
    
    //Ётот метод реализует итерационную функцию на Tricorn fractal.
	//ќн принимает два двойника дл€ действительной и мнимой частей комплексной
	//плоскости и возвращает число итераций дл€ соответствующей координаты.
     */
    public int numIterations(double x, double y)
    {
        int iteration = 0;
        double zreal = 0;
        double zimaginary = 0;
        
        while (iteration < MAX_ITERATIONS &&
               zreal * zreal + zimaginary * zimaginary < 4)
        {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = -2 * zreal * zimaginary + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration += 1;
        }
        
        //≈сли достигнуто ћаксимальное число итераций,
		//возвратите -1, чтобы указать, что точка не вышла за пределы границы.
        if (iteration == MAX_ITERATIONS){
            return -1;
        }
        
        return iteration;
    }
    
    public String toString() {
        return "Tricorn";
    }
    
}