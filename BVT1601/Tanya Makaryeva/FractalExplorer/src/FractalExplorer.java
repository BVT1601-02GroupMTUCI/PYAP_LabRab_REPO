import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FractalExplorer extends JFrame {
    public static final int MAX_ITERATIONS = 2000;
    private int displaySize;            // размер отображаемой области
    private JImageDisplay imageDisplay; // ссылка на отображения
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public static void main(String[] args) {
        FractalExplorer displayExplorer = new FractalExplorer(500);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }

    /*
     * конструктор принимает размер окна
     * и инициализирует свои внутренние поля
     * */
    private FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        imageDisplay = new JImageDisplay(displaySize, displaySize);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");
        frame.setLayout(new BorderLayout());
        // располагаем отображение в центре
        frame.add(imageDisplay, BorderLayout.CENTER);
        // создаем кнопку для очистки области
        JButton resetButton = new JButton("Reset");
        // подключаем слушатель к кнопке
        resetButton.addActionListener(e -> {
            fractal.getInitialRange(range);
            drawFractal();
        });
        // располагаем кнопку снизу экрана
        frame.add(resetButton, BorderLayout.SOUTH);

        MouseHandler click = new MouseHandler();
        imageDisplay.addMouseListener(click);

        // закрытие приложение при нажатии на крестик в правом верхнем углу экрана
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // минимально упаковываем контейнер
        frame.pack();
        // отображаем окно
        frame.setVisible(true);
        // отключаем возможность изменения размеров окна
        frame.setResizable(false);
    }

    /**
     * метод просматривает каждый пиксель на дисплее и вычисляет количество
     * итерации для соответствующих координат в области отображения фрактала.
     * если кол-во итераций = -1, окрашивает пиксель в черный.
     * иначе окрашиваем в цвет, основанный на кол-ве итераций.
     * перерисовывает JImageDisplay.
     */
    private void drawFractal() {
        /*
         * цикл по пикселям изображения
         * */
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {

                /*
                 * текущие координаты
                 * */
                double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

                /*
                 * вычисляем кол-во итераций для соответствующих координат в области отображения фрактала
                 * */
                int iteration = fractal.numIterations(xCoord, yCoord);

                /*
                 * если число итераций -1, то окрашиваем в такой пиксель в черный
                 * */
                if (iteration == -1) {
                    imageDisplay.drawPixel(x, y, 0);
                } else {
                    // иначе выбираем оттенок, основанный на кол-ве итераций
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    /** обновление дисплея новым цветом для каждого пикселя */
                    imageDisplay.drawPixel(x, y, rgbColor);
                }

            }
        }
        // перерисовка окна
        imageDisplay.repaint();
    }

    class Mandelbrot extends FractalGenerator {
        public void getInitialRange(Rectangle2D.Double range) {
            range.x = -2;
            range.y = -1.5;
            range.width = range.height = 3;
        }

        public int numIterations(double x, double y) {
            int i = 0;
            double re = 0, im = 0;
            /*while (i < MAX_ITERATIONS || (re * re + im + im) < 4.) {
                i++;
                double rez = re * re - im * im + x;
                double imz = 2 * re * im + y;
                re = rez;
                im = imz;
            }*/
            for (double Rez = 0, Imz = 0; i < MAX_ITERATIONS && Rez * Rez + Imz * Imz < 4.0D; ++i) {
                double RezUpdated = Rez * Rez - Imz * Imz + x;
                double ImzUpdated = 2 * Rez * Imz + y;
                Rez = RezUpdated;
                Imz = ImzUpdated;
            }
            return i == MAX_ITERATIONS ? -1 : i;
        }

    }

    private class MouseHandler extends MouseAdapter {
        /**
         * Когда обработчик получает событие щелчка мыши, он отображает пиксель-
         * координаты щелчка в область фрактала, которая
         * отображается, а затем вызывает recenterAndZoomRange () с координатами, которые были нажаты, и масштабом 0,5.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            /** поучаем координаты клика мышью по фракталу */
            int x = e.getX();
            int y = e.getY();
            double xCoord = fractal.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = fractal.getCoord(range.y, range.y + range.height, displaySize, y);

            /**
             * Вызвать метод recenterAndZoomRange () с координатами, клика мышью, и масштабом 0,5.
             */
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            /**
             * Перерисовать фрактал после изменения отображаемой области.
             */
            drawFractal();
        }
    }
}
