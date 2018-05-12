import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent {
	public BufferedImage image;
    public JImageDisplay(int width, int height)
    {
    	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	super.setPreferredSize(new Dimension(width, height));
    }
    
    @Override
    protected void paintComponent(Graphics g) //вывод на экран изображения 
    { 
	    super.paintComponent(g); //
	    g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null); 
    } 
    public void clearImage() //очищает изображение(перекрашивает все пиксели в черный)
    { 
    	for (int i=0;i<image.getWidth(); i++)
    		for (int j=0; j<image.getHeight(); j++) 
    			image.setRGB(i, j, 0);
    } 
    public void drawPixel(int x,int y, int rgbColor) //функция задает цвет определенному пикселю 
    { 
    	image.setRGB(x, y, rgbColor);
    }
}
