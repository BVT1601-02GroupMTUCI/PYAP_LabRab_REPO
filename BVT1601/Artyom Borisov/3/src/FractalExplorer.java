import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.*;


public class FractalExplorer extends JFrame {
    private int displaySize;
    private JImageDisplay imageDisplay;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public static void main(String[] args) {
        FractalExplorer displayExplorer = new FractalExplorer(500);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }

    private FractalExplorer(int size) {
        displaySize = size;
        imageDisplay = new JImageDisplay(displaySize, displaySize);
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double(0,0,0,0);
        fractal.getInitialRange(range);

    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");
        // создаем кнопку для очистки области
        JButton ClearButton = new JButton("Clear");
        // подключаем слушатель к кнопке
        ClearButton.addActionListener(e -> {
            fractal.getInitialRange(range);
            imageDisplay.clearImage();
            drawFractal();
        });
        // располагаем кнопку снизу
        frame.add(ClearButton, BorderLayout.SOUTH);

        MouseHandler click = new MouseHandler();
        imageDisplay.addMouseListener(click);

        frame.add(imageDisplay, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal() {
        // Цикл по пикселям
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {
                //Текущие
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);


                int iteration = fractal.numIterations(xCoord, yCoord);

                int pxColor = 0; // Black - default

                if (iteration == -1) {
                    imageDisplay.drawPixel(x, y, pxColor);
                } else {
                    float hue = 0.7f + (float) iteration / 200f;
                    pxColor = Color.HSBtoRGB(hue, 1f, 1f);

                    //обновление дисплея (с новым цветом пикселя)
                    imageDisplay.drawPixel(x, y, pxColor);
                }
            }
        }
        imageDisplay.repaint();
    }

    private class MouseHandler extends MouseAdapter {
        private final double SCALE = 0.5;

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            //Читаем координаты клика
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, SCALE);
            imageDisplay.clearImage();
            drawFractal();
        }
    }
}
