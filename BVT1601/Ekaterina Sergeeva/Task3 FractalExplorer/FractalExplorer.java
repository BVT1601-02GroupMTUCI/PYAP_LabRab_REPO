import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer{
// класс позволяет исследовать различные части фрактала

    private JImageDisplay display;
    private int dSize;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double rect = new Rectangle2D.Double(0,0,0,0);
	
	public FractalExplorer(int size){
        dSize = size;
        display = new JImageDisplay(dSize, dSize);
        fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(rect);
    }
            
    public static void main(String[] args){
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }

    //cоздание окна с фракталом
    public void createAndShowGUI(){
	
        JFrame frame = new JFrame("Fractal Explorer");

        display.addMouseListener(new mouseListener());
        frame.add(display,BorderLayout.CENTER);

        JButton button = new JButton("Reset");
        button.addActionListener(new buttonListener());
        frame.add(button, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void drawFractal(){
	// вывести на экран фрактал
        for(int i = 0; i < dSize; i++){
			for (int j = 0; j < dSize; j++){
				double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, dSize, i);
				double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, dSize, j);
				int iterations = fractralGenerator.numIterations(xCoord, yCoord);
				if(iterations!=-1){
					float hue = 0.7f + (float) iterations / 200f;
					int color = Color.HSBtoRGB(hue, 1f, 1f);
					display.drawPixel(i, j, color);
				}else {
				display.drawPixel(i, j, 0);
				}
			}
         display.repaint();
		}
	}

    private class mouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {
            super.mouseClicked(event);
            int mouseX = event.getX();
            int mouseY = event.getY();

            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, dSize, mouseX);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, dSize, mouseY);

            fractralGenerator.recenterAndZoomRange(rect, xCoord, yCoord, 0.5);
            display.clearImage();
            drawFractal();
        }
    }
	
	  private class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            fractralGenerator.getInitialRange(rect);
            display.clearImage();
            drawFractal();
        }
    }
}