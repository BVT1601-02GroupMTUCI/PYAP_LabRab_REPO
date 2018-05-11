import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import java.io.File;
import java.util.Objects;

/**
 * Этот класс позволяет исследовать различные части фрактала на
 * создание и отображение графического интерфейса Swing и обработка событий, вызванных различными
 * взаимодействие с пользователем.
 */
public class FractalExplorer
{
    /** размер дисплея - это ширина и высота отображения в пикселях.**/
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    /**
     Конструктор, который отображает размер экрана, сохраняет его и
     * инициализирует объектыфрактального генератора.
     */
    private FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    /**
     * Этот метод инициализирует графический интерфейс Swing с помощью JFrame,
     * Объект JImageDisplay и кнопку для сброса отображения.
     */
    private void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame myframe = new JFrame();

        myframe.add(display, BorderLayout.CENTER);  //располагаем область отображения по центру окна

        JButton resetButton = new JButton("Очистить");
        /* добавляем обработчик события на кнопку */
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);
        /* добавляем нашу кнопку "на ЮГ"(где NORTH,SOUTH,West, EAST -стороны окна), т.е. вниз */
        myframe.add(resetButton, BorderLayout.SOUTH);
        /* вешаем обраотчик события на клик в области отображения */
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        /* ДОП КОД */
        JComboBox myComboBox = new JComboBox();
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);


        ResetHandler fractalChooser = new ResetHandler();
        myComboBox.addActionListener(fractalChooser);

        /*
          Создаем панель(Элемент для группировки элементов управления) и размещаем там ComboBox, и Label
          расположение ставим NORTH-т.е. на верху окна
         */
        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Тип фрактала:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        myframe.add(myPanel, BorderLayout.NORTH);


        JButton saveButton = new JButton("Сохранить");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        myframe.add(myBottomPanel, BorderLayout.SOUTH);


        ResetHandler saveHandler = new ResetHandler();
        saveButton.addActionListener(saveHandler);

        /* устанавливаем по умолчаниюстандартную процедуру закрытия Jframe */
        myframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        myframe.pack();
		myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);
        myframe.setResizable(false);
    }

    /**
     * метод через каждый пиксель на дисплее и вычисляет количество
     * итерации для соответствующих координат в области отображения фрактала.
     * Если число итераций равно -1, установливает цвет пикселя в черный. В противном случае выберает значение, основанное на количестве итераций.
     *перерисовывает JImageDisplay.
     */
    private void drawFractal()
    {
        /* проходим по всем пикселям изображения */
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){
                /* Находим текущие координаты xCoord и yCoord */
                double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y,range.y + range.height, displaySize, y);

                int iteration = fractal.numIterations(xCoord, yCoord); //считаем количество итераций для отрисовки части фрактала в данных координатах

                /* Если число итераций равно -1, установите пиксель в черный.
                иначе выбор значения оттенка, основанн на количестве итераций.
                */
                if (iteration == -1)
                    display.drawPixel(x, y, 0);
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(x, y, rgbColor);  //обновить дисплей цветом для каждого пикселя.
                }

            }
        }
        display.repaint();
    }
    /**
     * Класс для обработки событий ActionListener из кнопки сброса.
     */
    private class ResetHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            /* определяем источник команды (какую кнопку нажали или тыкнули по комбобоксу). **/
            String command = e.getActionCommand();

            /* если событие произошло с ComboBox */
            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                fractal = (FractalGenerator) mySource.getSelectedItem();
                Objects.requireNonNull(fractal).getInitialRange(range);
                drawFractal();

            }
            /* Если была нажата кнопка ОЧИСТИТЬ. */
            else if (command.equals("Очистить")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
            /* Если нажали кнопку СОХРАНИТЬ */
            else if (command.equals("Сохранить")) {

                /* объект реализующий сохранение изображения. */
                JFileChooser myFileChooser = new JFileChooser();

                /* Сохраняем изображение как PNG. */
                FileFilter extensionFilter =new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);
                /* запрещаем выбирать не PNG */
                myFileChooser.setAcceptAllFileFilterUsed(false);

                /* получаем путь до директории, куда юзверь хочет сохранять. */
                int userSelection = myFileChooser.showSaveDialog(display);

                /* Если результатом операции выбора файла является APPROVE_OPTION, продолжайте операцию сохранения файла. */
                if (userSelection == JFileChooser.APPROVE_OPTION) {

                    /* Получаем файл и его имя. **/
                    java.io.File file = new File(myFileChooser.getSelectedFile() + ".png");

                    /* при удачном сохранениии файла на диск **/
                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }
                    /* При неудачном сохранении выводим на экран ошибку */
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                                exception.getMessage(), "Невозможно сохранить изоражение",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                /* Если результатом операции выбора файла является НЕ APPROVE_OPTION. */
            }
        }
    }

    private class MouseHandler extends MouseAdapter
    {
        /**
         * Когда обработчик получает событие щелчка мыши, он отображает пиксель-
         * координаты щелчка в область фрактала, которая
         * отображается, а затем вызывает recenterAndZoomRange () с координатами, которые были нажаты, и масштабом 0,5.
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            /* поучаем координаты клика мышью по фракталу */
            int x = e.getX(), y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y,range.y + range.height, displaySize, y);

            /*Вызвать метод recenterAndZoomRange () с координатами, клика мышью, и масштабом 0,5 */
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            /*Перерисовать фрактал после изменения отображаемой области */
            drawFractal();
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}