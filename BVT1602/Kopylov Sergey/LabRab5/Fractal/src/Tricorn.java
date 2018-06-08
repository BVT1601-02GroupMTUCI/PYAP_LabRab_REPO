import java.awt.geom.Rectangle2D;

/**
 * класс наследник от FractalGenerator.  Нужен чтобы посчитать фрактал Мальдеброта
 */
public class Tricorn extends FractalGenerator
{

    public static final int MAX_ITERATIONS = 2000;

    /**
     * Метод позволяет фрактальному генератору определять,
     * какая часть комплексной плоскости является самой "интересной" для определенного фрактала.
     * Реализация этого метода должна установить начальный диапазон в (-2 - 1.5i) - (1 + 1.5i).
     * Т.е. x и значения y будут -2 и -2 соответственно, и ширина и высота будут равняться 4.
     */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }

    /**
     *Метод реализует итеративную функцию для фрактала Мальдерброта.
     */
    public int numIterations(double x, double y)
    {
        int iterator = 0;

        /**
         Вычисляем Zn = Zn-1 ^ 2 + c, где значения представляют собой комплексные числа,
         Z0 = 0, а c - конкретная точка фрактала, которую мы показываем (заданная x и y).
         Он повторяется до тех пор, пока Z ^ 2> 4 (абсолютное значение Z больше 2) или не будет достигнуто максимальное количество итераций.
         */

        for(double Rez=0,Imz = 0;iterator < MAX_ITERATIONS && Rez * Rez + Imz * Imz < 4.0D; ++iterator) {
            double RezUpdated = Rez * Rez - Imz * Imz + x;
            double ImzUpdated = -2 * Rez * Imz + y;
            Rez = RezUpdated;
            Imz = ImzUpdated;
        }
/**
 *Если число максимальных итераций достигнуто, верните -1, чтобы указать, что точка не вышла за пределы границы.
 */
        return iterator == MAX_ITERATIONS ? -1 : iterator;

    }
    /**
  * Возвращаетназвание фрактала: «Трикорн».
        */
    public String toString() {
        return "Tricorn";
    }

}
