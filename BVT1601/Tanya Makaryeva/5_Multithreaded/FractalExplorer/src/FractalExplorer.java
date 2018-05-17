import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class FractalExplorer extends JFrame {
    private int displaySize;            // размер отображаемой области
    private JImageDisplay imageDisplay; // ссылка на отображения
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    private int rowsRemainig;

    JButton resetButton;
    JButton saveButton;
    JComboBox comboBox;

    public static void main(String[] args) {
        FractalExplorer displayExplorer = new FractalExplorer(500);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }

    /*
     * конструктор принимает размер окна
     * и инициализирует свои внутренние поля
     * */
    private FractalExplorer(int size) {
        displaySize = size;
        imageDisplay = new JImageDisplay(displaySize, displaySize);
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double(0, 0, 0, 0);
        fractal.getInitialRange(range);

    }

    private void enableUI(boolean val) {
        resetButton.setEnabled(val);
        saveButton.setEnabled(val);
        comboBox.setEnabled(val);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");


        // создаём панель, помещаем в нее JLabel и JComboBox
        JPanel downPanel = new JPanel();
        // создаем кнопку для очистки области
        resetButton = new JButton("Reset Display");
        saveButton = new JButton("Save Fractal");

        // подключаем слушатель к кнопке с помощью лямбда-выражения
        saveButton.addActionListener(e -> {
            /*
             * JFileChooser предоставляет возможность выбора файла или директории
             * */
            JFileChooser chooser = new JFileChooser();

            /*
             * сохранение в формате png
             * */
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);

            /*
             * выбор файла в диалоговом окне прошел успешно; выбранный файл можно получить методом getSelectedFile();
             * */
            if (chooser.showSaveDialog(imageDisplay) == JFileChooser.APPROVE_OPTION) {

                /*
                 * getSelectedFile получаем ссылку на объект File и затем продолжаем с ним работать
                 * */
                File file = chooser.getSelectedFile();

                /* удачная попытка сохранения */
                try {
                    BufferedImage displayImage = imageDisplay.getImage();
                    javax.imageio.ImageIO.write(displayImage, "png", file);
                } catch (Exception exception) {
                    /* неудачная попытка сохранения */
                    JOptionPane.showMessageDialog(imageDisplay, exception.getMessage(), "Cannot Save Image",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else return;
        });

        // подключаем слушатель к кнопке с помощью лямбда-выражения
        resetButton.addActionListener(e -> {
            fractal.getInitialRange(range);
            imageDisplay.clearImage();
            drawFractal();
        });
        // располагаем кнопку снизу экрана
        downPanel.add(resetButton);
        downPanel.add(saveButton);

        MouseHandler click = new MouseHandler();
        imageDisplay.addMouseListener(click);

        //создание эл-та JComboBox и добавление в него фракталов
        comboBox = new JComboBox();
        FractalGenerator fractalGenerator = new Mandelbrot();
        comboBox.addItem(fractalGenerator);
        fractalGenerator = new Tricorn();
        comboBox.addItem(fractalGenerator);
        fractalGenerator = new BurningShip();
        comboBox.addItem(fractalGenerator);

        // подключаем слушатель к JComboBox с помощью лямбда-выражения
        comboBox.addActionListener(e -> {
            JComboBox source = (JComboBox) e.getSource();
            fractal = (FractalGenerator) source.getSelectedItem();
            fractal.getInitialRange(range);
            drawFractal();
        });

        // создаём панель, помещаем в нее JLabel и JComboBox
        JPanel upPanel = new JPanel();
        JLabel label = new JLabel("Fractal:");
        upPanel.add(label);
        upPanel.add(comboBox);

        // располагаем панель сверху
        frame.add(upPanel, BorderLayout.NORTH);

        // располагаем панель снизу
        frame.add(downPanel, BorderLayout.SOUTH);

        // располагаем отображение в центре
        frame.add(imageDisplay, BorderLayout.CENTER);

        // закрытие приложение при нажатии на крестик в правом верхнем углу экрана
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // минимально упаковываем контейнер
        frame.pack();
        // отображаем окно
        frame.setVisible(true);
        // отключаем возможность изменения размеров окна
        frame.setResizable(false);
    }

    /**
     * метод просматривает каждый пиксель на дисплее и вычисляет количество
     * итерации для соответствующих координат в области отображения фрактала.
     * если кол-во итераций = -1, окрашивает пиксель в черный.
     * иначе окрашиваем в цвет, основанный на кол-ве итераций.
     * перерисовывает JImageDisplay.
     */
    private void drawFractal() {

        // отключаем всем виджеты перед началом перерисовки
        enableUI(false);
        rowsRemainig = displaySize;

        for (int row = 0; row < displaySize; row++) {
            FractalExplorer.FractalWorker row2 = new FractalExplorer.FractalWorker(row);
            row2.execute();
        }
    }

    private class MouseHandler extends MouseAdapter {
        private final double SCALE = 0.5;

        /**
         * Когда обработчик получает событие щелчка мыши, он отображает пиксель-
         * координаты щелчка в область фрактала, которая
         * отображается, а затем вызывает recenterAndZoomRange () с координатами, которые были нажаты, и масштабом 0,5.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            /** поучаем координаты клика мышью по фракталу */
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

            /**
             * Вызвать метод recenterAndZoomRange () с координатами, клика мышью, и масштабом 0,5.
             */
            fractal.recenterAndZoomRange(range, xCoord, yCoord, SCALE);
            imageDisplay.clearImage();

            /**
             * Перерисовать фрактал после изменения отображаемой области.
             */
            drawFractal();
        }
    }

    /*
     * обобщённый класс FractalWorker наследуется от обобщённого абстрактного класса SwingWorker
     * и является внутренним классом(inner class) для FractalExplorer
     * параметры не используются, поэтому указаны как Object
     * */
    public class FractalWorker extends SwingWorker<Object, Object> {

        private int yyCoord;
        private int[] rgbRow;

        FractalWorker(int y) {
            yyCoord = y;
        }

        @Override
        protected Object doInBackground() {
            rgbRow = new int[displaySize];

            for (int i = 0; i < displaySize; i++) {
                /*
                 * текущие координаты
                 * */
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, i);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, yyCoord);

                /*
                 * вычисляем кол-во итераций для соответствующих координат в области отображения фрактала
                 * */
                int iteration = fractal.numIterations(xCoord, yCoord);

                int pxColor = 0; // цвет по умолчанию - чёрный
                /*
                 * если число итераций -1, то окрашиваем в такой пиксель в черный
                 * */
                if (iteration == -1) {
                    rgbRow[i] = pxColor;
                } else {
                    // иначе выбираем оттенок, основанный на кол-ве итераций
                    float hue = 0.7f + (float) iteration / 200f;
                    pxColor = Color.HSBtoRGB(hue, 1f, 1f);

                    /** обновление дисплея новым цветом для каждого пикселя */
                    rgbRow[i] = pxColor;
                }

            }
            return null;
        }

        @Override
        protected void done() {
            //super.done();
            for(int x = 0; x < rgbRow.length; x++) {
                imageDisplay.drawPixel(x, yyCoord, rgbRow[x]);

                /*
                * первый параметр long - неиспользуемый, заполняем 0
                * начало (0,y) - конец (displaySize,1)
                * */
                imageDisplay.repaint(0,0,yyCoord,displaySize,1);

                rowsRemainig--;
                if(rowsRemainig ==0)
                    enableUI(true);
            }
        }
    }
}
