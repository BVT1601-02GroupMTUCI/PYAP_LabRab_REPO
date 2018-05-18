import java.awt.geom.Rectangle2D;
/**
 * Этот класс предоставляет общий интерфейс и операций для фрактала
 * генераторы, которые можно просмотреть в Fractal Explorer.
 */
public abstract class FractalGenerator {
    /**
     * Какая-та статическая помогающая функция
     *
     * @param rangeMin the minimum value of the floating-point range
     * @param rangeMax the maximum value of the floating-point range
     * @param size the size of the dimension that the pixel coordinate is from.
     *        For example, this might be the image width, or the image height.
     * @param coord the coordinate to compute the double-precision value for.
     *        The coordinate should fall in the range [0, size].
     */
    public static double getCoord(double rangeMin, double rangeMax,
                                  int size, int coord) {
        assert size > 0;
        assert coord >= 0 && coord < size;
        double range = rangeMax - rangeMin;
        return rangeMin + (range * (double) coord / (double) size);
    }
    /**
     * Задает указанный прямоугольник, содержащий начальный диапазон, подходящий для генерируемого фрактала
     */
    public abstract void getInitialRange(Rectangle2D.Double range);
    /**
     * Обновление текущего диапазона для центрирования по указанным координатам и увелич \ уменьш по коэфиценту
     */
    public void recenterAndZoomRange(Rectangle2D.Double range,
                                     double centerX, double centerY, double scale) {
        double newWidth = range.width * scale;
        double newHeight = range.height * scale;
        range.x = centerX - newWidth / 2;
        range.y = centerY - newHeight / 2;
        range.width = newWidth;
        range.height = newHeight;
    }
    /**
     * Given a coordinate <em>x</em> + <em>iy</em> in the complex plane,
     * computes and returns the number of iterations before the fractal
     * function escapes the bounding area for that point.  A point that
     * doesn't escape before the iteration limit is reached is indicated
     * with a result of -1.
     */
    public abstract int numIterations(double x, double y);
}