import javax.swing.*;
import java.awt.image.*;
import java.awt.*;


//Этот класс позволяет нам отображать наши фракталы.
class JImageDisplay extends JComponent
{
    
     //Управляет образом, содержимое которого можно записать.   
	 private BufferedImage displayImage;
    
    //метод получает изображения из другого класса.
    public BufferedImage getImage() {
        return displayImage;
    }
    
    // Конструктор принимает целочисленные значения width и height
	//и инициализирует объект BufferedImage как новое изображение
	//с шириной height типа image TYPE_INT_RGB.
    public JImageDisplay(int width, int height) {
        displayImage = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
        
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
        
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(),
        displayImage.getHeight(), null);
    }
 
	//устанавливаются черные пикселы
    public void clearImage()
    {
        int[] blankArray = new int[getWidth() * getHeight()];
        displayImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }
  //устанавливаются остальные пикселы
    public void drawPixel(int x, int y, int rgbColor)
    {
        displayImage.setRGB(x, y, rgbColor);
    }
}