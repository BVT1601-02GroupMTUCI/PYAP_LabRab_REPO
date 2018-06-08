import java.awt.*;
import javax.swing.filechooser.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.Rectangle2D;


public class FractalExplorer
{
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;
    private int rowsRemaining;
    private JComboBox myComboBox;

    private JButton saveBtn;
    private JButton resetBtn;



    public FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame myframe = new JFrame("Fractal Explorer");

        myframe.add(display, BorderLayout.CENTER);//располагаем областьотображения по центру окна

        this.resetBtn = new JButton("Очистить");

        ButtonHandler handler = new ButtonHandler();
        resetBtn.addActionListener(handler);
        myframe.add(resetBtn, BorderLayout.SOUTH);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        this.myComboBox = new JComboBox();
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);

        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);


        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Тип:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        myframe.add(myPanel, BorderLayout.NORTH);

        this.saveBtn = new JButton("Сохранить");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveBtn);
        myBottomPanel.add(resetBtn);
        myframe.add(myBottomPanel, BorderLayout.SOUTH);

        ButtonHandler saveHandler = new ButtonHandler();
        saveBtn.addActionListener(saveHandler);

        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myframe.pack();//метод pack() устанавливает такой минимальный размер контейнера, который достаточен для отображения всех компонентов.
        myframe.setVisible(true);
        myframe.setResizable(false);//убираем возможность расширения окна
    }
    private void enableUI(boolean var1) {
        this.myComboBox.setEnabled(var1);
        this.resetBtn.setEnabled(var1);
        this.saveBtn.setEnabled(var1);
    }

    private void drawFractal()
    {
        this.enableUI(false);
        this.rowsRemaining = this.displaySize;

        for(int var1 = 0; var1 < this.displaySize; ++var1) {
            FractalExplorer.FractalWorker var2 = new FractalExplorer.FractalWorker(var1);
            var2.execute();
        }

    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            String command = e.getActionCommand();

            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                fractal = (FractalGenerator) mySource.getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();

            }

            else if (command.equals("Очистить")) {
                fractal.getInitialRange(range);
                drawFractal();
            }

            else if (command.equals("Сохранить")) {

                JFileChooser myFileChooser = new JFileChooser();

                FileFilter extensionFilter =new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);
                myFileChooser.setAcceptAllFileFilterUsed(false);

                int userSelection = myFileChooser.showSaveDialog(display);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {

                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();

                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }

                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                                exception.getMessage(), "Error: Невозможно сохранить изоражение",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else return;
            }
        }
    }

    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,range.x + range.width, displaySize, x);
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,range.y + range.height, displaySize, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }

    private class FractalWorker extends SwingWorker<Object, Object>
    {
        int yCoordi;
        int[] RGBMASS;
        private FractalWorker(int yCoord)
        {this.yCoordi=yCoord;
        }

        protected Object doInBackground()
        {
            RGBMASS=new int[displaySize];
            for (int i=0; i<displaySize; i++){

                double xCoord = fractal.getCoord(range.x,
                        range.x + range.width, displaySize, i);
                double yCoord = fractal.getCoord(range.y,
                        range.y + range.height, displaySize, yCoordi);

                int iteration = fractal.numIterations(xCoord, yCoord);

                if (iteration == -1){
                   RGBMASS[i]=0;
                }

                else {

                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    RGBMASS[i]=rgbColor;
                }

            }
          return null;
        }
        protected void done() {
            // перебираем содержимое массива пикселей и выводи их на экран
            for (int i = 0; i < RGBMASS.length; i++) {
                display.drawPixel(i, yCoordi, RGBMASS[i]);
            }
            display.repaint(0, 0, yCoordi, displaySize, 1);

            // уменьшаем кол-во строк
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }

}

