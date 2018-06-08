import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO.*;
import java.awt.image.*;


//Этот класс позволяет исследовать различные части фрактала с помощью
//создания и отображения Swing GUI и обрабатывает события, 
//вызванные различными взаимодействиями пользователей.

public class FractalExplorer
{
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    //Конструктор, который принимает Размер отображения, сохраняет его и
	//инициализирует объекты диапазона и фрактала-генератора.
    public FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
        
    }
    
	//Этот метод инициализирует GUI jframe содержащий
	//JImageDisplay объект и кнопка для сброса дисплея, кнопка
	//для сохранения текущего фрактального изображения и JComboBox для выбора
	//типа фрактала
     */
    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame myFrame = new JFrame("Fractal Explorer");
        myFrame.add(display, BorderLayout.CENTER);
        JButton resetButton = new JButton("Reset");
        
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JComboBox myComboBox = new JComboBox();
        
		// добавить фрактал в ComboBox
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);
        
        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);
        
        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        myFrame.add(myPanel, BorderLayout.NORTH);
        
        JButton saveButton = new JButton("Save");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        myFrame.add(myBottomPanel, BorderLayout.SOUTH);
        
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);
        
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setResizable(false);
        
    }
    
//Частный вспомогательный метод для отображения фрактала.
//Этот метод выполняет цикл через каждый пиксел на дисплее и вычисляет количество 
//итерации для соответствующих координат в области отображения фрактала. 
//Если число итераций равно -1, задайте для пикселя черный цвет.
//В противном случае выберите значение на основе числа итераций.
//Обновите дисплей цветом для каждого пикселя и перекрасьте
//JImageDisplay, когда все пиксели были нарисованы.
     */
    private void drawFractal()
    {
	// вывести каждый пиксель на дисплей
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                
                double xCoord = fractal.getCoord(range.x,
                range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y,
                range.y + range.height, displaySize, y);

                //Вычислить количество итераций для координат в области отображения фрактала.
                int iteration = fractal.numIterations(xCoord, yCoord);
				
                //Если число итераций равно -1, задайте для пикселя черный цвет.
                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                }
                
                else {
                     //В противном случае выберите значение оттенка на основе числа итераций. 
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                
                    //обновить на дисплее цвета каждого пикселя
                    display.drawPixel(x, y, rgbColor);
                }
                
            }
        }
//Когда все пиксели будут отрисованы, перекрасьте JImageDisplay
// в соответствии с текущим содержимым изображения.
        display.repaint();
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();

             //Если источником является поле со списком, получите фрактал,
			//выбранный пользователем, и отобразите его.
            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                fractal = (FractalGenerator) mySource.getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();
                
            }
 
            else if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
            //Если источником является кнопка сохранить,
			//сохраните текущее фрактальное изображение.
            else if (command.equals("Save")) {
                
                //Позволяет пользователю выбрать файл для сохранения изображения.
                JFileChooser myFileChooser = new JFileChooser();
                
                //сохраниить изображение
                FileFilter extensionFilter =
                new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);

                myFileChooser.setAcceptAllFileFilterUsed(false);
                
                //Всплывает окно "сохранить файл", которое позволяет
				//пользователю выбрать каталог и файл для сохранения.
                int userSelection = myFileChooser.showSaveDialog(display);
                
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    
                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();
                    
                    //сохранить изображение на диск
                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }
                    //Перехватывает все исключения и печатает сообщение с исключением.
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                        exception.getMessage(), "Cannot Save Image",
                        JOptionPane.ERROR_MESSAGE);
                    }
                }
                else return;
            }
        }
    }
    

    private class MouseHandler extends MouseAdapter
    {
        //Когда обработчик получает событие щелчка мыши, он отображает
		//координаты пиксела щелчка в область фрактала, который отображается,
		//а затем вызывает recenterAndZoomRange генератора(метод с координатами,
		//которые были нажаты и 0,5 масштаба.
        @Override
        public void mouseClicked(MouseEvent e)
        {
            /**Получить координату x области отображения щелчка мыши.**/
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,
            range.x + range.width, displaySize, x);
            
            /** Получить координату у области отображения щелчка мыши. **/
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,
            range.y + range.height, displaySize, y);
            
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

	//Статический метод main() для запуска FractalExplorer.
	//Инициализирует новый экземпляр FractalExplorer с размером экрана 600,
	//вызывает createAndShowGUI() для объекта explorer, а затем вызывает drawFractal () 
	//для просмотра начального представления.

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}