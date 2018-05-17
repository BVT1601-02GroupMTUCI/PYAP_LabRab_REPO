import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import java.io.File;

public class FractalExplorer
{
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;
    private int rowsRemaining;
    private JButton saveButton;
    private JButton resetButton;
    private JComboBox myComboBox;

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
        JFrame myframe = new JFrame("Laba_6: Fractal Explorer");

        myframe.add(display, BorderLayout.CENTER);//располагаем областьотображения по центру окна

        this.resetButton = new JButton("Очистить");

        ButtonHandler handler = new ButtonHandler();
        resetButton.addActionListener(handler);

        myframe.add(resetButton, BorderLayout.SOUTH);

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
        JLabel myLabel = new JLabel("Тип фрактала:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        myframe.add(myPanel, BorderLayout.NORTH);

        this.saveButton = new JButton("Сохранить");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        myframe.add(myBottomPanel, BorderLayout.SOUTH);

        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myframe.pack();//метод pack() устанавливает такой минимальный размер контейнера, который достаточен для отображения всех компонентов.
        myframe.setVisible(true);
        myframe.setResizable(false);//убираем возможность расширения окна
    }

    private void enableUI(boolean bool) {
        this.myComboBox.setEnabled(bool);
        this.resetButton.setEnabled(bool);
        this.saveButton.setEnabled(bool);
    }

    private void drawFractal()
    {
        this.enableUI(false);
        this.rowsRemaining = this.displaySize;

        for(int i = 0; i < this.displaySize; ++i) {
            FractalExplorer.FractalWorker j = new FractalExplorer.FractalWorker(i);
            j.execute();
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
                    java.io.File file = new File(myFileChooser.getSelectedFile() + "png");
                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                                exception.getMessage(), "Невозможно сохранить изоражение",
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
        private FractalWorker(int yCoord){
            this.yCoordi=yCoord;
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
            for (int i = 0; i < RGBMASS.length; i++) {
                display.drawPixel(i, yCoordi, RGBMASS[i]);
            }
            display.repaint(0, 0, yCoordi, displaySize, 1);

            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }

}

