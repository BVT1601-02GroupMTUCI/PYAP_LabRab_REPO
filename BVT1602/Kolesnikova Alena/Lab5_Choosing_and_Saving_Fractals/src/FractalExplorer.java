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
        JButton button = new JButton("Сброс");
        comboBox = new JComboBox<>();
        JButton saveImage = new JButton("Сохранить изображение");
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
        for (int y = 0; y < displaySize; y++)
            for (int x = 0; x < displaySize; x++)

            {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                int countIt = fractalGenerator.numIterations(xCoord, yCoord);
                if (countIt == -1) image.drawPixel(x, y, 0);
                else {
                    float hue = 0.7f + (float) countIt / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    image.drawPixel(x, y, rgbColor);
                }
            }

        image.repaint();
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
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
            fractalGenerator.recenterAndZoomRange(range,xCoord,yCoord,0.5);
            drawFractal();
        }

    }

    public static void main(String[] args)
    {
        FractalExplorer fractalExplorer = new FractalExplorer(550);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}
