import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

class JImageDisplay extends JComponent
{
    /**
     * создаем переменную типа Buffered Image.
     * для созранения изображения.
     */ 
    private BufferedImage ShowImage;
    
    /**
     Конструктор JImageDisplay должен получить целочисленную ширину и высоту и инициализировать этими значениями BufferedImage,
     чтобы быть новым изображением указанной ширины и высоты.
      */
    public JImageDisplay(int width, int height) {
        ShowImage = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
        
        /** 
         * Вызываем метод родительского класса setPreferredSize()
         * передав ширину и высоту (для вывода изображения на экран, задаем рамеры области отображения)
         */
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
        
    }
    public BufferedImage getImage() {
        return ShowImage;
    }   //метод, возвращающий изображение
    /**
     * переопределяем paintComponent(g) родительского класса чтобы он нарисовал нам изображение в окне.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); //вызываем родительский метод
        /*передаем методу Drawimage параметры
         *ShowImage-изображение для отрисовки
         * x,y - позиция начала отрисовки (верхний левый угол области)
         * размеры изображения по горизонтали/вертикали
         * слушателя(observer) не устанавливаем*/
        g.drawImage(ShowImage, 0, 0, ShowImage.getWidth(),
        ShowImage.getHeight(), null);
    }
    /**
     * делает все пиксели изображения черными.
     */
    public void clearImage()
    {
        //цикл по ширине/высоте изображения(по всем пикселям)
        for (int i = 0; i < ShowImage.getWidth(); i++) {
            for (int j = 0; j < ShowImage.getHeight(); j++) {
                ShowImage.setRGB(i, j, 0); //делаем текущий пиксель черным
            }
        }

    }
    /**
     * устанавливает цвет отдельного пикселя
     */
    public void drawPixel(int x, int y, int rgbColor)
    {
        ShowImage.setRGB(x, y, rgbColor);
    }
}