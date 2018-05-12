import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {

    private int displaySize;
    private JImageDisplay image;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int displaySize){
        this.displaySize = displaySize;
        range = new Rectangle2D.Double();
        fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(range);
    }
    public void createAndShowGUI()
    {
        JFrame frame = new JFrame ("Fractal Explorer");
        image = new JImageDisplay(displaySize,displaySize);
        JButton button = new JButton("Сброс");
        button.addActionListener(new ButtonClick());
        image.addMouseListener(new Zoom());
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(image, BorderLayout.CENTER);
        frame.add(button,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    private void drawFractal() //отрисовывает фрактал
    {
        for (int y = 0; y < displaySize; y++)
            for (int x = 0; x < displaySize; x++)

            {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                int countIt = fractalGenerator.numIterations(xCoord, yCoord);
                if (countIt == -1) image.drawPixel(x, y, 0);
                else {
                    float hue = 0.7f + (float) countIt / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    image.drawPixel(x, y, rgbColor);
                }
            }

        image.repaint();
    }
    private class ButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fractalGenerator.getInitialRange(range);
            drawFractal();
        }
    }

    private class Zoom extends MouseAdapter{ // по клику мыши определяет координаты и пересчитывает диапозон, произведя приближение в 2 раза
        @Override
        public void mouseClicked(MouseEvent e){
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
            fractalGenerator.recenterAndZoomRange(range,xCoord,yCoord,0.5);
            drawFractal();
        }

    }

    public static void main(String[] args)
    {
        FractalExplorer fractalExplorer = new FractalExplorer(500);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}
