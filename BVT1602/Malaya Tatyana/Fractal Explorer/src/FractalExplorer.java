import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
	private int displaySize;
	private JImageDisplay image;//
	private FractalGenerator fractalGen;
	private Rectangle2D.Double range;
	
	public FractalExplorer(int displaySize){
		this.displaySize = displaySize;
		range = new Rectangle2D.Double();
		fractalGen = new Mandelbrot();
		fractalGen.getInitialRange(range);
	}
	
	public void createAndShowGUI ()  // создание визуала и вывод его на экран
	{
		JFrame frame = new JFrame("Fractal Explorer");
		image = new JImageDisplay(displaySize,displaySize);
		JButton button = new JButton("Сброс");
		button.addActionListener(new ButtonClick());
		image.addMouseListener(new Zoom());
		frame.setLayout(new java.awt.BorderLayout());
		frame.add(image,BorderLayout.CENTER);
		frame.add(button,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	
	private void drawFractal() {
		
		for (int x=0;x<displaySize; x++)
    		for (int y=0; y<displaySize; y++)
    		{
    			double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
    			double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
    			int countIter = fractalGen.numIterations(xCoord, yCoord);
    			if (countIter == -1) image.drawPixel(x, y, 0);
    			else {
    				float hue = 0.7f + (float) countIter / 200f;
    				int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
    				image.drawPixel(x, y, rgbColor);

    			}	
    		}
		image.repaint();
	}
	private class ButtonClick implements ActionListener{// 
		
		@Override
		public void actionPerformed(ActionEvent e) {
			fractalGen.getInitialRange(range);
			drawFractal();
		}
	}
	private class Zoom extends MouseAdapter{     //класс считывает действие мыши(клик) и пересчитывает область для отрисовки фрактала
		@Override
		public void mouseClicked(MouseEvent e) {
			double xCoord = FractalGenerator.getCoord (range.x, range.x + range.width, displaySize, e.getX());
			double yCoord = FractalGenerator.getCoord (range.y, range.y + range.height, displaySize, e.getY());
			fractalGen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
			drawFractal();
		}
	}
	public static void main(String[] args) {
		FractalExplorer fractalExplorer = new FractalExplorer(800);
		fractalExplorer.createAndShowGUI();
		fractalExplorer.drawFractal();
	}
	
}
