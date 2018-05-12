import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class FractalExplorer {
    private JComboBox<FractalGenerator> comboBox;
    private int displaySize;
    private JImageDisplay image;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;
    private int countRow;
    private JButton button;
    private JButton saveImage;
    public FractalExplorer(int displaySize){
        this.displaySize = displaySize;
        range = new Rectangle2D.Double();
        fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(range);
    }
    public void createAndShowGUI()
    {
        JFrame frame = new JFrame ("Fractal Explorer");
        image = new JImageDisplay(displaySize,displaySize);
        button = new JButton("Сброс");
        comboBox = new JComboBox<>();
        saveImage = new JButton("Сохранить изображение");
        JLabel label = new JLabel("Фрактал:");
        JPanel panelSouth = new JPanel();
        JPanel panelNorth = new JPanel();
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());
        ActionListener clickEvent  = new ClickEvent();
        button.setActionCommand("Сброс");
        saveImage.setActionCommand("Сохранить");
        panelNorth.add(label);
        panelNorth.add(comboBox);
        panelSouth.add(button);
        panelSouth.add(saveImage);
        button.addActionListener(clickEvent);
        saveImage.addActionListener(clickEvent);
        comboBox.addActionListener(clickEvent);
        image.addMouseListener(new Zoom());
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(image, BorderLayout.CENTER);
        frame.add(panelSouth,BorderLayout.SOUTH);
        frame.add(panelNorth,BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    private void drawFractal() //отрисовывает фрактал
    {
        enableUI(false);
        countRow = displaySize;
        for (int y = 0; y < displaySize; y++)
        {
            FractalWorker drawRow = new FractalWorker(y);
            drawRow.execute();

        }
    }
    private void enableUI(boolean val)
    {
        button.setEnabled(val);
        saveImage.setEnabled(val);
        comboBox.setEnabled(val);
    }
    private class ClickEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == comboBox )
            {
                fractalGenerator = (FractalGenerator)comboBox.getSelectedItem();
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
            else if (e.getActionCommand() == "Сброс")
            {
                    fractalGenerator.getInitialRange(range);
                    drawFractal();
            }
            else if (e.getActionCommand() == "Сохранить")
            {
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showSaveDialog(image) == JFileChooser.APPROVE_OPTION)
                {
                    try {
                        ImageIO.write(image.image,"png",chooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(image,e1.getMessage(),"Ошибка записи", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private class Zoom extends MouseAdapter{ // по клику мыши определяет координаты и пересчитывает диапозон, произведя приближение в 2 раза
        @Override
        public void mouseClicked(MouseEvent e){
            if (countRow !=0)
                return;
            double scale = (e.getButton() == MouseEvent.BUTTON1)?0.5:2;
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
            fractalGenerator.recenterAndZoomRange(range,xCoord,yCoord,scale);
            drawFractal();
        }

    }

    private class FractalWorker extends SwingWorker<Object,Object>{

        private int y;
        private int[] colors;

        public FractalWorker(int y)
        {
            this.y = y;
        }

        public Object doInBackground()
        {
            colors = new int[displaySize];
            for (int x=0;x<displaySize;x++)
            {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                int countIt = fractalGenerator.numIterations(xCoord, yCoord);
                if (countIt == -1) colors[x] = 0;
                else {
                    float hue = 0.7f + (float) countIt / 200f;
                    colors[x] = Color.HSBtoRGB(hue, 1f, 1f);
                }
            }
            return null;
        }

        public void done()
        {
            for (int x=0;x<displaySize;x++)
                image.drawPixel(x,y,colors[x]);
        image.repaint(0,0,y,displaySize,1);
        countRow--;
        if (countRow==0)
            enableUI(true);
        }


    }

    public static void main(String[] args)
    {
        FractalExplorer fractalExplorer = new FractalExplorer(550);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

}
