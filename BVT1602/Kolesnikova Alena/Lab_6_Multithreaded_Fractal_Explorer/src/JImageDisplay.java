import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends javax.swing.JComponent {
    public BufferedImage image;
    public JImageDisplay(int width, int height)
    {
        image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width,height));
    }
    @Override
    protected void paintComponent(Graphics g) // выводит на экран изображение, хранящееся в image
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
    public void clearImage() //Функция очищает изображение(перекрашивает все пиксели в черный)
    {
        for (int i=0;i<image.getHeight();i++)
            for (int j=0;j<image.getWidth();j++)
                drawPixel(i,j,0);
    }
    public void drawPixel(int x,int y, int rgbColor) //Функция красит пиксель с координатами x,y в выбранный цвет
    {
        image.setRGB(x,y,rgbColor);
    }
}

