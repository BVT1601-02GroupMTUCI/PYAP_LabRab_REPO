import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay image;
    private FractalGenerator mandelbrot;
    private Rectangle2D.Double range;

    public FractalExplorer(int displaySize){
        this.displaySize = displaySize;
        range = new Rectangle2D.Double();
        mandelbrot = new Mandelbrot();
        mandelbrot.getInitialRange(range);
    }

    public void createAndShowGUI(){
        JFrame frame = new JFrame("Fractal Explorer");
        image = new JImageDisplay(displaySize,displaySize);
        JButton button = new JButton("reset display");
        MouseHandler zoomAction = new MouseHandler();
        ActionHandler resetAction = new ActionHandler();
        button.addActionListener(resetAction);
        image.addMouseListener(zoomAction);
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(image, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        for (int x = 0; x<image.getWidth();x++){
            for (int y = 0; y<image.getHeight();y++){
                double xCoord = FractalGenerator.getCoord(range.x,range.x+range.width,displaySize,x);
                double yCoord = FractalGenerator.getCoord(range.y,range.y+range.height,displaySize,y);
                int numIters = mandelbrot.numIterations(xCoord,yCoord);
                if (numIters == -1){
                    image.drawPixel(x,y,0);
                }
                else {
                    float hue = 0.5f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    image.drawPixel(x, y, rgbColor);
                }
            }
        }
        image.repaint();
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mandelbrot.getInitialRange(range);
            drawFractal();
        }
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            double xCoord = FractalGenerator.getCoord(range.x,range.width,displaySize,e.getX());
            double yCoord = FractalGenerator.getCoord(range.y,range.height,displaySize,e.getY());
            mandelbrot.recenterAndZoomRange(range,xCoord,yCoord,0.5);
            drawFractal();
        }
    }

    public static void main(String[] args){
        FractalExplorer fe = new FractalExplorer(800);
        fe.createAndShowGUI();
        fe.drawFractal();
    }
}