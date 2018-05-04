import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

class JImageDisplay extends JComponent
{
    /**
     * создаем переменную Buffered Image.
     * для созранения в ней нашего изображения.
     */ 
    private BufferedImage displayImage;
    
    /**
     Конструктор JImageDisplay должен получить целочисленную ширину и высоту и инициализировать этими значениями BufferedImage,
     чтобы быть новым изображением указанной ширины и высоты, и типом изображения TYPE_INT_RGB.
      */
    public JImageDisplay(int width, int height) {
        displayImage = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
        
        /** 
         * Вызываем метод родительского класса setPreferredSize()
         * передав ширину и высоту (для вывода изображения на экран, задаем рамеры области отображения)
         */
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
        
    }
    /**
     * переопределяем paintComponent(g) родительского класса чтобы он нарисовал нам изображение в окне.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(),
        displayImage.getHeight(), null);
    }
    /**
     * делает все пиксели изображения черными.
     */
    public void clearImage()
    {
        for (int i = 0; i < displayImage.getWidth(); i++) {
            for (int j = 0; j < displayImage.getHeight(); j++) {
                displayImage.setRGB(i, j, 0);
            }
        }

    }
    /**
     * устанавливает цвет отдельного пикселя
     */
    public void drawPixel(int x, int y, int rgbColor)
    {
        displayImage.setRGB(x, y, rgbColor);
    }
}