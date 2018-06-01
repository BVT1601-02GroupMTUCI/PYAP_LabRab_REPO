import javax.imageio.ImageIO;
import javax.swing.*; 
import javax.swing.filechooser.FileFilter; 
import javax.swing.filechooser.FileNameExtensionFilter; 
import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class FractalExplorer {
	private int displaySize;
	private JImageDisplay image;//
	private FractalGenerator fractalGen;
	private Rectangle2D.Double range;
	private JComboBox<FractalGenerator> comboBox;
	
	public FractalExplorer(int displaySize){
		this.displaySize = displaySize;
		range = new Rectangle2D.Double();
		fractalGen = new Mandelbrot();
		fractalGen.getInitialRange(range);
	}
	
	public void createAndShowGUI ()  // создание окна и отображение в нем
	{
		JFrame frame = new JFrame("Fractal Explorer");
		image = new JImageDisplay(displaySize,displaySize);
		JButton button = new JButton("Сброс");
		JButton saveButton = new JButton("Сохранение");
		comboBox = new JComboBox<>();
		JLabel label = new JLabel ("Фрактал:");
		ActionListener click = new ButtonClick();
		button.setActionCommand("Сброс");
		saveButton.setActionCommand("Сохранить");
		button.addActionListener(click);
		comboBox.addActionListener(click); 
		saveButton.addActionListener(click);
		image.addMouseListener(new Zoom());
		JPanel panelS = new JPanel();
		JPanel panelN = new JPanel();
		comboBox.addItem(new Mandelbrot());
		comboBox.addItem(new Tricorn());
		comboBox.addItem(new BurningShip()); 
		frame.setLayout(new java.awt.BorderLayout());
		panelN.add(label);
		panelN.add(comboBox);
		panelS.add(button);
		panelS.add(saveButton);
		frame.add(image,BorderLayout.CENTER);
		frame.add(panelS,BorderLayout.SOUTH);
		frame.add(panelN,BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	
	private void drawFractal() { //отрисовка фрактала
		
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
	private class ButtonClick implements ActionListener{ // класс выполняет ряд действий
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()== comboBox)
			{
				fractalGen = (FractalGenerator)comboBox.getSelectedItem();
				fractalGen.getInitialRange(range);
				drawFractal();
			}
			else if (e.getActionCommand() == "Сброс") {
				fractalGen.getInitialRange(range);
				drawFractal();
			}
			else if (e.getActionCommand() == "Сохранить") {
				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showSaveDialog(image) == JFileChooser.APPROVE_OPTION) {
					try {
						ImageIO.write(image.image, "png", chooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(image, e1.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
					}
				}
				fractalGen.getInitialRange(range);
				drawFractal();
			}
		}
	}
	private class Zoom extends MouseAdapter{     //пересчитывает область отрисовки фрактала по клику мыши
		@Override
		public void mouseClicked(MouseEvent e) {
			double xCoord = FractalGenerator.getCoord (range.x, range.x + range.width, displaySize, e.getX());
			double yCoord = FractalGenerator.getCoord (range.y, range.y + range.height, displaySize, e.getY());
			fractalGen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
			drawFractal();
		}
	}
	public static void main(String[] args) {
		FractalExplorer fractalExplorer = new FractalExplorer(500);
		fractalExplorer.createAndShowGUI();
		fractalExplorer.drawFractal();
	}
	
}
